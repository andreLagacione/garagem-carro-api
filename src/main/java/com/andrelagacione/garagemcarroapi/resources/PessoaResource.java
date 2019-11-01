package com.andrelagacione.garagemcarroapi.resources;

import com.andrelagacione.garagemcarroapi.domain.Pessoa;
import com.andrelagacione.garagemcarroapi.dto.PadraoMensagemRetorno;
import com.andrelagacione.garagemcarroapi.dto.PessoaDTO;
import com.andrelagacione.garagemcarroapi.services.EnderecoService;
import com.andrelagacione.garagemcarroapi.services.PessoaService;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/pessoa")
public class PessoaResource {
    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private EnderecoService enderecoService;

    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<Page<PessoaDTO>> findPage(
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="size", defaultValue="25") Integer size,
            @RequestParam(value="orderBy", defaultValue="nome") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction
    ) {
        return ResponseEntity.ok().body(this.pessoaService.findPage(page, size, orderBy, direction));
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Pessoa> find(@PathVariable Integer id) throws ObjectNotFoundException {
        return ResponseEntity.ok().body(this.pessoaService.find(id));
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<PadraoMensagemRetorno> insert(@Valid @RequestBody PessoaDTO pessoaDTO) throws Exception {
        Pessoa pessoa = pessoaService.validarDados(pessoaDTO, true);
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Pessoa adicionada com sucesso!");

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(pessoa.getId()).toUri();

        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<PadraoMensagemRetorno> update(
            @Valid @RequestBody PessoaDTO pessoaDTO
    ) throws Exception {
        this.pessoaService.validarDados(pessoaDTO, false);
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Pessoa editada com sucesso!");
        return ResponseEntity.ok(mensagemRetorno);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<PadraoMensagemRetorno> delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.pessoaService.delete(id);
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Pessoa removida com sucesso!");
        return ResponseEntity.ok().body(mensagemRetorno);
    }

}
