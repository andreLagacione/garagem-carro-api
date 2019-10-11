package com.andrelagacione.garagemcarroapi.resources;

import com.andrelagacione.garagemcarroapi.domain.Marca;
import com.andrelagacione.garagemcarroapi.domain.Modelo;
import com.andrelagacione.garagemcarroapi.dto.MarcaDTO;
import com.andrelagacione.garagemcarroapi.dto.ModeloDTO;
import com.andrelagacione.garagemcarroapi.services.ModeloService;
import javassist.tools.rmi.ObjectNotFoundException;
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
@RequestMapping(value="/modelos")
public class ModeloResource {
    @Autowired
    private ModeloService modeloService;

    @RequestMapping(value="/lista", method= RequestMethod.GET)
    public ResponseEntity<List<ModeloDTO>> findAll() {
        List<Modelo> modelos = this.modeloService.findAll();
        List<ModeloDTO> modeloDTO = modelos.stream().map(obj -> new ModeloDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(modeloDTO);
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Page<ModeloDTO>> findPage(
            @RequestParam(value="page", defaultValue="0") Integer page,
            @RequestParam(value="size", defaultValue="25") Integer size,
            @RequestParam(value="ordrBy", defaultValue="nome") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction
    ) {
        Page<Modelo> modelos = this.modeloService.findPage(page, size, direction, orderBy);
        Page<ModeloDTO> modeloDTO = modelos.map(obj -> new ModeloDTO(obj));
        return ResponseEntity.ok().body(modeloDTO);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Modelo> find(@PathVariable Integer id) throws ObjectNotFoundException {
        Modelo modelo = this.modeloService.find(id);
        return ResponseEntity.ok().body(modelo);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody ModeloDTO modeloDTO) {
        Modelo modelo = this.modeloService.fromDto(modeloDTO);
        modelo = this.modeloService.insert(modelo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(modelo.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> update(
            @Valid @RequestBody ModeloDTO modeloDTO,
            @PathVariable Integer id
    ) throws ObjectNotFoundException {
        Modelo modelo = this.modeloService.fromDto(modeloDTO);
        modelo.setId(id);
        modelo = this.modeloService.update(modelo);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws ObjectNotFoundException {
        this.modeloService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
