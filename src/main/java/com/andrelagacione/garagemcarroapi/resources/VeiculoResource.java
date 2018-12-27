package com.andrelagacione.garagemcarroapi.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.andrelagacione.garagemcarroapi.domain.Veiculo;
import com.andrelagacione.garagemcarroapi.dto.VeiculoDTO;
import com.andrelagacione.garagemcarroapi.services.VeiculoService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value = "/veiculos")
public class VeiculoResource {
	@Autowired
	private VeiculoService veiculoService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<VeiculoDTO>> findAll() {
		List<Veiculo> veiculos = veiculoService.findAll();
		List<VeiculoDTO> veiculosDTO = veiculos.stream().map(obj -> new VeiculoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(veiculosDTO);
	}
	
	/*
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<VeiculoDTO>> findPage(
		@RequestParam(value="nome", defaultValue="") String nome,
		@RequestParam(value="categorias", defaultValue="") String categorias,
		@RequestParam(value="page", defaultValue="0") Integer page,
		@RequestParam(value="size", defaultValue="25") Integer size,
		@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
		@RequestParam(value="direction", defaultValue="ASC") String direction
	) {
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeintList(categorias);
		Page<Veiculo> veiculos = veiculoService.search(nomeDecoded, ids, page, size, orderBy, direction);
		Page<VeiculoDTO> veiculosDTO = veiculos.map(obj -> new VeiculoDTO(obj));
		return ResponseEntity.ok().body(veiculosDTO);
	}
	*/
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<VeiculoDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="size", defaultValue="25") Integer size,
			@RequestParam(value="ordrBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction
	) {
		Page<Veiculo> veiculos = veiculoService.findPage(page, size, direction, orderBy);
		Page<VeiculoDTO> veiculosDTO = veiculos.map(obj -> new VeiculoDTO(obj));
		return ResponseEntity.ok().body(veiculosDTO);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Veiculo> find(@PathVariable Integer id) throws ObjectNotFoundException {
		Veiculo veiculo = veiculoService.find(id);
		return ResponseEntity.ok().body(veiculo);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody VeiculoDTO veiculoDTO) {
		Veiculo veiculo = veiculoService.fromDto(veiculoDTO);
		veiculo = veiculoService.insert(veiculo);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(veiculo.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(
		@Valid @RequestBody VeiculoDTO veiculoDto,
		@PathVariable Integer id
	) throws ObjectNotFoundException {
		Veiculo veiculo = veiculoService.fromDto(veiculoDto);
		veiculo.setId(id);
		veiculo = veiculoService.update(veiculo);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) throws ObjectNotFoundException {
		veiculoService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
