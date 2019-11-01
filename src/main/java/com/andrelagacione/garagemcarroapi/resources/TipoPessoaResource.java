package com.andrelagacione.garagemcarroapi.resources;

import com.andrelagacione.garagemcarroapi.domain.TipoPessoa;
import com.andrelagacione.garagemcarroapi.dto.PadraoMensagemRetorno;
import com.andrelagacione.garagemcarroapi.dto.TipoPessoaDTO;
import com.andrelagacione.garagemcarroapi.services.TipoPessoaService;
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
@RequestMapping(value = "/tipo-pessoa")
public class TipoPessoaResource {
    @Autowired
    private TipoPessoaService tipoPessoaService;

    @RequestMapping(value = "/list" ,method = RequestMethod.GET)
    public ResponseEntity<List<TipoPessoaDTO>> findAll() {
        return ResponseEntity.ok().body(this.tipoPessoaService.findAll());
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Page<TipoPessoaDTO>> findPage(
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="size", defaultValue="25") Integer size,
            @RequestParam(value="orderBy", defaultValue="descricao") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction
    ) {
        return ResponseEntity.ok().body(this.tipoPessoaService.findPage(page, size, orderBy, direction));
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<TipoPessoa> find(@PathVariable Integer id) throws ObjectNotFoundException {
        return ResponseEntity.ok().body(this.tipoPessoaService.find(id));
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<PadraoMensagemRetorno> insert(@Valid @RequestBody TipoPessoaDTO tipoPessoaDTO) {
        TipoPessoa tipoPessoa = this.tipoPessoaService.salvarDados(tipoPessoaDTO, true);
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Tipo de pessoa criado com sucesso!");

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(tipoPessoa.getId()).toUri();

        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<PadraoMensagemRetorno> update(
            @Valid @RequestBody TipoPessoaDTO tipoPessoaDTO
    ) throws ObjectNotFoundException {
        this.tipoPessoaService.salvarDados(tipoPessoaDTO, false);
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Tipo de pessoa editado com sucesso!");
        return ResponseEntity.ok().body(mensagemRetorno);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<PadraoMensagemRetorno> delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.tipoPessoaService.delete(id);
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Tipo de pessoa removido com sucesso!");
        return ResponseEntity.ok().body(mensagemRetorno);
    }

}
