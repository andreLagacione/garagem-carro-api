package com.andrelagacione.garagemcarroapi.services;

import com.andrelagacione.garagemcarroapi.domain.Categoria;
import com.andrelagacione.garagemcarroapi.dto.CategoriaDTO;
import com.andrelagacione.garagemcarroapi.dto.PadraoMensagemRetorno;
import com.andrelagacione.garagemcarroapi.repositories.CategoriaRespository;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRespository categoriaRepository;
	
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> categorias = this.categoriaRepository.findAll();
		List<CategoriaDTO> categoriaDTO = categorias.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(categoriaDTO);
	}
	
	public ResponseEntity<Page<CategoriaDTO>> findPage(Integer page, Integer size, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		Page<Categoria> categorias = this.categoriaRepository.findAll(pageRequest);
		Page<CategoriaDTO> categoriaDTO = categorias.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(categoriaDTO);
	}
	
	public ResponseEntity<Categoria> find(Integer id) throws ObjectNotFoundException {
		Categoria categoria = this.findCategoria(id);
		return ResponseEntity.ok().body(categoria);
	}

	public Categoria findCategoria(Integer id) {
		Optional<Categoria> categoria = this.categoriaRepository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada!"));
	}
	
	private Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return this.categoriaRepository.save(categoria);
	}
	
	public Categoria update(Categoria categoria) throws ObjectNotFoundException {
		Categoria newCategoria = findCategoria(categoria.getId());
		this.updateData(newCategoria, categoria);
		return this.categoriaRepository.save(newCategoria);
	}
	
	public ResponseEntity<PadraoMensagemRetorno> delete(Integer id) throws ObjectNotFoundException {
		PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno();
		find(id);

		try {
			this.categoriaRepository.deleteById(id);
			mensagemRetorno.setHttpStatus(HttpStatus.OK);
			mensagemRetorno.setHttpStatusCode(HttpStatus.valueOf("OK").value());
			mensagemRetorno.setMensagem("Categoria removida com sucesso!");
			return ResponseEntity.ok(mensagemRetorno);
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.ok().build();
		}
	}
	
	public Categoria fromDto(CategoriaDTO categoriaDTO) {
		return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
	}
	
	public void updateData(Categoria newCategoria, Categoria categoria) {
		newCategoria.setNome(categoria.getNome());
	}

	public ResponseEntity<PadraoMensagemRetorno> salvarRegistro(CategoriaDTO categoriaDTO, Boolean adicionar) {
		Categoria categoria= this.fromDto(categoriaDTO);
		PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno();

		if (adicionar) {
			this.insert(categoria);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
			mensagemRetorno.setHttpStatus(HttpStatus.CREATED);
			mensagemRetorno.setHttpStatusCode(HttpStatus.valueOf("CREATED").value());
			mensagemRetorno.setMensagem("Categoria adicionada com sucesso!");
			return ResponseEntity.created(uri).body(mensagemRetorno);
		}

		this.update(categoria);
		mensagemRetorno.setHttpStatus(HttpStatus.OK);
		mensagemRetorno.setHttpStatusCode(HttpStatus.valueOf("OK").value());
		mensagemRetorno.setMensagem("Categoria editada com sucesso!");
		return ResponseEntity.ok(mensagemRetorno);

	}
}
