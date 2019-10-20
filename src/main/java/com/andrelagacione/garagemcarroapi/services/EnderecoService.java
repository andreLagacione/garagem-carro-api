package com.andrelagacione.garagemcarroapi.services;

import com.andrelagacione.garagemcarroapi.domain.Endereco;
import com.andrelagacione.garagemcarroapi.dto.EnderecoDTO;
import com.andrelagacione.garagemcarroapi.repositories.EnderecoRepository;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {
    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<Endereco> findAll(Integer idPessoa) {
        return enderecoRepository.findEnderecos(idPessoa);
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
            throw new DataIntegrityViolationException("Não é possível excluir um endereço que possui pessoas vinculadas a ele.");
        }
    }

    public Endereco fromDto(EnderecoDTO enderecoDTO) {
        return new Endereco(
                enderecoDTO.getId(),
                enderecoDTO.getLogradouro(),
                enderecoDTO.getNumero(),
                enderecoDTO.getComplemento(),
                enderecoDTO.getBairro(),
                enderecoDTO.getCep(),
                enderecoDTO.getApelido(),
                enderecoDTO.getPessoa(),
                enderecoDTO.getCidade()
        );
    }

    public void updateData(Endereco newEndereco, Endereco endereco) {
        newEndereco.setLogradouro(endereco.getLogradouro());
        newEndereco.setNumero(endereco.getNumero());
        newEndereco.setComplemento(endereco.getComplemento());
        newEndereco.setBairro(endereco.getBairro());
        newEndereco.setCep(endereco.getCep());
        newEndereco.setApelido(endereco.getApelido());
        newEndereco.setPessoa(endereco.getPessoa());
        newEndereco.setCidade(endereco.getCidade());
    }
}
