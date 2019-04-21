package com.andrelagacione.garagemcarroapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.andrelagacione.garagemcarroapi.domain.Cidade;
import com.andrelagacione.garagemcarroapi.dto.CidadeDTO;
import com.andrelagacione.garagemcarroapi.repositories.CidadeRepository;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;

@Service
public class CidadeService {
	@Autowired
	private CidadeRepository cidadeRepository;
	
	public List<Cidade> findByEstado(Integer estadoId) {
		return cidadeRepository.findCidades(estadoId);
	}
	
	public Page<Cidade> findPage(Integer page, Integer size, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return cidadeRepository.findAll(pageRequest);
	}
	
	public Cidade find(Integer id) throws ObjectNotFoundException {
		Optional<Cidade> cidade = cidadeRepository.findById(id); 
		return cidade.orElseThrow(() -> new ObjectNotFoundException("Cidade não encontrada!"));
	}
	
	public Cidade insert(Cidade cidade) {
		cidade.setId(null);
		return cidadeRepository.save(cidade);
	}
	
	public Cidade update(Cidade cidade) throws ObjectNotFoundException {
		Cidade newCidade = find(cidade.getId());
		updateData(newCidade, cidade);
		return cidadeRepository.save(newCidade);
	}
	
	public void delete(Integer id) throws ObjectNotFoundException {
		find(id);
		
		try {
			cidadeRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível remover essa cidade, pois existem registros atrelados a ela!");
		}
	}
	
	public Cidade fromDto(CidadeDTO cidadeDTO) {
		return new Cidade(cidadeDTO.getId(), cidadeDTO.getNome(), null);
	}
	
	public void updateData(Cidade newCidade, Cidade cidade) {
		newCidade.setNome(cidade.getNome());
	}

}
