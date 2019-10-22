package com.andrelagacione.garagemcarroapi.services;

import com.andrelagacione.garagemcarroapi.domain.Pessoa;
import com.andrelagacione.garagemcarroapi.dto.PessoaDTO;
import com.andrelagacione.garagemcarroapi.repositories.PessoaRepository;
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
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    public Page<Pessoa> findPage(Integer page, Integer size, String orderBy, String direction, Integer tipoPessoa) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        return pessoaRepository.findPessoasPage(tipoPessoa, pageRequest);
    }

    public Pessoa find(Integer id) throws ObjectNotFoundException {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        return pessoa.orElseThrow(() -> new ObjectNotFoundException("Pessoa não encontrada!"));
    }

    public Pessoa insert(Pessoa pessoa) {
        pessoa.setId(null);
        return pessoaRepository.save(pessoa);
    }

    public Pessoa update(Pessoa pessoa) throws ObjectNotFoundException {
        Pessoa newPessoa = find(pessoa.getId());
        updateData(newPessoa, pessoa);
        return pessoaRepository.save(newPessoa);
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        find(id);

        try {
            pessoaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível remover essa pessoa!");
        }
    }

    public Pessoa fromDto(PessoaDTO pessoaDTO) {
        return new Pessoa(
                pessoaDTO.getId(),
                pessoaDTO.getNome(),
                pessoaDTO.getEmail(),
                pessoaDTO.getCpfCnpj(),
                pessoaDTO.getTelefone(),
                pessoaDTO.getListaEnderecos()
        );
    }

    public void updateData(Pessoa newPessoa, Pessoa pessoa) {
        newPessoa.setId(pessoa.getId());
        newPessoa.setNome(pessoa.getNome());
        newPessoa.setEmail(pessoa.getEmail());
        newPessoa.setCpfCnpj(pessoa.getCpfCnpj());
        newPessoa.setTelefone(pessoa.getTelefone());
        newPessoa.setListaEnderecos(pessoa.getListaEnderecos());
    }
}
