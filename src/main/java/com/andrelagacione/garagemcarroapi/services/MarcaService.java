package com.andrelagacione.garagemcarroapi.services;

import com.andrelagacione.garagemcarroapi.domain.Marca;
import com.andrelagacione.garagemcarroapi.dto.MarcaDTO;
import com.andrelagacione.garagemcarroapi.repositories.MarcaRepository;
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
public class MarcaService {
	@Autowired
	private MarcaRepository marcaRepository;
	
	public List<MarcaDTO> findAll() {
		List<Marca> marcas = this.marcaRepository.findAll();
		List<MarcaDTO> marcasDTO = marcas.stream().map(obj -> new MarcaDTO(obj)).collect(Collectors.toList());
		return marcasDTO;
	}
	
	public Page<MarcaDTO> findPage(Integer page, Integer size, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		Page<Marca> marcas = marcaRepository.findAll(pageRequest);
		Page<MarcaDTO> marcasDTO = marcas.map(obj -> new MarcaDTO(obj));
		return marcasDTO;
	}
	
	public Marca find(Integer id) throws ObjectNotFoundException {
		Optional<Marca> marca = marcaRepository.findById(id);
		return marca.orElseThrow(() -> new ObjectNotFoundException("Marca não encontrada!"));
	}
	
	private Marca insert(Marca marca) {
		marca.setId(null);
		return marcaRepository.save(marca);
	}

	private Marca update(Marca marca) throws ObjectNotFoundException {
		Marca newMarca = find(marca.getId());
		this.updateData(newMarca, marca);
		return marcaRepository.save(newMarca);
	}
	
	public void delete(Integer id) throws ObjectNotFoundException {
		this.find(id);
		
		try {
			this.marcaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível excluir uma marca que possui modelos relacionados!");
		}
	}

	private Marca fromDto(MarcaDTO marcaDTO) {
		return new Marca(marcaDTO.getId(), marcaDTO.getNome());
	}

	private void updateData(Marca newMarca, Marca marca) {
		newMarca.setNome(marca.getNome());
	}

	public Marca salvarDados(MarcaDTO marcaDTO, Boolean adicionar) {
		Marca marca = this.fromDto(marcaDTO);

		if (adicionar) {
			return this.insert(marca);
		}

		return this.update(marca);
	}
}
