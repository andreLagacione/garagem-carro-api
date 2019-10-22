package com.andrelagacione.garagemcarroapi.resources;

import com.andrelagacione.garagemcarroapi.domain.TipoPessoa;
import com.andrelagacione.garagemcarroapi.dto.TipoPessoaDTO;
import com.andrelagacione.garagemcarroapi.services.TipoPessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/tipo-pessoa")
public class TipoPessoaResource {
    @Autowired
    private TipoPessoaService tipoPessoaService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TipoPessoaDTO>> findAll() {
        List<TipoPessoa> tipoPessoas = this.tipoPessoaService.findAll();
        List<TipoPessoaDTO> tipoPessoaDTO = tipoPessoas.stream().map(obj -> new TipoPessoaDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(tipoPessoaDTO);
    }
}
