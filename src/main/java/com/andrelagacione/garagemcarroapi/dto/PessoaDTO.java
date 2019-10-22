package com.andrelagacione.garagemcarroapi.dto;

import com.andrelagacione.garagemcarroapi.domain.Endereco;
import com.andrelagacione.garagemcarroapi.domain.Pessoa;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class PessoaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotEmpty(message="Preenchimento obrigatório.")
    @Length(min=3, max=50, message="O tamanho deve ser entre 3 e 50 caractéres.")
    private String nome;

    @NotEmpty(message="Preenchimento obrigatório.")
    @Length(min=3, max=50, message="O tamanho deve ser entre 3 e 50 caractéres.")
    @Email(message = "E-mail inválido.")
    private String email;

    @NotEmpty(message="Preenchimento obrigatório.")
    @Length(min=3, max=20, message="O tamanho deve ser entre 3 e 20 caractéres.")
    private String cpfCnpj;

    @NotEmpty(message="Preenchimento obrigatório.")
    @Length(min=3, max=20, message="O tamanho deve ser entre 3 e 20 caractéres.")
    private String telefone;

    private List<Endereco> listaEnderecos;

    @NotNull(message="Informe o tipo da pessoa.")
    private Integer tipoPessoa;

    public PessoaDTO(Pessoa pessoa) {
        this.id = pessoa.getId();
        this.nome = pessoa.getNome();
        this.email = pessoa.getEmail();
        this.cpfCnpj = pessoa.getCpfCnpj();
        this.telefone = pessoa.getTelefone();
        this.listaEnderecos = pessoa.getListaEnderecos();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Endereco> getListaEnderecos() {
        return listaEnderecos;
    }

    public void setListaEnderecos(List<Endereco> listaEnderecos) {
        this.listaEnderecos = listaEnderecos;
    }

    public Integer getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(Integer tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }
}
