package com.andrelagacione.garagemcarroapi.services;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andrelagacione.garagemcarroapi.domain.Categoria;
import com.andrelagacione.garagemcarroapi.domain.Cidade;
import com.andrelagacione.garagemcarroapi.domain.Estado;
import com.andrelagacione.garagemcarroapi.domain.Marca;
import com.andrelagacione.garagemcarroapi.domain.Menu;
import com.andrelagacione.garagemcarroapi.domain.Veiculo;
import com.andrelagacione.garagemcarroapi.repositories.CategoriaRespository;
import com.andrelagacione.garagemcarroapi.repositories.CidadeRepository;
import com.andrelagacione.garagemcarroapi.repositories.EstadoRepository;
import com.andrelagacione.garagemcarroapi.repositories.MarcaRepository;
import com.andrelagacione.garagemcarroapi.repositories.MenuRepository;
import com.andrelagacione.garagemcarroapi.repositories.VeiculoRepository;

@Service
public class DBService {
	@Autowired
	private MarcaRepository marcaRepository;
	
	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@Autowired
	private CategoriaRespository categoriaRespository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private MenuRepository menuRepository;
	
	public void instantiateTestDataBase() throws ParseException {
		Categoria categoria1 = new Categoria(null, "Carros");
		Categoria categoria2 = new Categoria(null, "Esportivos(as)");
		
		Marca marca1 = new Marca(null, "Ford");
		Marca marca2 = new Marca(null, "Chevrolet");
		
		Veiculo veiculo1 = new Veiculo(null, 350550.0, "Preto", 350.0, null, 2, "Mustang GT", "Mustang GT preto");
		Veiculo veiculo2 = new Veiculo(null, 412541.0, "Vermelho", 501.0, null, 2, "Corvete", "Corvete vermelho");
		
		veiculo1.setMarca(marca1);
		veiculo1.getCategorias().addAll(Arrays.asList(categoria1, categoria2));
		veiculo2.setMarca(marca2);
		veiculo2.getCategorias().addAll(Arrays.asList(categoria1, categoria2));
		
		categoriaRespository.saveAll(Arrays.asList(categoria1, categoria2));
		marcaRepository.saveAll(Arrays.asList(marca1, marca2));
		veiculoRepository.saveAll(Arrays.asList(veiculo1, veiculo2));
		
		Estado estado1 = new Estado(null, "São Paulo", "SP");
		Estado estado2 = new Estado(null, "Santa Catarina", "SC");
		
		Cidade cidade1 = new Cidade(null, "Osvaldo Cruz", estado1);
		Cidade cidade2 = new Cidade(null, "São Paulo", estado1);
		Cidade cidade3 = new Cidade(null, "Blumenau", estado2);
		Cidade cidade4 = new Cidade(null, "Florianópolis", estado2);
		
		estado1.getCidades().addAll(Arrays.asList(cidade1, cidade2));
		estado2.getCidades().addAll(Arrays.asList(cidade3, cidade4));
		
		estadoRepository.saveAll(Arrays.asList(estado1, estado2));
		cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3, cidade4));
		
		Menu menu1 = new Menu(null, "Categorias", "/categorias", "fas fa-archive");
		Menu menu2 = new Menu(null, "Cidades", "/cidades", "fas fa-city");
		Menu menu3 = new Menu(null, "Estados", "/estados", "fas fa-map");
		Menu menu4 = new Menu(null, "Marcas", "/marcas", "fas fa-copyright");
		Menu menu5 = new Menu(null, "Veículos", "/veiculos", "fas fa-car");
		
		menuRepository.saveAll(Arrays.asList(menu1, menu2, menu3, menu4, menu5));
		
	}
}
