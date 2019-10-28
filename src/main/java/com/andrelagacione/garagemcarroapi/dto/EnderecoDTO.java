package com.andrelagacione.garagemcarroapi.dto;

import com.andrelagacione.garagemcarroapi.domain.Cidade;
import com.andrelagacione.garagemcarroapi.domain.Endereco;
import com.andrelagacione.garagemcarroapi.domain.Pessoa;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class EnderecoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotEmpty(message="Preenchimento obrigatório.")
    @Length(min=3, max=100, message="O tamanho deve ser entre 3 e 100 caractéres.")
    private String logradouro;

    @NotEmpty(message="Preenchimento obrigatório.")
    private String numero;

    private String complemento;

    @NotEmpty(message="Preenchimento obrigatório.")
    @Length(min=3, max=30, message="O tamanho deve ser entre 3 e 30 caractéres.")
    private String bairro;

    @NotEmpty(message="Preenchimento obrigatório.")
    private String cep;

    @NotEmpty(message="Preenchimento obrigatório.")
    @Length(min=3, max=50, message="O tamanho deve ser entre 3 e 50 caractéres.")
    private String apelido;

    @NotNull(message="Informe a pessoa.")
    private Pessoa pessoa;

    @NotNull(message="Informe a cidade.")
    private Cidade cidade;

    public EnderecoDTO() {}

    public EnderecoDTO(Endereco endereco) {
        this.id = endereco.getId();
        this.logradouro = endereco.getLogradouro();
        this.numero = endereco.getNumero();
        this.complemento = endereco.getComplemento();
        this.bairro = endereco.getBairro();
        this.cep = endereco.getCep();
        this.apelido = endereco.getApelido();
        this.pessoa = endereco.getPessoa();
        this.cidade = endereco.getCidade();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
}
