package com.andrelagacione.garagemcarroapi.repositories;

import com.andrelagacione.garagemcarroapi.domain.Pessoa;
import com.andrelagacione.garagemcarroapi.enums.TipoPessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT p FROM Pessoa p WHERE p.tipo_pessoa = :tipoPessoa ORDER BY p.nome")
    public Page<Pessoa> findPessoasPage(@Param("tipoPessoa")TipoPessoa tipoPessoa, Pageable pageable);
}
