package com.andrelagacione.garagemcarroapi.dto;

import com.andrelagacione.garagemcarroapi.domain.Pessoa;
import com.andrelagacione.garagemcarroapi.domain.TipoPessoa;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class TipoPessoaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotEmpty(message = "Informe uma descrição")
    private String descricao;

    private Pessoa pessoa;

    public TipoPessoaDTO() {}

    public TipoPessoaDTO(TipoPessoa tipoPessoa) {
        this.id = tipoPessoa.getId();
        this.descricao = tipoPessoa.getDescricao();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
