package com.andrelagacione.garagemcarroapi.services;

import com.andrelagacione.garagemcarroapi.domain.Cidade;
import com.andrelagacione.garagemcarroapi.domain.Estado;
import com.andrelagacione.garagemcarroapi.dto.CidadeDTO;
import com.andrelagacione.garagemcarroapi.repositories.CidadeRepository;
import com.andrelagacione.garagemcarroapi.repositories.EstadoRepository;
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
public class CidadeService {
	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<CidadeDTO> findByEstado(Integer estadoId) {
		List<Cidade> cidades = this.cidadeRepository.findCidades(estadoId);
		List<CidadeDTO> cidadeDTO = cidades.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		return cidadeDTO;
	}
	
	public Page<CidadeDTO> findPage(Integer page, Integer size, String orderBy, String direction, Integer idEstado) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		Page<Cidade> cidades = this.cidadeRepository.findCidadesPage(idEstado, pageRequest);
		Page<CidadeDTO> cidadeDTO = cidades.map(obj -> new CidadeDTO(obj));
		return cidadeDTO;
	}
	
	public Cidade find(Integer id) throws ObjectNotFoundException {
		Optional<Cidade> cidade = this.cidadeRepository.findById(id);
		return cidade.orElseThrow(() -> new ObjectNotFoundException("Cidade não encontrada!"));
	}

	private Cidade insert(Cidade cidade) {
		cidade.setId(null);
		return this.cidadeRepository.save(cidade);
	}

	private Cidade update(Cidade cidade) throws ObjectNotFoundException {
		Cidade newCidade = this.find(cidade.getId());
		this.updateData(newCidade, cidade);
		return this.cidadeRepository.save(newCidade);
	}
	
	public void delete(Integer id) throws ObjectNotFoundException {
		this.find(id);
		
		try {
			this.cidadeRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível remover essa cidade, pois existem registros atrelados a ela!");
		}
	}
	
	public Cidade fromDto(CidadeDTO cidadeDTO) {
		return new Cidade(cidadeDTO.getId(), cidadeDTO.getNome(), cidadeDTO.getEstado());
	}
	
	private void updateData(Cidade newCidade, Cidade cidade) {
		newCidade.setNome(cidade.getNome());
	}

	public Cidade validarDados(CidadeDTO cidadeDTO, Boolean adicionar) throws ObjectNotFoundException {
		Optional<Estado> estado = this.estadoRepository.findById(cidadeDTO.getEstado().getId());

		if (!estado.isPresent()) {
			throw new ObjectNotFoundException("Estado não encontrado! Por favor informe um estado válido.");
		}

		Cidade cidade = this.fromDto(cidadeDTO);

		if (adicionar) {
			return this.insert(cidade);
		}

		return this.update(cidade);
	}

}
