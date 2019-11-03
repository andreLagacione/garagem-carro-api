package com.andrelagacione.garagemcarroapi.dto;

import com.andrelagacione.garagemcarroapi.domain.TipoVeiculo;

import javax.validation.constraints.NotEmpty;

public class TipoVeiculoDTO {

    private Integer id;

    @NotEmpty(message = "Informe um nome!")
    private String nome;

    public TipoVeiculoDTO() {};

    public TipoVeiculoDTO(TipoVeiculo tipoVeiculo) {
        this.id = tipoVeiculo.getId();
        this.nome = tipoVeiculo.getNome();
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
}
