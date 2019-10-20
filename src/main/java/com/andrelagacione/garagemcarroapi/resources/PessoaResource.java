package com.andrelagacione.garagemcarroapi.resources;

import com.andrelagacione.garagemcarroapi.domain.Endereco;
import com.andrelagacione.garagemcarroapi.domain.Pessoa;
import com.andrelagacione.garagemcarroapi.dto.PessoaDTO;
import com.andrelagacione.garagemcarroapi.enums.TipoPessoa;
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
            @RequestParam(value="tipoPessoa")TipoPessoa tipoPessoa,
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="size", defaultValue="25") Integer size,
            @RequestParam(value="orderBy", defaultValue="nome") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction
    ) {
        Page<Pessoa> pessoas = pessoaService.findPage(page, size, orderBy, direction, tipoPessoa);
        Page<PessoaDTO> pessoaDTO = pessoas.map(obj -> new PessoaDTO(obj));
        return ResponseEntity.ok().body(pessoaDTO);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Pessoa> find(@PathVariable Integer id) throws ObjectNotFoundException {
        Pessoa pessoa = pessoaService.find(id);
        return ResponseEntity.ok().body(pessoa);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody PessoaDTO pessoaDTO) {
        Pessoa pessoa = pessoaService.fromDto(pessoaDTO);
        List<Endereco> listaEndereco = enderecoService.findAll(pessoaDTO.getId());
        pessoa.setListaEnderecos(listaEndereco);
        pessoa = pessoaService.insert(pessoa);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(pessoa.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> update(
            @Valid @RequestBody PessoaDTO pessoaDTO,
            @PathVariable Integer id
    ) throws ObjectNotFoundException {
        Pessoa pessoa = pessoaService.fromDto(pessoaDTO);
        pessoa.setId(id);
        List<Endereco> listaEndereco = enderecoService.findAll(pessoa.getId());
        pessoa.setListaEnderecos(listaEndereco);
        pessoa = pessoaService.update(pessoa);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws ObjectNotFoundException {
        pessoaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
