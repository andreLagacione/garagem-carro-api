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

import com.andrelagacione.garagemcarroapi.domain.Modelo;
import com.andrelagacione.garagemcarroapi.dto.ModeloDTO;
import com.andrelagacione.garagemcarroapi.services.ModeloService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value="/modelos")
public class ModeloResource {
	@Autowired
	private ModeloService modeloService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ModeloDTO>> findAll() {
		List<Modelo> modelos = modeloService.findAll();
		List<ModeloDTO> modelosDTO = modelos.stream().map(obj -> new ModeloDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(modelosDTO);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ModeloDTO>> findPage(
		@RequestParam(value="page", defaultValue="0") Integer page,
		@RequestParam(value="size", defaultValue="25") Integer size,
		@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
		@RequestParam(value="direction", defaultValue="ASC") String direction
	) {
		Page<Modelo> modelos = modeloService.findPage(page, size, orderBy, direction);
		Page<ModeloDTO> modelosDTO = modelos.map(obj -> new ModeloDTO(obj));
		return ResponseEntity.ok().body(modelosDTO);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Modelo> find(@PathVariable Integer id) throws ObjectNotFoundException {
		Modelo modelo = modeloService.find(id);
		return ResponseEntity.ok().body(modelo);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ModeloDTO modeloDTO) {
		Modelo modelo = modeloService.fromDto(modeloDTO);
		modelo = modeloService.insert(modelo);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(modelo.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(
		@Valid @RequestBody ModeloDTO modeloDto,
		@PathVariable Integer id
	) throws ObjectNotFoundException {
		Modelo modelo = modeloService.fromDto(modeloDto);
		modelo.setId(id);
		modelo= modeloService.update(modelo);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) throws ObjectNotFoundException {
		modeloService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
