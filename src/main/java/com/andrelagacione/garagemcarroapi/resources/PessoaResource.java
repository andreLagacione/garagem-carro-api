package com.andrelagacione.garagemcarroapi.resources;

import com.andrelagacione.garagemcarroapi.domain.Pessoa;
import com.andrelagacione.garagemcarroapi.dto.PessoaDTO;
import com.andrelagacione.garagemcarroapi.enums.TipoPessoa;
import com.andrelagacione.garagemcarroapi.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/pessoa")
public class PessoaResource {
    @Autowired
    private PessoaService pessoaService;

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
}
