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

import com.andrelagacione.garagemcarroapi.domain.Categoria;
import com.andrelagacione.garagemcarroapi.dto.CategoriaDTO;
import com.andrelagacione.garagemcarroapi.services.CategoriaService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	@Autowired
	private CategoriaService categoriaService;
	
	@RequestMapping(value="/lista", method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> categorias = categoriaService.findAll();
		List<CategoriaDTO> categoriaDTO = categorias.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(categoriaDTO);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
		@RequestParam(value="page", defaultValue="0") Integer page,
		@RequestParam(value="size", defaultValue="25") Integer size,
		@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
		@RequestParam(value="direction", defaultValue="ASC") String direction
	) {
		Page<Categoria> categorias = categoriaService.findPage(page, size, orderBy, direction);
		Page<CategoriaDTO> categoriaDTO = categorias.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(categoriaDTO);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) throws ObjectNotFoundException {
		Categoria categoria = categoriaService.find(id);
		return ResponseEntity.ok().body(categoria);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO categoriaDTO) {
		Categoria categoria= categoriaService.fromDto(categoriaDTO);
		categoria = categoriaService.insert(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(
		@Valid @RequestBody CategoriaDTO categoriaDto,
		@PathVariable Integer id
	) throws ObjectNotFoundException {
		Categoria categoria = categoriaService.fromDto(categoriaDto);
		categoria.setId(id);
		categoria = categoriaService.update(categoria);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) throws ObjectNotFoundException {
		categoriaService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
