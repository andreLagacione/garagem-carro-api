package com.andrelagacione.garagemcarroapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andrelagacione.garagemcarroapi.domain.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Integer> {

}
