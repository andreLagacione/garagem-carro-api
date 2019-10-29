package com.andrelagacione.garagemcarroapi.resources;

import com.andrelagacione.garagemcarroapi.domain.Endereco;
import com.andrelagacione.garagemcarroapi.dto.EnderecoDTO;
import com.andrelagacione.garagemcarroapi.dto.PadraoMensagemRetorno;
import com.andrelagacione.garagemcarroapi.services.EnderecoService;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/enderecos")
public class EnderecoResource {
    @Autowired
    private EnderecoService enderecoService;

    @RequestMapping(value="/lista/{idPessoa}", method= RequestMethod.GET)
    public ResponseEntity<List<EnderecoDTO>> findAll(
            @PathVariable Integer idPessoa
    ) {
        return ResponseEntity.ok().body(this.enderecoService.findAll(idPessoa));
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Endereco> find(@PathVariable Integer id) throws ObjectNotFoundException {
        return ResponseEntity.ok().body(this.enderecoService.find(id));
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<PadraoMensagemRetorno> insert(@Valid @RequestBody EnderecoDTO enderecoDTO) {
        Endereco endereco = this.enderecoService.validarDados(enderecoDTO, true);
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Endereço adicionado com sucesso!");

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(endereco.getId()).toUri();

        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<PadraoMensagemRetorno> update(
            @Valid @RequestBody EnderecoDTO enderecoDTO
    ) throws ObjectNotFoundException {
        this.enderecoService.validarDados(enderecoDTO, false);
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Endereço editado com sucesso!");
        return ResponseEntity.ok(mensagemRetorno);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<PadraoMensagemRetorno> delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.enderecoService.delete(id);
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Endereço removido com sucesso!");
        return ResponseEntity.ok(mensagemRetorno);
    }
}
