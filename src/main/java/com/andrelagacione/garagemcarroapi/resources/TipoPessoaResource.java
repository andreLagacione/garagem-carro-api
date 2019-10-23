package com.andrelagacione.garagemcarroapi.resources;

import com.andrelagacione.garagemcarroapi.domain.TipoPessoa;
import com.andrelagacione.garagemcarroapi.dto.TipoPessoaDTO;
import com.andrelagacione.garagemcarroapi.services.TipoPessoaService;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/tipo-pessoa")
public class TipoPessoaResource {
    @Autowired
    private TipoPessoaService tipoPessoaService;

    @RequestMapping(value = "/list" ,method = RequestMethod.GET)
    public ResponseEntity<List<TipoPessoaDTO>> findAll() {
        List<TipoPessoa> tipoPessoas = this.tipoPessoaService.findAll();
        List<TipoPessoaDTO> tipoPessoaDTO = tipoPessoas.stream().map(obj -> new TipoPessoaDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(tipoPessoaDTO);
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Page<TipoPessoaDTO>> findPage(
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="size", defaultValue="25") Integer size,
            @RequestParam(value="orderBy", defaultValue="descricao") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction
    ) {
        Page<TipoPessoa> tipoPessoa = this.tipoPessoaService.findPage(page, size, orderBy, direction);
        Page<TipoPessoaDTO> tipoPessoaDTO = tipoPessoa.map(obj -> new TipoPessoaDTO(obj));
        return ResponseEntity.ok().body(tipoPessoaDTO);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<TipoPessoa> find(@PathVariable Integer id) throws ObjectNotFoundException {
        TipoPessoa tipoPessoa = this.tipoPessoaService.find(id);
        return ResponseEntity.ok().body(tipoPessoa);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody TipoPessoaDTO tipoPessoaDTO) {
        TipoPessoa tipoPessoa = this.tipoPessoaService.fromDto(tipoPessoaDTO);
        tipoPessoa = this.tipoPessoaService.insert(tipoPessoa);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(tipoPessoa.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> update(
            @Valid @RequestBody TipoPessoaDTO tipoPessoaDTO,
            @PathVariable Integer id
    ) throws ObjectNotFoundException {
        TipoPessoa tipoPessoa = this.tipoPessoaService.fromDto(tipoPessoaDTO);
        tipoPessoa.setId(id);
        tipoPessoa = this.tipoPessoaService.update(tipoPessoa);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.tipoPessoaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
