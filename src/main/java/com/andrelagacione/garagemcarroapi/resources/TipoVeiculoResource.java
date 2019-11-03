package com.andrelagacione.garagemcarroapi.resources;

import com.andrelagacione.garagemcarroapi.domain.TipoVeiculo;
import com.andrelagacione.garagemcarroapi.dto.PadraoMensagemRetorno;
import com.andrelagacione.garagemcarroapi.dto.TipoVeiculoDTO;
import com.andrelagacione.garagemcarroapi.services.TipoVeiculoService;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/tipo-veiculo")
public class TipoVeiculoResource {
    @Autowired
    private TipoVeiculoService tipoVeiculoService;

    @RequestMapping(value = "/lista", method = RequestMethod.GET)
    public ResponseEntity<List<TipoVeiculoDTO>> findAll() {
        return ResponseEntity.ok().body(this.tipoVeiculoService.findAll());
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<TipoVeiculoDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "25") Integer size,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        return ResponseEntity.ok().body(this.tipoVeiculoService.findPage(page, size, direction, orderBy));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<TipoVeiculo> find(@PathVariable Integer id) throws ObjectNotFoundException {
        return ResponseEntity.ok().body(this.tipoVeiculoService.find(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PadraoMensagemRetorno> insert(@RequestBody TipoVeiculoDTO tipoVeiculoDTO) {
        TipoVeiculo tipoVeiculo = this.tipoVeiculoService.salvarDados(tipoVeiculoDTO, true);
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Tipo de veículo criado com sucesso!");

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(tipoVeiculo.getId()).toUri();

        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<PadraoMensagemRetorno> update(
            @Valid @RequestBody TipoVeiculoDTO tipoVeiculoDTO
    ) throws ObjectNotFoundException {
        this.tipoVeiculoService.salvarDados(tipoVeiculoDTO, false);
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Tipo de veículo editado com sucesso!");
        return ResponseEntity.ok().body(mensagemRetorno);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<PadraoMensagemRetorno> delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.tipoVeiculoService.delete(id);
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Tipo de veículo removido com sucesso!");
        return ResponseEntity.ok().body(mensagemRetorno);
    }
}
