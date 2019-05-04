package com.andrelagacione.garagemcarroapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andrelagacione.garagemcarroapi.domain.Menu;
import com.andrelagacione.garagemcarroapi.repositories.MenuRepository;

@Service
public class MenuService {
	@Autowired
	private MenuRepository menuRepository;
	
	public List<Menu> findAll() {
		return menuRepository.findAll();
	}
}
