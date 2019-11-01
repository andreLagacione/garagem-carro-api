package com.andrelagacione.garagemcarroapi.services;

import com.andrelagacione.garagemcarroapi.domain.Cidade;
import com.andrelagacione.garagemcarroapi.domain.Endereco;
import com.andrelagacione.garagemcarroapi.dto.EnderecoDTO;
import com.andrelagacione.garagemcarroapi.repositories.CidadeRepository;
import com.andrelagacione.garagemcarroapi.repositories.EnderecoRepository;
import com.andrelagacione.garagemcarroapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnderecoService {
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    public List<EnderecoDTO> findAll(Integer idPessoa) {
        List<Endereco> enderecos = this.enderecoRepository.findEnderecos(idPessoa);
        List<EnderecoDTO> enderecosDTO = enderecos.stream().map(obj -> new EnderecoDTO(obj)).collect(Collectors.toList());
        return enderecosDTO;
    }

    public Endereco find(Integer id) throws ObjectNotFoundException {
        Optional<Endereco> endereco = this.enderecoRepository.findById(id);
        return endereco.orElseThrow(() -> new ObjectNotFoundException("Endereço não encontrado"));
    }

    private Endereco insert(Endereco endereco) {
        endereco.setId(null);
        return this.enderecoRepository.save(endereco);
    }

    private Endereco update(Endereco endereco) throws ObjectNotFoundException {
        Endereco newEndereco = this.find(endereco.getId());
        this.updateData(newEndereco, endereco);
        return this.enderecoRepository.save(newEndereco);
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            this.enderecoRepository.deleteById(id);
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro ao excluir endereço: ", e);
        }
    }

    private Endereco fromDto(EnderecoDTO enderecoDTO) {
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

    private void updateData(Endereco newEndereco, Endereco endereco) {
        newEndereco.setLogradouro(endereco.getLogradouro());
        newEndereco.setNumero(endereco.getNumero());
        newEndereco.setComplemento(endereco.getComplemento());
        newEndereco.setBairro(endereco.getBairro());
        newEndereco.setCep(endereco.getCep());
        newEndereco.setApelido(endereco.getApelido());
        newEndereco.setPessoa(endereco.getPessoa());
        newEndereco.setCidade(endereco.getCidade());
    }

    public Endereco validarDados(EnderecoDTO enderecoDTO, Boolean adicionar) throws ObjectNotFoundException {
        Optional<Cidade> cidade = this.cidadeRepository.findById(enderecoDTO.getCidade().getId());

        if (!cidade.isPresent()) {
            throw new ObjectNotFoundException("A cidade informada não foi encontrada! Por favor informe outra cidade.");
        }

        Endereco endereco = this.fromDto(enderecoDTO);

        if (adicionar) {
            return this.insert(endereco);
        }

        return this.update(endereco);
    }
}
