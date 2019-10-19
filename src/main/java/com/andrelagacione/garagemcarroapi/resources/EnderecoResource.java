package com.andrelagacione.garagemcarroapi.resources;

import com.andrelagacione.garagemcarroapi.domain.Endereco;
import com.andrelagacione.garagemcarroapi.dto.EnderecoDTO;
import com.andrelagacione.garagemcarroapi.dto.EstadoDTO;
import com.andrelagacione.garagemcarroapi.services.EnderecoService;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/enderecos")
public class EnderecoResource {
    @Autowired
    private EnderecoService enderecoService;

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
        Estado estado = estadoService.find(cidadeDTO.getIdEstado());
        cidade.setEstado(estado);
        cidade = cidadeService.insert(cidade);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(cidade.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
