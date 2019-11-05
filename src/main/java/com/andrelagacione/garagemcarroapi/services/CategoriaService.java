package com.andrelagacione.garagemcarroapi.services;

import com.andrelagacione.garagemcarroapi.domain.Categoria;
import com.andrelagacione.garagemcarroapi.dto.CategoriaDTO;
import com.andrelagacione.garagemcarroapi.repositories.CategoriaRespository;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRespository categoriaRepository;
	
	public List<CategoriaDTO> findAll() {
		List<Categoria> categorias = this.categoriaRepository.findAll();
		List<CategoriaDTO> categoriaDTO = categorias.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return categoriaDTO;
	}
	
	public Page<CategoriaDTO> findPage(Integer page, Integer size, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		Page<Categoria> categorias = this.categoriaRepository.findAll(pageRequest);
		Page<CategoriaDTO> categoriaDTO = categorias.map(obj -> new CategoriaDTO(obj));
		return categoriaDTO;
	}
	
	public Categoria find(Integer id) throws ObjectNotFoundException {
		Optional<Categoria> categoria = this.categoriaRepository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada!"));
	}

	private Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return this.categoriaRepository.save(categoria);
	}

	private Categoria update(Categoria categoria) throws ObjectNotFoundException {
		Categoria newCategoria = find(categoria.getId());
		this.updateData(newCategoria, categoria);
		return this.categoriaRepository.save(newCategoria);
	}
	
	public void delete(Integer id) throws ObjectNotFoundException {
		this.find(id);

		try {
			this.categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível excluir categorias que existem veículos vinculados à ela! ", e);
		}
	}
	
	private Categoria fromDto(CategoriaDTO categoriaDTO) {
		return new Categoria(
				categoriaDTO.getId(),
				categoriaDTO.getNome()
		);
	}

	private void updateData(Categoria newCategoria, Categoria categoria) {
		newCategoria.setNome(categoria.getNome());
	}

	public Categoria salvarRegistro(CategoriaDTO categoriaDTO, Boolean adicionar) {
		Categoria categoria = this.fromDto(categoriaDTO);

		if (adicionar) {
			return this.insert(categoria);
		}

		return this.update(categoria);

	}
}
