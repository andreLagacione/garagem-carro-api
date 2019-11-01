package com.andrelagacione.garagemcarroapi.services;

import com.andrelagacione.garagemcarroapi.domain.Marca;
import com.andrelagacione.garagemcarroapi.domain.Modelo;
import com.andrelagacione.garagemcarroapi.dto.ModeloDTO;
import com.andrelagacione.garagemcarroapi.repositories.MarcaRepository;
import com.andrelagacione.garagemcarroapi.repositories.ModeloRepository;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ModeloService {
    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    public List<ModeloDTO> findByMarca(Integer idMarca) {
        List<Modelo> modelos = this.modeloRepository.findByMarca(idMarca);
        List<ModeloDTO> modeloDTO = modelos.stream().map(obj -> new ModeloDTO(obj)).collect(Collectors.toList());
        return modeloDTO;
    }

    public Page<ModeloDTO> findPage(Integer page, Integer size, String direction, String orderBy, Integer idMarca) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        Page<Modelo> modelos;

        if (idMarca != null) {
            modelos = this.modeloRepository.findByMarcaPageable(idMarca, pageRequest);
        } else {
            modelos = this.modeloRepository.findAll(pageRequest);
        }

        Page<ModeloDTO> modeloDTO = modelos.map(obj -> new ModeloDTO(obj));
        return modeloDTO;
    }

    public Modelo find(Integer id) throws ObjectNotFoundException {
        Optional<Modelo> modelo = this.modeloRepository.findById(id);
        return modelo.orElseThrow(() -> new ObjectNotFoundException("Modelo não encontrado"));
    }

    private Modelo insert(Modelo modelo) {
        modelo.setId(null);
        return this.modeloRepository.save(modelo);
    }

    private Modelo update(Modelo modelo) throws ObjectNotFoundException {
        Modelo newModelo = this.find(modelo.getId());
        this.updateData(newModelo, modelo);
        return this.modeloRepository.save(newModelo);
    }

    public void delete (Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            this.modeloRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível excluir um modelo que tenha veículos relacionados a ele.");
        }
    }

    private Modelo fromDto(ModeloDTO modeloDTO) {
        return new Modelo(modeloDTO.getId(), modeloDTO.getNome(), modeloDTO.getMarca());
    }

    private void updateData(Modelo newModelo, Modelo modelo) {
        newModelo.setNome(modelo.getNome());
        newModelo.setMarca(modelo.getMarca());
    }

    public Modelo salvarDados(ModeloDTO modeloDTO, Boolean adicionar) {
        Optional<Marca> marca = this.marcaRepository.findById(modeloDTO.getMarca().getId());

        if (!marca.isPresent()) {
            throw new ObjectNotFoundException("A marca informada não foi encontrada. Por favor selecione uma marca válida!");
        }

        Modelo modelo = this.fromDto(modeloDTO);

        if (adicionar) {
            return this.insert(modelo);
        }

        return this.update(modelo);
    }
}
