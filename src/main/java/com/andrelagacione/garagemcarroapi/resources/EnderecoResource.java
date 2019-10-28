package com.andrelagacione.garagemcarroapi.resources;

import com.andrelagacione.garagemcarroapi.domain.Endereco;
import com.andrelagacione.garagemcarroapi.dto.EnderecoDTO;
import com.andrelagacione.garagemcarroapi.dto.PadraoMensagemRetorno;
import com.andrelagacione.garagemcarroapi.services.EnderecoService;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        return ResponseEntity.ok().body(enderecoService.findAll(idPessoa));
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Endereco> find(@PathVariable Integer id) throws ObjectNotFoundException {
        return enderecoService.find(id);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<PadraoMensagemRetorno> insert(@Valid @RequestBody EnderecoDTO enderecoDTO) {
        return enderecoService.validarDados(enderecoDTO, true);
    }

    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<PadraoMensagemRetorno> update(
            @Valid @RequestBody EnderecoDTO enderecoDTO
    ) throws ObjectNotFoundException {
        return enderecoService.validarDados(enderecoDTO, false);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<PadraoMensagemRetorno> delete(@PathVariable Integer id) throws ObjectNotFoundException {
        return enderecoService.delete(id);
    }
}
