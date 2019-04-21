package com.andrelagacione.garagemcarroapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.andrelagacione.garagemcarroapi.domain.Estado;
import com.andrelagacione.garagemcarroapi.dto.EstadoDTO;
import com.andrelagacione.garagemcarroapi.repositories.EstadoRepository;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;

@Service
public class EstadoService {
	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Estado> findAll() {
		return estadoRepository.findAllByOrderByNome();
	}
	
	public Page<Estado> findPage(Integer page, Integer size, String orderBy, String direction) {
		PageRequest pageResquest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return estadoRepository.findAll(pageResquest);
	}
	
	public Estado find(Integer id) throws ObjectNotFoundException {
		Optional<Estado> estado = estadoRepository.findById(id);
		return estado.orElseThrow(() -> new ObjectNotFoundException("Estado não encontrado!"));
	}
	
	public Estado insert(Estado estado) {
		estado.setId(null);
		return estadoRepository.save(estado);
	}
	
	public Estado update(Estado estado) throws ObjectNotFoundException {
		Estado newEstado = find(estado.getId());
		updateData(newEstado, estado);
		return estadoRepository.save(newEstado);
	}
	
	public void delete(Integer id) throws ObjectNotFoundException {
		find(id);
		
		try {
			estadoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível excluir este estado, pois existem registros atrelados a ele!");
		}
	}
	
	public Estado fromDto(EstadoDTO estadoDTO) {
		return new Estado(estadoDTO.getId(), estadoDTO.getNome(), estadoDTO.getSigla());
	}
	
	public void updateData(Estado newEstado, Estado estado) {
			newEstado.setNome(estado.getNome());
			newEstado.setSigla(estado.getSigla());
	}
}
