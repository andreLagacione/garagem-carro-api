package com.andrelagacione.garagemcarroapi.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andrelagacione.garagemcarroapi.domain.Categoria;
import com.andrelagacione.garagemcarroapi.domain.Marca;
import com.andrelagacione.garagemcarroapi.domain.Veiculo;
import com.andrelagacione.garagemcarroapi.dto.VeiculoDTO;
import com.andrelagacione.garagemcarroapi.repositories.CategoriaRespository;
import com.andrelagacione.garagemcarroapi.repositories.VeiculoRepository;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;

@Service
public class VeiculoService {
	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@Autowired
	private CategoriaRespository categoriaRespository;
	
	public List<Veiculo> findAll() {
		return veiculoRepository.findAll();
	}
	
	public Page<Veiculo> findPage(Integer page, Integer size, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return veiculoRepository.findAll(pageRequest);
	}
	
	public Veiculo find(Integer id) throws ObjectNotFoundException {
		Optional<Veiculo> veiculo = veiculoRepository.findById(id);
		return veiculo.orElseThrow(() -> new ObjectNotFoundException("Veiculo não encontrado!"));
	}
	
	@Transactional
	public Veiculo insert(Veiculo veiculo) {
		veiculo.setId(null);
		veiculo = veiculoRepository.save(veiculo);
		// categoriaRespository.saveAll(veiculo.getCategorias());
		return veiculo;
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
			null,
			veiculoDTO.getValor(),
			veiculoDTO.getCor(),
			veiculoDTO.getCavalos(),
			veiculoDTO.getCilindradas(),
			veiculoDTO.getPortas(),
			veiculoDTO.getModelo(),
			veiculoDTO.getDescricao()
		);
	}
	
	public Veiculo fromDto(Double valor, String cor, Double cavalos, Double cilindradas, Integer portas, String modelo, String descricao, Integer idMarca, List<Integer> idCategoria) {
		Veiculo veiculo = new Veiculo(null, valor, cor, cavalos, cilindradas, portas, modelo, descricao);
		// Marca marca = new Marca(idMarca, null);
		// this.adicionarCategorias(veiculo, idCategoria);
		
		// marca.getVeiculos().add(veiculo);
		// veiculo.setMarca(marca);
		
		return veiculo;
	}
	
	public void updateData(Veiculo newVeiculo, Veiculo veiculo) {
		newVeiculo.setValor(veiculo.getValor());
		newVeiculo.setCor(veiculo.getCor());
		newVeiculo.setCavalos(veiculo.getCavalos());
		newVeiculo.setCilindradas(veiculo.getCilindradas());
		newVeiculo.setPortas(veiculo.getPortas());
		newVeiculo.setModelo(veiculo.getModelo());
		newVeiculo.setDescricao(veiculo.getDescricao());
		newVeiculo.setMarca(veiculo.getMarca());
		newVeiculo.setCategorias(veiculo.getCategorias());
	}
	
	private void adicionarCategorias(Veiculo veiculo, List<Integer> categorias) {
		for (Integer id : categorias) {
			Categoria categoria = new Categoria(id, null);
			// veiculo.getCategorias().addAll(Arrays.asList(categoria));
		}
	}
}
