package com.andrelagacione.garagemcarroapi.services;

import java.util.List;
import java.util.stream.Collectors;

import com.andrelagacione.garagemcarroapi.dto.MenuDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andrelagacione.garagemcarroapi.domain.Menu;
import com.andrelagacione.garagemcarroapi.repositories.MenuRepository;

@Service
public class MenuService {
	@Autowired
	private MenuRepository menuRepository;
	
	public List<MenuDTO> findAll() {
		List<Menu> itensMenu = this.menuRepository.findAll();
		List<MenuDTO> menuDTO = itensMenu.stream().map(obj -> new MenuDTO(obj)).collect(Collectors.toList());
		return menuDTO;
	}
}
