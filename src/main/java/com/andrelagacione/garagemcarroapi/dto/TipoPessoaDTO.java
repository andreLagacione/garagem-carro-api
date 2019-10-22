package com.andrelagacione.garagemcarroapi.dto;

import com.andrelagacione.garagemcarroapi.domain.TipoPessoa;

import java.io.Serializable;

public class TipoPessoaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String descricao;

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
}
