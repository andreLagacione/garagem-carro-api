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
import java.util.stream.Collectors;

@Service
public class TipoPessoaService {
    @Autowired
    private TipoPessoaRepository tipoPessoaRepository;

    public List<TipoPessoaDTO> findAll() {
        List<TipoPessoa> tipoPessoas = this.tipoPessoaRepository.findAll();
        List<TipoPessoaDTO> tipoPessoaDTO = tipoPessoas.stream().map(obj -> new TipoPessoaDTO(obj)).collect(Collectors.toList());
        return tipoPessoaDTO;
    }

    public Page<TipoPessoaDTO> findPage(Integer page, Integer size, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
        Page<TipoPessoa> tipoPessoa = this.tipoPessoaRepository.findAll(pageRequest);
        Page<TipoPessoaDTO> tipoPessoaDTO = tipoPessoa.map(obj -> new TipoPessoaDTO(obj));
        return tipoPessoaDTO;
    }

    public TipoPessoa find(Integer id) throws ObjectNotFoundException {
        Optional<TipoPessoa> tipoPessoa = this.tipoPessoaRepository.findById(id);
        return tipoPessoa.orElseThrow(() -> new ObjectNotFoundException("Tipo de pessoa não encontrada!"));
    }

    private TipoPessoa insert(TipoPessoa tipoPessoa) {
        tipoPessoa.setId(null);
        return this.tipoPessoaRepository.save(tipoPessoa);
    }

    private TipoPessoa update(TipoPessoa tipoPessoa) throws ObjectNotFoundException {
        TipoPessoa newTipoPessoa = find(tipoPessoa.getId());
        this.updateData(newTipoPessoa, tipoPessoa);
        return this.tipoPessoaRepository.save(newTipoPessoa);
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            this.tipoPessoaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível excluir esse tipo de pessoa.");
        }
    }

    private TipoPessoa fromDto(TipoPessoaDTO tipoPessoaDTO) {
        return new TipoPessoa(
                tipoPessoaDTO.getId(),
                tipoPessoaDTO.getDescricao()
        );
    }

    private void updateData(TipoPessoa newTipoPessoa, TipoPessoa tipoPessoa) {
        newTipoPessoa.setDescricao(tipoPessoa.getDescricao());
    }

    public TipoPessoa salvarDados(TipoPessoaDTO tipoPessoaDTO, Boolean adicionar) {
        TipoPessoa tipoPessoa = this.fromDto(tipoPessoaDTO);

        if (adicionar) {
            return this.insert(tipoPessoa);
        }

        return this.update(tipoPessoa);
    }
}
