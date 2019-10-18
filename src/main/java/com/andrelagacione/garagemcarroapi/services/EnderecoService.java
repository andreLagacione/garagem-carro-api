package com.andrelagacione.garagemcarroapi.services;

import com.andrelagacione.garagemcarroapi.domain.Endereco;
import com.andrelagacione.garagemcarroapi.dto.EnderecoDTO;
import com.andrelagacione.garagemcarroapi.repositories.EnderecoRepository;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public class EnderecoService {
    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<Endereco> findAll() {
        return enderecoRepository.findAll();
    }

    public Page<Endereco> findPage(Integer page, Integer size, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        return enderecoRepository.findAll(pageRequest);
    }

    public Endereco find(Integer id) throws ObjectNotFoundException {
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        return endereco.orElseThrow(() -> new ObjectNotFoundException("Endereço não encontrado!"));
    }

    public Endereco insert(Endereco endereco) {
        endereco.setId(null);
        return enderecoRepository.save(endereco);
    }

    public Endereco update(Endereco endereco) throws ObjectNotFoundException {
        Endereco newEndereco = find(endereco.getId());
        updateData(newEndereco, endereco);
        return enderecoRepository.save(newEndereco);
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        find(id);

        try {
            enderecoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível excluir uma categoria que possui veículos");
        }
    }

    public Endereco fromDto(EnderecoDTO enderecoDTO) {
        return new Endereco(
                enderecoDTO.getLogradouro(),
                enderecoDTO.getNumero(),
                enderecoDTO.getComplemento(),
                enderecoDTO.getBairro(),
                enderecoDTO.getCep(),
                enderecoDTO.getCidade()
        );
    }

    public void updateData(Endereco newCategoria, Endereco categoria) {
        newCategoria.setLogradouro(categoria.getLogradouro());
        newCategoria.setNumero(categoria.getNumero());
        newCategoria.setComplemento(categoria.getComplemento());
        newCategoria.setBairro(categoria.getBairro());
        newCategoria.setCep(categoria.getCep());
        newCategoria.setCidade(categoria.getCidade());
    }
}
