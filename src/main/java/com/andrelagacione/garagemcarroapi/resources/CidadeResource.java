package com.andrelagacione.garagemcarroapi.resources;

import com.andrelagacione.garagemcarroapi.domain.Cidade;
import com.andrelagacione.garagemcarroapi.dto.CidadeDTO;
import com.andrelagacione.garagemcarroapi.dto.PadraoMensagemRetorno;
import com.andrelagacione.garagemcarroapi.services.CidadeService;
import com.andrelagacione.garagemcarroapi.services.EstadoService;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;
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
		return ResponseEntity.ok().body(this.cidadeService.findByEstado(idEstado));
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<CidadeDTO>> findPage(
		@RequestParam(value="idEstado") Integer idEstado,
		@RequestParam(value="page", defaultValue="0") Integer page,
		@RequestParam(value="size", defaultValue="25") Integer size,
		@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
		@RequestParam(value="direction", defaultValue="ASC") String direction
	) {
		return ResponseEntity.ok().body(this.cidadeService.findPage(page, size, orderBy, direction, idEstado));
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cidade> find(@PathVariable Integer id) throws ObjectNotFoundException {
		return ResponseEntity.ok().body(this.cidadeService.find(id));
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<PadraoMensagemRetorno> insert(@Valid @RequestBody CidadeDTO cidadeDTO) {
		Cidade cidade = this.cidadeService.validarDados(cidadeDTO, true);
		PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Cidade adicionada com sucesso!");

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(cidade.getId()).toUri();

		return ResponseEntity.created(uri).body(mensagemRetorno);
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<PadraoMensagemRetorno> update(
		@Valid @RequestBody CidadeDTO cidadeDTO
	) throws ObjectNotFoundException {
		Cidade cidade = this.cidadeService.validarDados(cidadeDTO, false);
		PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Cidade editada com sucesso!");
		return ResponseEntity.ok().body(mensagemRetorno);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<PadraoMensagemRetorno> delete(@PathVariable Integer id) throws ObjectNotFoundException {
		this.cidadeService.delete(id);
		PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Cidade removida com sucesso!");
		return ResponseEntity.ok().body(mensagemRetorno);
	}

}
