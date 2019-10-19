package com.andrelagacione.garagemcarroapi.repositories;

import com.andrelagacione.garagemcarroapi.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT e FROM Endereco e WHERE e.pessoa.id = :pessoaId ORDER BY e.apelido")
    public List<Endereco> findEnderecos(@Param("pessoaId") Integer pessoaId);
}
