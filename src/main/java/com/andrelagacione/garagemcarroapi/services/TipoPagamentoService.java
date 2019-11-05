package com.andrelagacione.garagemcarroapi.services;

import com.andrelagacione.garagemcarroapi.domain.TipoPagamento;
import com.andrelagacione.garagemcarroapi.dto.TipoPagamentoDTO;
import com.andrelagacione.garagemcarroapi.repositories.TipoPagamentoRepository;
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
public class TipoPagamentoService {
    @Autowired
    private TipoPagamentoRepository tipoPagamentoRepository;

    public List<TipoPagamentoDTO> findAll() {
        List<TipoPagamento> tipoPagamento = this.tipoPagamentoRepository.findAll();
        List<TipoPagamentoDTO> tipoPagamentoDTOList = tipoPagamento.stream().map(obj -> new TipoPagamentoDTO(obj)).collect(Collectors.toList());
        return tipoPagamentoDTOList;
    }

    public Page<TipoPagamentoDTO> findPage(Integer page, Integer size, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
        Page<TipoPagamento> tipoPagamento = this.tipoPagamentoRepository.findAll(pageRequest);
        Page<TipoPagamentoDTO> tipoPagamentoDTOList = tipoPagamento.map(obj -> new TipoPagamentoDTO(obj));
        return tipoPagamentoDTOList;
    }

    public TipoPagamento find(Integer id) throws ObjectNotFoundException {
        Optional<TipoPagamento> tipoPagamento = this.tipoPagamentoRepository.findById(id);
        return tipoPagamento.orElseThrow(() -> new ObjectNotFoundException("Tipo de pagamento não encontrado!"));
    }

    private TipoPagamento insert(TipoPagamento tipoPagamento) {
        tipoPagamento.setId(null);
        return this.tipoPagamentoRepository.save(tipoPagamento);
    }

    private TipoPagamento update(TipoPagamento tipoPagamento) throws ObjectNotFoundException {
        TipoPagamento newTipoPagamento = find(tipoPagamento.getId());
        this.updateData(newTipoPagamento, tipoPagamento);
        return this.tipoPagamentoRepository.save(newTipoPagamento);
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            this.tipoPagamentoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível excluir tipo de pagamento que existem pagamentos vinculados à ele! ", e);
        }
    }

    private TipoPagamento fromDto(TipoPagamentoDTO tipoPagamentoDTO) {
        return new TipoPagamento(
                tipoPagamentoDTO.getId(),
                tipoPagamentoDTO.getNome()
        );
    }

    private void updateData(TipoPagamento newTipoPagamento, TipoPagamento tipoPagamento) {
        newTipoPagamento.setNome(tipoPagamento.getNome());
    }

    public TipoPagamento salvarRegistro(TipoPagamentoDTO tipoPagamentoDTO, Boolean adicionar) {
        TipoPagamento tipoPagamento = this.fromDto(tipoPagamentoDTO);

        if (adicionar) {
            return this.insert(tipoPagamento);
        }

        return this.update(tipoPagamento);

    }
}
