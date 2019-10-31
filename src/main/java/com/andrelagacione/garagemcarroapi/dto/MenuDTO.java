package com.andrelagacione.garagemcarroapi.dto;

import com.andrelagacione.garagemcarroapi.domain.Menu;

import java.io.Serializable;

public class MenuDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String url;
	private String icone;
	
	public MenuDTO() {}
	
	public MenuDTO(Menu menu) {
		id = menu.getId();
		nome = menu.getNome();
		url = menu.getUrl();
		icone = menu.getIcone();
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
