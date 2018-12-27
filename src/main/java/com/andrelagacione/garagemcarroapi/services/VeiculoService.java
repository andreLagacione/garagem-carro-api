package com.andrelagacione.garagemcarroapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.andrelagacione.garagemcarroapi.domain.Veiculo;
import com.andrelagacione.garagemcarroapi.dto.VeiculoDTO;
import com.andrelagacione.garagemcarroapi.repositories.VeiculoRepository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class VeiculoService {
	@Autowired
	private VeiculoRepository veiculoRepository;
	
	/*
	@Autowired
	private CategoriaRespository categoriaRespository;
	*/
	
	public List<Veiculo> findAll() {
		return veiculoRepository.findAll();
	}
	
	public Page<Veiculo> findPage(Integer page, Integer size, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return veiculoRepository.findAll(pageRequest);
	}
	
	public Veiculo find(Integer id) throws ObjectNotFoundException {
		Optional<Veiculo> veiculo = veiculoRepository.findById(id);
		return veiculo.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada!"));
	}
	
	public Veiculo insert(Veiculo veiculo) {
		veiculo.setId(null);
		return veiculoRepository.save(veiculo);
	}
	
	public Veiculo update(Veiculo veiculo) throws ObjectNotFoundException {
		Veiculo newVeiculo = find(veiculo.getId());
		updateData(newVeiculo, veiculo);
		return veiculoRepository.save(newVeiculo);
	}
	
	public void delete(Integer id) throws ObjectNotFoundException {
		find(id);
		
		try {
			veiculoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Erro ao excluir veículo.");
		}
	}
	
	public Veiculo fromDto(VeiculoDTO veiculoDTO) {
		return new Veiculo(
			veiculoDTO.getId(),
			veiculoDTO.getValor(),
			veiculoDTO.getCor(),
			veiculoDTO.getCavalos(),
			veiculoDTO.getCilindradas(),
			veiculoDTO.getPortas(),
			veiculoDTO.getModelo(),
			veiculoDTO.getDescricao()
		);
	}
	
	public void updateData(Veiculo newVeiculo, Veiculo veiculo) {
		newVeiculo.setValor(veiculo.getValor());
		newVeiculo.setCor(veiculo.getCor());
		newVeiculo.setCavalos(veiculo.getCavalos());
		newVeiculo.setCilindradas(veiculo.getCilindradas());
		newVeiculo.setPortas(veiculo.getPortas());
		newVeiculo.setModelo(veiculo.getModelo());
		newVeiculo.setDescricao(veiculo.getDescricao());
	}
	
	/*	
	public Page<Veiculo> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRespository.findAllById(ids);
		return veiculoRepository.findByNome(nome, categorias, pageRequest);
	}
	*/
	
}
