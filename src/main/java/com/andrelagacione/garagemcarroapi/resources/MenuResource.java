package com.andrelagacione.garagemcarroapi.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.andrelagacione.garagemcarroapi.domain.Menu;
import com.andrelagacione.garagemcarroapi.dto.MenuDTO;
import com.andrelagacione.garagemcarroapi.services.MenuService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/menu")
public class MenuResource {
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<MenuDTO>> findAll() {
		return ResponseEntity.ok().body(this.menuService.findAll());
	}
}
