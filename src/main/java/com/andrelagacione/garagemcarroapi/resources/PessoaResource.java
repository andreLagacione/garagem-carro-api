package com.andrelagacione.garagemcarroapi.resources;

import com.andrelagacione.garagemcarroapi.domain.Endereco;
import com.andrelagacione.garagemcarroapi.domain.Pessoa;
import com.andrelagacione.garagemcarroapi.dto.PadraoMensagemRetorno;
import com.andrelagacione.garagemcarroapi.dto.PessoaDTO;
import com.andrelagacione.garagemcarroapi.services.EnderecoService;
import com.andrelagacione.garagemcarroapi.services.PessoaService;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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
        Page<Pessoa> pessoas = pessoaService.findPage(page, size, orderBy, direction);
        Page<PessoaDTO> pessoaDTO = pessoas.map(obj -> new PessoaDTO(obj));
        return ResponseEntity.ok().body(pessoaDTO);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Pessoa> find(@PathVariable Integer id) throws ObjectNotFoundException {
        Pessoa pessoa = pessoaService.find(id);
        return ResponseEntity.ok().body(pessoa);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<PadraoMensagemRetorno> insert(@Valid @RequestBody PessoaDTO pessoaDTO) {
        return pessoaService.validarDados(pessoaDTO, true);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<PadraoMensagemRetorno> update(
            @Valid @RequestBody PessoaDTO pessoaDTO
    ) throws ObjectNotFoundException {
        return pessoaService.validarDados(pessoaDTO, false);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws ObjectNotFoundException {
        pessoaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
