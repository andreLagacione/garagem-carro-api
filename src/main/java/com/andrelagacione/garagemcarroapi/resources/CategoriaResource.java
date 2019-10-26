package com.andrelagacione.garagemcarroapi.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.andrelagacione.garagemcarroapi.dto.PadraoMensagemRetorno;
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

import com.andrelagacione.garagemcarroapi.domain.Categoria;
import com.andrelagacione.garagemcarroapi.dto.CategoriaDTO;
import com.andrelagacione.garagemcarroapi.services.CategoriaService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/categorias")
public class CategoriaResource {
	@Autowired
	private CategoriaService categoriaService;
	
	@RequestMapping(value="/lista", method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		return this.categoriaService.findAll();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
		@RequestParam(value="page", defaultValue="0") Integer page,
		@RequestParam(value="size", defaultValue="25") Integer size,
		@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
		@RequestParam(value="direction", defaultValue="ASC") String direction
	) {
		return this.categoriaService.findPage(page, size, orderBy, direction);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) throws ObjectNotFoundException {
		return this.categoriaService.find(id);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<PadraoMensagemRetorno> insert(@Valid @RequestBody CategoriaDTO categoriaDTO) {
		return this.categoriaService.salvarRegistro(categoriaDTO, true);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<PadraoMensagemRetorno> update(
		@Valid @RequestBody CategoriaDTO categoriaDto,
		@PathVariable Integer id
	) throws ObjectNotFoundException {
		return this.categoriaService.salvarRegistro(categoriaDto, false);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<PadraoMensagemRetorno> delete(@PathVariable Integer id) throws ObjectNotFoundException {
		return this.categoriaService.delete(id);
	}
}
