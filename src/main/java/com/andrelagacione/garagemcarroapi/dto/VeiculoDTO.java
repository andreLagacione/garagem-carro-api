package com.andrelagacione.garagemcarroapi.dto;

import com.andrelagacione.garagemcarroapi.domain.Categoria;
import com.andrelagacione.garagemcarroapi.domain.Modelo;
import com.andrelagacione.garagemcarroapi.domain.Veiculo;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

public class VeiculoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;

	@NotEmpty(message = "Informe um valor")
	private Double valor;

	@NotEmpty(message = "Informe uma cor")
	@Size(min = 3, max = 30, message = "O tamanho tem que estar entre 3 e 30 caractéres")
	private String cor;
	private Double cavalos;
	private Double cilindradas;
	private Integer portas;

	@NotEmpty(message = "Informe uma descrição")
	@Size(min = 3, max = 500, message = "O tamanho tem que estar entre 3 e 500 caractéres")
	private String descricao;

	private List<Categoria> categorias;

	@NotEmpty(message = "Informe pelo menos uma categoria")
	@Length(min = 1)
	private List<Integer> idCategorias;

	@NotNull(message = "Selecione o modelo.")
	private Integer idModelo;
	private Modelo modelo;

	public VeiculoDTO() {}
	
	public VeiculoDTO(Veiculo veiculo) {
		id = veiculo.getId();
		valor = veiculo.getValor();
		cor = veiculo.getCor();
		cavalos = veiculo.getCavalos();
		cilindradas = veiculo.getCilindradas();
		portas = veiculo.getPortas();
		descricao = veiculo.getDescricao();
		categorias = veiculo.getCategorias();
		modelo = veiculo.getModelo();
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

	public void setCategorias(List<Categoria> categoria) {
		this.categorias = categoria;
	}
	
	public List<Integer> getIdCategorias() {
		return idCategorias;
	}

	public void setIdCategorias(List<Integer> idCategorias) {
		this.idCategorias = idCategorias;
	}

	public Integer getIdModelo() {
		return idModelo;
	}

	public void setIdModelo(Integer idModelo) {
		this.idModelo = idModelo;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}
}
