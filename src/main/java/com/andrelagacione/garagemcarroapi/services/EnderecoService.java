package com.andrelagacione.garagemcarroapi.services;

import com.andrelagacione.garagemcarroapi.domain.Cidade;
import com.andrelagacione.garagemcarroapi.domain.Endereco;
import com.andrelagacione.garagemcarroapi.dto.EnderecoDTO;
import com.andrelagacione.garagemcarroapi.dto.PadraoMensagemRetorno;
import com.andrelagacione.garagemcarroapi.repositories.CidadeRepository;
import com.andrelagacione.garagemcarroapi.repositories.EnderecoRepository;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

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
                enderecoDTO.getIdPessoa(),
                enderecoDTO.getIdCidade()
        );
    }

    public void updateData(Endereco newEndereco, Endereco endereco) {
        newEndereco.setLogradouro(endereco.getLogradouro());
        newEndereco.setNumero(endereco.getNumero());
        newEndereco.setComplemento(endereco.getComplemento());
        newEndereco.setBairro(endereco.getBairro());
        newEndereco.setCep(endereco.getCep());
        newEndereco.setApelido(endereco.getApelido());
        newEndereco.setIdPessoa(endereco.getIdPessoa());
        newEndereco.setIdCidade(endereco.getIdCidade());
    }

    public ResponseEntity<PadraoMensagemRetorno> validarDados(EnderecoDTO enderecoDTO, Boolean adicionar) {
        Optional<Cidade> cidade = this.cidadeRepository.findById(enderecoDTO.getIdCidade());

        if (!cidade.isPresent()) {
            PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.NOT_FOUND, HttpStatus.valueOf("NOT_FOUND").value(), "Cidade não econtrada!");
            return ResponseEntity.badRequest().body(mensagemRetorno);
        }

        Endereco endereco = this.fromDto(enderecoDTO);

        if (adicionar) {
            return adicionarEndereco(endereco);
        }

        return this.editarEndereco(endereco);
    }

    private ResponseEntity<PadraoMensagemRetorno> adicionarEndereco(Endereco endereco) {
        this.insert(endereco);
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.CREATED, HttpStatus.valueOf("CREATED").value(), "Endereço adicionado com sucesso!");
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(endereco.getId()).toUri();

        return ResponseEntity.created(uri).body(mensagemRetorno);
    }

    private ResponseEntity<PadraoMensagemRetorno> editarEndereco(Endereco endereco) {
        this.update(endereco);
        PadraoMensagemRetorno mensagemRetorno = new PadraoMensagemRetorno(HttpStatus.OK, HttpStatus.valueOf("OK").value(), "Endereço editado com sucesso!");
        return ResponseEntity.ok(mensagemRetorno);
    }
}
