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

import com.andrelagacione.garagemcarroapi.domain.Cidade;
import com.andrelagacione.garagemcarroapi.domain.Estado;
import com.andrelagacione.garagemcarroapi.dto.CidadeDTO;
import com.andrelagacione.garagemcarroapi.services.CidadeService;
import com.andrelagacione.garagemcarroapi.services.EstadoService;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;

@RestController
@RequestMapping(value="/cidades")
public class CidadeResource {
	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private EstadoService estadoService;
	
	@RequestMapping(value="/lista", method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findAll(
		@RequestParam(value="idEstado") Integer idEstado
	) throws ObjectNotFoundException {
		List<Cidade> cidades = cidadeService.findByEstado(idEstado);
		List<CidadeDTO> cidadeDTO = cidades.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(cidadeDTO);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<CidadeDTO>> findPage(
		@RequestParam(value="idEstado") Integer idEstado,
		@RequestParam(value="page", defaultValue="0") Integer page,
		@RequestParam(value="size", defaultValue="25") Integer size,
		@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
		@RequestParam(value="direction", defaultValue="ASC") String direction
	) {
		Page<Cidade> cidades = cidadeService.findPage(page, size, orderBy, direction, idEstado);
		Page<CidadeDTO> cidadeDTO = cidades.map(obj -> new CidadeDTO(obj));
		return ResponseEntity.ok().body(cidadeDTO);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cidade> find(@PathVariable Integer id) throws ObjectNotFoundException {
		Cidade cidade = cidadeService.find(id);
		return ResponseEntity.ok().body(cidade);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CidadeDTO cidadeDTO) {
		Cidade cidade = cidadeService.fromDto(cidadeDTO);
		Estado estado = estadoService.find(cidadeDTO.getIdEstado());
		cidade.setEstado(estado);
		cidade = cidadeService.insert(cidade);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(cidade.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(
		@Valid @RequestBody CidadeDTO cidadeDTO,
		@PathVariable Integer id
	) throws ObjectNotFoundException {
		Cidade cidade = cidadeService.fromDto(cidadeDTO);
		cidade.setId(id);
		cidade = cidadeService.update(cidade);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) throws ObjectNotFoundException {
		cidadeService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
