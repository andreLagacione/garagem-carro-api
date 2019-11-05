package com.andrelagacione.garagemcarroapi.services;

import com.andrelagacione.garagemcarroapi.domain.Categoria;
import com.andrelagacione.garagemcarroapi.domain.Modelo;
import com.andrelagacione.garagemcarroapi.domain.TipoVeiculo;
import com.andrelagacione.garagemcarroapi.domain.Veiculo;
import com.andrelagacione.garagemcarroapi.dto.VeiculoDTO;
import com.andrelagacione.garagemcarroapi.repositories.ModeloRepository;
import com.andrelagacione.garagemcarroapi.repositories.TipoVeiculoRepository;
import com.andrelagacione.garagemcarroapi.repositories.VeiculoRepository;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VeiculoService {
	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@Autowired
	private ModeloRepository modeloRepository;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private TipoVeiculoRepository tipoVeiculoRepository;
	
	public List<VeiculoDTO> findAll() {
		List<Veiculo> veiculos = this.veiculoRepository.findAll();
		List<VeiculoDTO> veiculoDTO = veiculos.stream().map(obj -> new VeiculoDTO(obj)).collect(Collectors.toList());
		return veiculoDTO;
	}
	
	public Page<VeiculoDTO> findPage(Integer page, Integer size, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		Page<Veiculo> veiculos = this.veiculoRepository.findAll(pageRequest);
		Page<VeiculoDTO> veiculoDTO = veiculos.map(obj -> new VeiculoDTO(obj));
		return veiculoDTO;
	}
	
	public Veiculo find(Integer id) throws ObjectNotFoundException {
		Optional<Veiculo> veiculoById = this.veiculoRepository.findById(id);
		return veiculoById.orElseThrow(() -> new ObjectNotFoundException("Veiculo não encontrado!"));
	}
	
	private Veiculo insert(Veiculo veiculo) {
		veiculo.setId(null);
		return this.veiculoRepository.save(veiculo);
	}
	
	private Veiculo update(Veiculo veiculo) throws ObjectNotFoundException {
		Veiculo newVeiculo = this.find(veiculo.getId());
		this.updateData(newVeiculo, veiculo);
		return this.veiculoRepository.save(newVeiculo);
	}
	
	public void delete(Integer id) throws ObjectNotFoundException {
		this.find(id);
		
		try {
			this.veiculoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Erro ao excluir veículo.");
		}
	}

	private Veiculo fromDto(VeiculoDTO veiculoDTO) {
		return new Veiculo(
				veiculoDTO.getId(),
				veiculoDTO.getValor(),
				veiculoDTO.getCor(),
				veiculoDTO.getCavalos(),
				veiculoDTO.getCilindradas(),
				veiculoDTO.getPortas(),
				veiculoDTO.getDescricao(),
				veiculoDTO.getCategorias(),
				veiculoDTO.getModelo(),
				veiculoDTO.getTipoVeiculo()
		);
	}

	private void updateData(Veiculo newVeiculo, Veiculo veiculo) {
		newVeiculo.setValor(veiculo.getValor());
		newVeiculo.setCor(veiculo.getCor());
		newVeiculo.setCavalos(veiculo.getCavalos());
		newVeiculo.setCilindradas(veiculo.getCilindradas());
		newVeiculo.setPortas(veiculo.getPortas());
		newVeiculo.setModelo(veiculo.getModelo());
		newVeiculo.setDescricao(veiculo.getDescricao());
		newVeiculo.setCategorias(veiculo.getCategorias());
		newVeiculo.setTipoVeiculo(veiculo.getTipoVeiculo());
	}
	
	private Veiculo setarCategorias(Veiculo veiculo, List<Categoria> categorias) throws ObjectNotFoundException {
		if (categorias == null || categorias.size() == 0) {
			throw new ObjectNotFoundException("Informe pelo menos uma categoria!");
		}

		List<Categoria> listaCategorias = new ArrayList<Categoria>();
		for (Categoria i : categorias) {
			Categoria categoria = this.categoriaService.find(i.getId());
			listaCategorias.add(categoria);
		}
		
		veiculo.setCategorias(listaCategorias);
		return veiculo;
	}

	public Veiculo salvarDados(VeiculoDTO veiculoDTO, Boolean adicionar) {
		this.validarModelo(veiculoDTO.getModelo());
		this.validarTipoVeiculo(veiculoDTO.getTipoVeiculo());

		Veiculo veiculo = this.fromDto(veiculoDTO);
		veiculo = this.setarCategorias(veiculo, veiculo.getCategorias());

		if (adicionar) {
			return this.insert(veiculo);
		}

		return this.update(veiculo);
	}

	private void validarModelo(Modelo modelo) throws ObjectNotFoundException {
		if (modelo == null) {
			throw new ObjectNotFoundException("Informe o modelo do veículo!");
		}

		Optional<Modelo> _modelo = this.modeloRepository.findById(modelo.getId());

		if (!_modelo.isPresent()) {
			throw new ObjectNotFoundException("O modelo informado não foi encontrado. Por favor selecione um modelo válido!");
		}
	}

	private void validarTipoVeiculo(TipoVeiculo tipoVeiculo) throws ObjectNotFoundException {
		if (tipoVeiculo == null) {
			throw new ObjectNotFoundException("Informe o tipo do veículo!");
		}

		Optional<TipoVeiculo> _tipoVeiculo = this.tipoVeiculoRepository.findById(tipoVeiculo.getId());

		if (!_tipoVeiculo.isPresent()) {
			throw new ObjectNotFoundException("O tipo de veículo informado não foi encontrado. Por favor selecione um tipo válido!");
		}
	}
}
