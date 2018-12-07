package com.andrelagacione.garagemcarroapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.andrelagacione.garagemcarroapi.domain.Marca;
import com.andrelagacione.garagemcarroapi.dto.MarcaDTO;
import com.andrelagacione.garagemcarroapi.repositories.MarcaRepository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class MarcaService {
	@Autowired
	private MarcaRepository marcaRepository;
	
	public List<Marca> findAll() {
		return marcaRepository.findAll();
	}
	
	public Page<Marca> findPage(Integer page, Integer size, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return marcaRepository.findAll(pageRequest);
	}
	
	public Marca find(Integer id) throws ObjectNotFoundException {
		Optional<Marca> marca = marcaRepository.findById(id);
		return marca.orElseThrow(() -> new ObjectNotFoundException("Marca não encontrada!"));
	}
	
	public Marca insert(Marca marca) {
		marca.setId(null);
		return marcaRepository.save(marca);
	}
	
	public Marca update(Marca marca) throws ObjectNotFoundException {
		Marca newMarca = find(marca.getId());
		updateData(newMarca, marca);
		return marcaRepository.save(newMarca);
	}
	
	public void delete(Integer id) throws ObjectNotFoundException {
		find(id);
		
		try {
			marcaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível excluir uma marca que possui modelos relacionados!");
		}
	}
	
	public Marca fromDto(MarcaDTO marcaDTO) {
		return new Marca(marcaDTO.getId(), marcaDTO.getNome());
	}
	
	public void updateData(Marca newMarca, Marca marca) {
		newMarca.setNome(marca.getNome());
	}
}
