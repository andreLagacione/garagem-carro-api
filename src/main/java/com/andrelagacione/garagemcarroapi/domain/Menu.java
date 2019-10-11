package com.andrelagacione.garagemcarroapi.domain;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "menu")
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "url")
	private String url;

	@Column(name = "icone")
	private String icone;
	
	public Menu() {}
	
	public Menu(Integer id, String nome, String url, String icone) {
		super();
		this.id = id;
		this.nome = nome;
		this.url = url;
		this.icone = icone;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcone() {
		return icone;
	}

	public void setIcone(String icone) {
		this.icone = icone;
	}
}
