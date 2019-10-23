package com.andrelagacione.garagemcarroapi.services;

import com.andrelagacione.garagemcarroapi.domain.TipoPessoa;
import com.andrelagacione.garagemcarroapi.dto.TipoPessoaDTO;
import com.andrelagacione.garagemcarroapi.repositories.TipoPessoaRepository;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoPessoaService {
    @Autowired
    private TipoPessoaRepository tipoPessoaRepository;

    public List<TipoPessoa> findAll() {
        return this.tipoPessoaRepository.findAll();
    }

    public Page<TipoPessoa> findPage(Integer page, Integer size, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
        return this.tipoPessoaRepository.findAll(pageRequest);
    }

    public TipoPessoa find(Integer id) throws ObjectNotFoundException {
        Optional<TipoPessoa> tipoPessoa = this.tipoPessoaRepository.findById(id);
        return tipoPessoa.orElseThrow(() -> new ObjectNotFoundException("Tipo de pessoa não encontrada!"));
    }

    public TipoPessoa insert(TipoPessoa tipoPessoa) {
        tipoPessoa.setId(null);
        return this.tipoPessoaRepository.save(tipoPessoa);
    }

    public TipoPessoa update(TipoPessoa tipoPessoa) throws ObjectNotFoundException {
        TipoPessoa newTipoPessoa = find(tipoPessoa.getId());
        updateData(newTipoPessoa, tipoPessoa);
        return this.tipoPessoaRepository.save(newTipoPessoa);
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        find(id);

        try {
            this.tipoPessoaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível excluir esse tipo de pessoa.");
        }
    }

    public TipoPessoa fromDto(TipoPessoaDTO tipoPessoaDTO) {
        return new TipoPessoa(tipoPessoaDTO.getId(), tipoPessoaDTO.getDescricao());
    }

    public void updateData(TipoPessoa newTipoPessoa, TipoPessoa tipoPessoa) {
        newTipoPessoa.setDescricao(tipoPessoa.getDescricao());
    }
}
