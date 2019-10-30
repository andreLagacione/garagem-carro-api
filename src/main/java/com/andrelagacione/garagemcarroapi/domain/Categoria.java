package com.andrelagacione.garagemcarroapi.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@ManyToMany(mappedBy = "categorias")
	private List<Veiculo> veiculos = new ArrayList<>();
	
	public Categoria() {}

	public Categoria(Integer id, String nome, List<Veiculo> veiculos) {
		this.id = id;
		this.nome = nome;
		this.veiculos = veiculos;
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
