package com.andrelagacione.garagemcarroapi.services;

import com.andrelagacione.garagemcarroapi.domain.Pessoa;
import com.andrelagacione.garagemcarroapi.domain.TipoPessoa;
import com.andrelagacione.garagemcarroapi.dto.PessoaDTO;
import com.andrelagacione.garagemcarroapi.repositories.PessoaRepository;
import com.andrelagacione.garagemcarroapi.repositories.TipoPessoaRepository;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TipoPessoaRepository tipoPessoaRepository;

    public Page<PessoaDTO> findPage(Integer page, Integer size, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        Page<Pessoa> pessoas = this.pessoaRepository.findAll(pageRequest);
        Page<PessoaDTO> pessoaDTO = pessoas.map(obj -> new PessoaDTO(obj));
        return pessoaDTO;
    }

    public Pessoa find(Integer id) throws ObjectNotFoundException {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        return pessoa.orElseThrow(() -> new ObjectNotFoundException("Pessoa não encontrada!"));
    }

    private Pessoa insert(Pessoa pessoa) {
        pessoa.setId(null);
        return pessoaRepository.save(pessoa);
    }

    private Pessoa update(Pessoa pessoa) throws ObjectNotFoundException {
        Pessoa newPessoa = find(pessoa.getId());
        this.updateData(newPessoa, pessoa);
        return pessoaRepository.save(newPessoa);
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            this.pessoaRepository.deleteById(id);
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Não é possível remover essa pessoa!");
        }
    }

    private Pessoa fromDto(PessoaDTO pessoaDTO) {
        return new Pessoa(
                pessoaDTO.getId(),
                pessoaDTO.getNome(),
                pessoaDTO.getEmail(),
                pessoaDTO.getCpfCnpj(),
                pessoaDTO.getTelefone(),
                pessoaDTO.getTipoPessoa()
        );
    }

    private void updateData(Pessoa newPessoa, Pessoa pessoa) {
        newPessoa.setId(pessoa.getId());
        newPessoa.setNome(pessoa.getNome());
        newPessoa.setEmail(pessoa.getEmail());
        newPessoa.setCpfCnpj(pessoa.getCpfCnpj());
        newPessoa.setTelefone(pessoa.getTelefone());
        newPessoa.setTipoPessoa(pessoa.getTipoPessoa());
    }

    public Pessoa validarDados(PessoaDTO pessoaDTO, Boolean adicionar) throws Exception {
        Optional<TipoPessoa> tipoPessoa = this.tipoPessoaRepository.findById(pessoaDTO.getTipoPessoa().getId());

        if (!tipoPessoa.isPresent()) {
            throw new ObjectNotFoundException("O tipo de pessoa informado não foi encontrado. Por favor informe um tipo válido!");
        }

        Pessoa pessoa = this.fromDto(pessoaDTO);
        return this.validarCpfCnpj(pessoa, adicionar);
    }

    private Pessoa validarCpfCnpj(Pessoa pessoa, Boolean adicionar) throws Exception {
        Boolean cpfCnpjExists = this.pessoaRepository.existsCpfCnpj(pessoa.getCpfCnpj());

        if (cpfCnpjExists) {
            throw new Exception("Já existe uma pessoa com esse CPF/CNPJ cadastrado na base!");
        }

        if (adicionar) {
            return this.insert(pessoa);
        }

        return this.update(pessoa);

    }
}
