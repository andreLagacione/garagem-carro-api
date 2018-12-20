package com.andrelagacione.garagemcarroapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.andrelagacione.garagemcarroapi.domain.Categoria;
import com.andrelagacione.garagemcarroapi.dto.CategoriaDTO;
import com.andrelagacione.garagemcarroapi.repositories.CategoriaRespository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRespository categoriaRepository;
	
	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer size, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return categoriaRepository.findAll(pageRequest);
	}
	
	public Categoria find(Integer id) throws ObjectNotFoundException {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada!"));
	}
	
	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return categoriaRepository.save(categoria);
	}
	
	public Categoria update(Categoria categoria) throws ObjectNotFoundException {
		Categoria newCategoria = find(categoria.getId());
		updateData(newCategoria, categoria);
		return categoriaRepository.save(newCategoria);
	}
	
	public void delete(Integer id) throws ObjectNotFoundException {
		find(id);
		
		try {
			categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível excluir uma categoria que possui veículos");
		}
	}
	
	public Categoria fromDto(CategoriaDTO categoriaDTO) {
		return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
	}
	
	public void updateData(Categoria newCategoria, Categoria categoria) {
		newCategoria.setNome(categoria.getNome());
	}
}
