package com.andrelagacione.garagemcarroapi.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Veiculo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Double valor;
	private String cor;
	private Double cavalos;
	private Double cilindradas;
	private Integer portas;
	private String modelo;
	private String descricao;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(
		name = "VEICULO_CATEGORIA",
		joinColumns = @JoinColumn(name = "veiculo_id"),
		inverseJoinColumns = @JoinColumn(name = "categoria_id")
	)
	private List<Categoria> categorias = new ArrayList<>();
	
	public Veiculo() {}

	public Veiculo(Integer id, Double valor, String cor, Double cavalos, Double cilindradas, Integer portas, String modelo, String descricao) {
		super();
		this.id = id;
		this.valor = valor;
		this.cor = cor;
		this.cavalos = cavalos;
		this.cilindradas = cilindradas;
		this.portas = portas;
		this.modelo = modelo;
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public Double getCavalos() {
		return cavalos;
	}

	public void setCavalos(Double cavalos) {
		this.cavalos = cavalos;
	}

	public Double getCilindradas() {
		return cilindradas;
	}

	public void setCilindradas(Double cilindradas) {
		this.cilindradas = cilindradas;
	}

	public Integer getPortas() {
		return portas;
	}

	public void setPortas(Integer portas) {
		this.portas = portas;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Veiculo other = (Veiculo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
