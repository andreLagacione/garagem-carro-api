package com.andrelagacione.garagemcarroapi.services;

import com.andrelagacione.garagemcarroapi.domain.TipoPessoa;
import com.andrelagacione.garagemcarroapi.repositories.TipoPessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoPessoaService {
    @Autowired
    private TipoPessoaRepository tipoPessoaRepository;

    public List<TipoPessoa> findAll() {
        return this.tipoPessoaRepository.findAll();
    }
}
