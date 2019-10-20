package com.andrelagacione.garagemcarroapi.resources;

import com.andrelagacione.garagemcarroapi.domain.Cidade;
import com.andrelagacione.garagemcarroapi.domain.Endereco;
import com.andrelagacione.garagemcarroapi.dto.EnderecoDTO;
import com.andrelagacione.garagemcarroapi.dto.EstadoDTO;
import com.andrelagacione.garagemcarroapi.services.CidadeService;
import com.andrelagacione.garagemcarroapi.services.EnderecoService;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/enderecos")
public class EnderecoResource {
    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private CidadeService cidadeService;

    @RequestMapping(value="/lista", method= RequestMethod.GET)
    public ResponseEntity<List<EnderecoDTO>> findAll(
            @RequestParam(value = "idPessoa") Integer idPessoa
    ) {
        List<Endereco> enderecos = enderecoService.findAll(idPessoa);
        List<EnderecoDTO> enderecoDTO = enderecos.stream().map(obj -> new EnderecoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(enderecoDTO);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Endereco> find(@PathVariable Integer id) throws ObjectNotFoundException {
        Endereco endereco = enderecoService.find(id);
        return ResponseEntity.ok().body(endereco);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody EnderecoDTO enderecoDTO) {
        Endereco endereco = enderecoService.fromDto(enderecoDTO);
        Cidade cidade = cidadeService.find(enderecoDTO.getIdCidade());
        endereco.setCidade(cidade);
        endereco = enderecoService.insert(endereco);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(endereco.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> update(
            @Valid @RequestBody EnderecoDTO enderecoDTO,
            @PathVariable Integer id
    ) throws ObjectNotFoundException {
        Endereco endereco = enderecoService.fromDto(enderecoDTO);
        endereco.setId(id);
        Cidade cidade = cidadeService.find(enderecoDTO.getIdCidade());
        endereco.setCidade(cidade);
        endereco = enderecoService.update(endereco);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws ObjectNotFoundException {
        enderecoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
