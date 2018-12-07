package com.andrelagacione.garagemcarroapi.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.andrelagacione.garagemcarroapi.domain.Marca;

public class MarcaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório.")
	@Length(min=3, max=20, message="O tamanho deve ser entre 3 e 20 caractéres.")
	private String nome;
	
	public MarcaDTO() {}

	public MarcaDTO(Marca marca) {
		this.id = marca.getId();
		this.nome = marca.getNome();
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
