package com.andrelagacione.garagemcarroapi.services;

import com.andrelagacione.garagemcarroapi.domain.Modelo;
import com.andrelagacione.garagemcarroapi.dto.ModeloDTO;
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

@Service
public class ModeloService {
    @Autowired
    private ModeloRepository modeloRepository;

    public List<Modelo> findByMarca(Integer idMarca) {
        return this.modeloRepository.findByMarca(idMarca);
    }

    public Page<Modelo> findPage(Integer page, Integer size, String direction, String orderBy, Integer idMarca) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);

        if (idMarca != null) {
            return this.modeloRepository.findByMarcaPageable(idMarca, pageRequest);
        }

        return this.modeloRepository.findAll(pageRequest);
    }

    public Modelo find(Integer id) throws ObjectNotFoundException {
        Optional<Modelo> modelo = this.modeloRepository.findById(id);
        return modelo.orElseThrow(() -> new ObjectNotFoundException("Modelo não encontrado"));
    }

    public Modelo insert(Modelo modelo) {
        modelo.setId(null);
        return this.modeloRepository.save(modelo);
    }

    public Modelo update(Modelo modelo) throws ObjectNotFoundException {
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

    public Modelo fromDto(ModeloDTO modeloDTO) {
        return new Modelo(modeloDTO.getId(), modeloDTO.getNome(), modeloDTO.getMarca());
    }

    private void updateData(Modelo newModelo, Modelo modelo) {
        newModelo.setNome(modelo.getNome());
        newModelo.setMarca(modelo.getMarca());
    }
}
