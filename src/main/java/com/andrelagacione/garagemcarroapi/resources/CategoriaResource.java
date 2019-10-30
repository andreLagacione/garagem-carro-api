package com.andrelagacione.garagemcarroapi.resources;

import com.andrelagacione.garagemcarroapi.domain.Categoria;
import com.andrelagacione.garagemcarroapi.dto.CategoriaDTO;
import com.andrelagacione.garagemcarroapi.dto.PadraoMensagemRetorno;
import com.andrelagacione.garagemcarroapi.services.CategoriaService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/categorias")
public class CategoriaResource {
	@Autowired
	private CategoriaService categoriaService;
	
	@RequestMapping(value="/lista", method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		return ResponseEntity.ok().body(this.categoriaService.findAll());
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
		@RequestParam(value="page", defaultValue="0") Integer page,
		@RequestParam(value="size", defaultValue="25") Integer size,
		@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
		@RequestParam(value="direction", defaultValue="ASC") String direction
	) {
		return ResponseEntity.ok().body(this.categoriaService.findPage(page, size, orderBy, direction));
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) throws ObjectNotFoundException {
		return ResponseEntity.ok().body(this.categoriaService.find(id));
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<PadraoMensagemRetorno> insert(@Valid @RequestBody CategoriaDTO categoriaDTO) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(this.categoriaService.salvarRegistro(categoriaDTO, true).getId()).toUri();
		PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Categoria adicionada com sucesso!");
		return ResponseEntity.created(uri).body(mensagemRetorno);
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<PadraoMensagemRetorno> update(
		@Valid @RequestBody CategoriaDTO categoriaDto
	) throws ObjectNotFoundException {
		this.categoriaService.salvarRegistro(categoriaDto, false);
		PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Categoria criada com sucesso!");
		return ResponseEntity.ok(mensagemRetorno);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<PadraoMensagemRetorno> delete(@PathVariable Integer id) throws ObjectNotFoundException {
		this.categoriaService.delete(id);
		PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Categoria removida com sucesso!");
		return ResponseEntity.ok().body(mensagemRetorno);
	}
}
