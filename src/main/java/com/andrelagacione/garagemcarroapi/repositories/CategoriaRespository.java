package com.andrelagacione.garagemcarroapi.repositories;

import com.andrelagacione.garagemcarroapi.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRespository extends JpaRepository<Categoria, Integer> {

}
