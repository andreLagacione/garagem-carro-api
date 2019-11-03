package com.andrelagacione.garagemcarroapi.services;

import com.andrelagacione.garagemcarroapi.domain.TipoVeiculo;
import com.andrelagacione.garagemcarroapi.dto.TipoVeiculoDTO;
import com.andrelagacione.garagemcarroapi.repositories.TipoVeiculoRepository;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TipoVeiculoService {
    @Autowired
    private TipoVeiculoRepository tipoVeiculoRepository;

    public List<TipoVeiculoDTO> findAll() {
        List<TipoVeiculo> tipoVeiculos = this.tipoVeiculoRepository.findAll();
        List<TipoVeiculoDTO> tipoVeiculoDTO = tipoVeiculos.stream().map(obj -> new TipoVeiculoDTO(obj)).collect(Collectors.toList());
        return tipoVeiculoDTO;
    }

    public Page<TipoVeiculoDTO> findPage(Integer page, Integer size, String direction, String orderBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
        Page<TipoVeiculo> tipoVeiculo = this.tipoVeiculoRepository.findAll(pageRequest);
        Page<TipoVeiculoDTO> tipoVeiculoDTO = tipoVeiculo.map(obj -> new TipoVeiculoDTO(obj));
        return tipoVeiculoDTO;
    }

    public TipoVeiculo find(Integer id) throws ObjectNotFoundException {
        Optional<TipoVeiculo> tipoVeiculo = this.tipoVeiculoRepository.findById(id);
        return tipoVeiculo.orElseThrow(() -> new ObjectNotFoundException("Tipo de veículo não encontrado!"));
    }

    private TipoVeiculo insert(TipoVeiculo tipoVeiculo) {
        tipoVeiculo.setId(null);
        return this.tipoVeiculoRepository.save(tipoVeiculo);
    }

    private TipoVeiculo update(TipoVeiculo tipoVeiculo) {
        TipoVeiculo newTipoVeiculo = this.find(tipoVeiculo.getId());
        this.updateData(newTipoVeiculo, tipoVeiculo);
        return this.tipoVeiculoRepository.save(newTipoVeiculo);
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            this.tipoVeiculoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Erro ao excluir o tipo de veículo, existem veículos relacionados a este tipo.");
        }
    }

    private TipoVeiculo fromDto(TipoVeiculoDTO tipoVeiculoDTO) {
        return new TipoVeiculo(
                tipoVeiculoDTO.getId(),
                tipoVeiculoDTO.getNome()
        );
    }

    private void updateData(TipoVeiculo newTipoVeiculo, TipoVeiculo tipoVeiculo) {
        newTipoVeiculo.setNome(tipoVeiculo.getNome());
    }

    public TipoVeiculo salvarDados(TipoVeiculoDTO tipoVeiculoDTO, Boolean adicionar) {
        TipoVeiculo tipoVeiculo = this.fromDto(tipoVeiculoDTO);

        if (adicionar) {
            return this.insert(tipoVeiculo);
        }

        return this.update(tipoVeiculo);
    }
}
