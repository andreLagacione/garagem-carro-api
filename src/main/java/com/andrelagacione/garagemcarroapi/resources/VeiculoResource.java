package com.andrelagacione.garagemcarroapi.resources;

import com.andrelagacione.garagemcarroapi.domain.Veiculo;
import com.andrelagacione.garagemcarroapi.dto.PadraoMensagemRetorno;
import com.andrelagacione.garagemcarroapi.dto.VeiculoDTO;
import com.andrelagacione.garagemcarroapi.services.ModeloService;
import com.andrelagacione.garagemcarroapi.services.VeiculoService;
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
@RequestMapping(value = "/veiculos")
public class VeiculoResource {
	@Autowired
	private VeiculoService veiculoService;
	
	@Autowired
	private ModeloService modeloService;
	
	@RequestMapping(value="/lista", method=RequestMethod.GET)
	public ResponseEntity<List<VeiculoDTO>> findAll() {
		return ResponseEntity.ok().body(this.veiculoService.findAll());
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<VeiculoDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="size", defaultValue="25") Integer size,
			@RequestParam(value="ordrBy", defaultValue="modelo") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction
	) {
		return ResponseEntity.ok().body(this.veiculoService.findPage(page, size, direction, orderBy));
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Veiculo> find(@PathVariable Integer id) throws ObjectNotFoundException {
		return ResponseEntity.ok().body(this.veiculoService.find(id));
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<PadraoMensagemRetorno> insert(@RequestBody VeiculoDTO veiculoDTO) throws Error {
		Veiculo veiculo = this.veiculoService.salvarDados(veiculoDTO, true);
		PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Veículo criado com sucesso!");

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(veiculo.getId()).toUri();

		return ResponseEntity.created(uri).body(mensagemRetorno);
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<PadraoMensagemRetorno> update(
		@Valid @RequestBody VeiculoDTO veiculoDTO
	) throws ObjectNotFoundException {
		this.veiculoService.salvarDados(veiculoDTO, false);
		PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Veículo editado com sucesso!");
		return ResponseEntity.ok().body(mensagemRetorno);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<PadraoMensagemRetorno> delete(@PathVariable Integer id) throws ObjectNotFoundException {
		this.veiculoService.delete(id);
		PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Veículo editado com sucesso!");
		return ResponseEntity.ok().body(mensagemRetorno);
	}
	
}
