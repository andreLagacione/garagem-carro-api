package com.andrelagacione.garagemcarroapi.repositories;

import com.andrelagacione.garagemcarroapi.domain.TipoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPessoaRepository extends JpaRepository<TipoPessoa, Integer> {
}
