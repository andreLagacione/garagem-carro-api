package com.andrelagacione.garagemcarroapi.dto;

import java.io.Serializable;
import java.util.List;

import com.andrelagacione.garagemcarroapi.domain.Categoria;
import com.andrelagacione.garagemcarroapi.domain.Marca;
import com.andrelagacione.garagemcarroapi.domain.Veiculo;

public class VeiculoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Double valor;
	private String cor;
	private Double cavalos;
	private Double cilindradas;
	private Integer portas;
	private String modelo;
	private String descricao;
	private List<Categoria> categorias;
	private Marca marca;
	
	public VeiculoDTO() {}
	
	public VeiculoDTO(Veiculo veiculo) {
		id = veiculo.getId();
		valor = veiculo.getValor();
		cor = veiculo.getCor();
		cavalos = veiculo.getCavalos();
		cilindradas = veiculo.getCilindradas();
		portas = veiculo.getPortas();
		modelo = veiculo.getModelo();
		descricao = veiculo.getDescricao();
		marca = veiculo.getMarca();
		categorias = veiculo.getCategorias();
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

	public void setCategorias(List<Categoria> categoria) {
		this.categorias = categoria;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marcas) {
		this.marca = marcas;
	}

}
