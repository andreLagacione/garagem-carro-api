package com.andrelagacione.garagemcarroapi.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tipo_pessoa")
public class TipoPessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @OneToOne(mappedBy = "tipoPesooa")
    private Pessoa pessoa;

    public TipoPessoa() {}

    public TipoPessoa(Integer id, String descricao, Pessoa pessoa) {
        this.id=id;
        this.descricao=descricao;
        this.pessoa=pessoa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao=descricao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa=pessoa;
    }
}
