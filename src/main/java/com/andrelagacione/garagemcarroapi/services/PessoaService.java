package com.andrelagacione.garagemcarroapi.services;

import com.andrelagacione.garagemcarroapi.domain.Pessoa;
import com.andrelagacione.garagemcarroapi.domain.TipoPessoa;
import com.andrelagacione.garagemcarroapi.dto.PadraoMensagemRetorno;
import com.andrelagacione.garagemcarroapi.dto.PessoaDTO;
import com.andrelagacione.garagemcarroapi.repositories.PessoaRepository;
import com.andrelagacione.garagemcarroapi.repositories.TipoPessoaRepository;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TipoPessoaRepository tipoPessoaRepository;

    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    public Page<Pessoa> findPage(Integer page, Integer size, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        return pessoaRepository.findAll(pageRequest);
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
                pessoaDTO.getTipoPessoa()
        );
    }

    public void updateData(Pessoa newPessoa, Pessoa pessoa) {
        newPessoa.setId(pessoa.getId());
        newPessoa.setNome(pessoa.getNome());
        newPessoa.setEmail(pessoa.getEmail());
        newPessoa.setCpfCnpj(pessoa.getCpfCnpj());
        newPessoa.setTelefone(pessoa.getTelefone());
        newPessoa.setTipoPessoa(pessoa.getTipoPessoa());
    }

    public ResponseEntity<PadraoMensagemRetorno> validarDados(PessoaDTO pessoaDTO, Boolean adicionar) {
        Optional<TipoPessoa> tipoPessoa = this.tipoPessoaRepository.findById(pessoaDTO.getTipoPessoa());

        if (!tipoPessoa.isPresent()) {
            PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.NOT_FOUND, HttpStatus.valueOf("NOT_FOUND").value(), "Tipo de pessoa não encontrado!");
            return ResponseEntity.badRequest().body(mensagemRetorno);
        }

        Pessoa pessoa = this.fromDto(pessoaDTO);

        if (adicionar == true) {
            return this.validarCpfCnpj(pessoa);
        }

        return this.atualizarPessoa(pessoa);
    }

    private ResponseEntity<PadraoMensagemRetorno> validarCpfCnpj(Pessoa pessoa) {
        Boolean cpfCnpjExists = this.pessoaRepository.existsCpfCnpj(pessoa.getCpfCnpj());

        if (cpfCnpjExists) {
            PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.CONFLICT, HttpStatus.valueOf("CONFLICT").value(), "Já existe uma pessoa com esse CPF/CNPJ cadastrado na base!");
            return ResponseEntity.badRequest().body(mensagemRetorno);
        }

        return this.adicionarPessoa(pessoa);

    }

    private ResponseEntity<PadraoMensagemRetorno> adicionarPessoa(Pessoa pessoa) {
        this.insert(pessoa);
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Pessoa adicionada com sucesso!");
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(pessoa.getId()).toUri();

        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    private ResponseEntity<PadraoMensagemRetorno> atualizarPessoa(Pessoa pessoa) {
        this.update(pessoa);
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Pessoa editada com sucesso!");
        return ResponseEntity.ok(mensagemRetorno);
    }
}
