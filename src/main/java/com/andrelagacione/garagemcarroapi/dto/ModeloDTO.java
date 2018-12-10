package com.andrelagacione.garagemcarroapi.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.andrelagacione.garagemcarroapi.domain.Modelo;

public class ModeloDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório.")
	@Length(min=3, max=50, message="O tamanho deve ser entre 3 e 50 caractéres.")
	private String nome;
	
	@NotEmpty(message="Selecione uma marca.")
	private Integer marca;
	
	public ModeloDTO() {
	}

	public ModeloDTO(Modelo modelo) {
		this.id = modelo.getId();
		this.nome = modelo.getNome();
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

	public Integer getMarca() {
		return marca;
	}

	public void setMarca(Integer marca) {
		this.marca = marca;
	}

}
