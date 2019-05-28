package com.andrelagacione.garagemcarroapi.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.andrelagacione.garagemcarroapi.domain.Marca;
import com.andrelagacione.garagemcarroapi.dto.MarcaDTO;
import com.andrelagacione.garagemcarroapi.services.MarcaService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/marcas")
public class MarcaResource {
	@Autowired
	private MarcaService marcaService;
	
	@RequestMapping(value="/lista", method=RequestMethod.GET)
	public ResponseEntity<List<MarcaDTO>> findAll() {
		List<Marca> marcas = marcaService.findAll();
		List<MarcaDTO> marcasDTO = marcas.stream().map(obj -> new MarcaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(marcasDTO);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<MarcaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="size", defaultValue="25") Integer size,
			@RequestParam(value="ordrBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction
	) {
		Page<Marca> marcas = marcaService.findPage(page, size, direction, orderBy);
		Page<MarcaDTO> marcasDTO = marcas.map(obj -> new MarcaDTO(obj));
		return ResponseEntity.ok().body(marcasDTO);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Marca> find(@PathVariable Integer id) throws ObjectNotFoundException {
		Marca marca = marcaService.find(id);
		return ResponseEntity.ok().body(marca);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody MarcaDTO marcaDTO) {
		Marca marca = marcaService.fromDto(marcaDTO);
		marca = marcaService.insert(marca);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(marca.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(
			@Valid @RequestBody MarcaDTO marcaDTO,
			@PathVariable Integer id
	) throws ObjectNotFoundException {
		Marca marca = marcaService.fromDto(marcaDTO);
		marca.setId(id);
		marca = marcaService.update(marca);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) throws ObjectNotFoundException {
		marcaService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
