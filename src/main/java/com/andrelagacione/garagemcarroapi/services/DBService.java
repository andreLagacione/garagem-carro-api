package com.andrelagacione.garagemcarroapi.services;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andrelagacione.garagemcarroapi.domain.Marca;
import com.andrelagacione.garagemcarroapi.repositories.MarcaRepository;

@Service
public class DBService {
	@Autowired
	private MarcaRepository marcaRepository;
	
	public void instantiateTestDataBase() throws ParseException {
		Marca marca1 = new Marca(1, "Ford");
		Marca marca2 = new Marca(2, "Chevrolet");
		marcaRepository.saveAll(Arrays.asList(marca1, marca2));
	}
}
