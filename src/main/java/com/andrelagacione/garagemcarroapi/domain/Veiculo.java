package com.andrelagacione.garagemcarroapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "veiculo")
public class Veiculo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "valor", nullable = false)
	private Double valor;

	@Column(name = "cor", nullable = false)
	private String cor;

	@Column(name = "cavalos", nullable = true)
	private Double cavalos;

	@Column(name = "cilindradas", nullable = true)
	private Double cilindradas;

	@Column(name = "portas", nullable = true)
	private Integer portas;

	@Column(name = "descricao", nullable = false)
	private String descricao;

	@ManyToOne
	@JoinColumn(name="modelo_id", nullable = false)
	private Modelo modelo;

	@ManyToOne
	@JoinColumn(name = "tipo_veiculo_id", nullable = false)
	private TipoVeiculo tipoVeiculo;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(
		name = "VEICULO_CATEGORIA",
		joinColumns = @JoinColumn(name = "veiculo_id"),
		inverseJoinColumns = @JoinColumn(name = "categoria_id")
	)
	private List<Categoria> categorias = new ArrayList<>();
	
	public Veiculo() {}

	public Veiculo(Integer id, Double valor, String cor, Double cavalos, Double cilindradas, Integer portas, String descricao, List<Categoria> categorias, Modelo modelo, TipoVeiculo tipoVeiculo) {
		this.id = id;
		this.valor = valor;
		this.cor = cor;
		this.cavalos = cavalos;
		this.cilindradas = cilindradas;
		this.portas = portas;
		this.descricao = descricao;
		this.categorias = categorias;
		this.modelo = modelo;
		this.tipoVeiculo = tipoVeiculo;
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

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public TipoVeiculo getTipoVeiculo() {
		return tipoVeiculo;
	}

	public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}
}
