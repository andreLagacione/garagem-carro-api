package com.andrelagacione.garagemcarroapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.andrelagacione.garagemcarroapi.domain.Modelo;
import com.andrelagacione.garagemcarroapi.dto.ModeloDTO;
import com.andrelagacione.garagemcarroapi.repositories.ModeloRepository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class ModeloService {
	@Autowired
	private ModeloRepository modeloRepository;
	
	public List<Modelo> findAll() {
		return modeloRepository.findAll();
	}
	
	public Page<Modelo> findPage(Integer page, Integer size, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return modeloRepository.findAll(pageRequest);
	}
	
	public Modelo find(Integer id) throws ObjectNotFoundException {
		Optional<Modelo> modelo = modeloRepository.findById(id);
		return modelo.orElseThrow(() -> new ObjectNotFoundException("Modelo não ecnontrado"));
	}
	
	public Modelo insert(Modelo modelo) {
		modelo.setId(null);
		return modeloRepository.save(modelo);
	}
	
	public Modelo update(Modelo modelo) throws ObjectNotFoundException {
		Modelo newModelo = find(modelo.getId());
		updateData(newModelo, modelo);
		return modeloRepository.save(newModelo);
	}
	
	public void delete(Integer id) throws ObjectNotFoundException {
		find(id);
		
		try {
			modeloRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível exclir um modelo que possui veículos!");
		}
	}
	
	public Modelo fromDto(ModeloDTO modeloDTO) {
		return new Modelo(modeloDTO.getId(), modeloDTO.getNome());
	}
	
	public void updateData(Modelo newModelo, Modelo modelo) {
		newModelo.setNome(modelo.getNome());
		newModelo.setMarca(modelo.getMarca());
	}
}
