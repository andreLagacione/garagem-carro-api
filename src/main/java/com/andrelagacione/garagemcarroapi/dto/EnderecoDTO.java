package com.andrelagacione.garagemcarroapi.dto;

import com.andrelagacione.garagemcarroapi.domain.Endereco;
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
    private Integer idPessoa;

    @NotNull(message="Informe a cidade.")
    private Integer idCidade;

    public EnderecoDTO() {}

    public EnderecoDTO(Endereco endereco) {
        this.id = endereco.getId();
        this.logradouro = endereco.getLogradouro();
        this.numero = endereco.getNumero();
        this.complemento = endereco.getComplemento();
        this.bairro = endereco.getBairro();
        this.cep = endereco.getCep();
        this.apelido = endereco.getApelido();
        this.idPessoa = endereco.getIdPessoa();
        this.idCidade = endereco.getIdCidade();
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

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public Integer getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(Integer idCidade) {
        this.idCidade = idCidade;
    }
}
