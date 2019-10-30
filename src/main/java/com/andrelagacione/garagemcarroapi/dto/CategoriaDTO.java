package com.andrelagacione.garagemcarroapi.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.andrelagacione.garagemcarroapi.domain.Veiculo;
import org.hibernate.validator.constraints.Length;

import com.andrelagacione.garagemcarroapi.domain.Categoria;

public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório.")
	@Length(min=3, max=20, message="O tamanho deve ser entre 3 e 20 caractéres.")
	private String nome;

	private List<Veiculo> veiculos = new ArrayList<>();
	
	public CategoriaDTO() {}
	
	public CategoriaDTO(Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
		this.veiculos = categoria.getVeiculos();
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

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos=veiculos;
	}
}
