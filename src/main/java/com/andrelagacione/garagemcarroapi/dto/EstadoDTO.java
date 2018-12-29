package com.andrelagacione.garagemcarroapi.dto;

import java.io.Serializable;

import com.andrelagacione.garagemcarroapi.domain.Estado;

public class EstadoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String sigla;
	
	public EstadoDTO() {}
	
	public EstadoDTO(Estado estado) {
		id = estado.getId();
		nome = estado.getNome();
		sigla = estado.getSigla();
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

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
}
