package com.andrelagacione.garagemcarroapi.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.andrelagacione.garagemcarroapi.domain.Categoria;
import com.andrelagacione.garagemcarroapi.domain.Marca;

public class NewVeiculoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message = "O valor precisa ser informado.")
	private Double valor;
	
	@NotEmpty(message = "A cor precisa ser informada.")
	@Length(min=3, max=20, message="O tamanho deve ser entre 3 e 20 caractéres.")
	private String cor;
	
	private Double cavalos;
	private Double cilindradas;
	private Integer portas;
	private String modelo;
	private String descricao;
	private List<Categoria> categorias;
	private Marca marca;
	
	@NotEmpty(message = "Selecione uma categoria.")
	private List<Integer> idCategoria;
	
	@NotEmpty(message = "Selecione uma marca.")
	private Integer idMarca;
	
	public NewVeiculoDTO() {}

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

	public List<Integer> getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(List<Integer> idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Integer getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(Integer idMarca) {
		this.idMarca = idMarca;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

}
