package com.andrelagacione.garagemcarroapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andrelagacione.garagemcarroapi.domain.Categoria;

@Repository
public interface CategoriaRespository extends JpaRepository<Categoria, Integer> {

}
