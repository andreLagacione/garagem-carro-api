package com.andrelagacione.garagemcarroapi.dto;

import java.io.Serializable;

import com.andrelagacione.garagemcarroapi.domain.Estado;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class EstadoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;

	@NotEmpty(message = "Campo obrigatório!")
	@Length(min = 3, max = 25, message = "O tamanho deve estar entre 3 e 25 caractéres!")
	private String nome;

	@NotEmpty(message = "Campo obrigatório!")
	@Length(min = 2, max = 2, message = "O tamanho deve ser 2 caractéres!")
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
