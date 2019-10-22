package com.andrelagacione.garagemcarroapi.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "cpfCnpj")
    private String cpfCnpj;

    @Column(name = "telefone")
    private String telefone;

    @OneToMany(mappedBy = "pessoa")
    private List<Endereco> listaEnderecos;

    public Pessoa() {}

    public Pessoa(Integer id, String nome, String email, String cpfCnpj, String telefone, List<Endereco> listaEnderecos) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpfCnpj = cpfCnpj;
        this.telefone = telefone;
        this.listaEnderecos = listaEnderecos;
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
}
