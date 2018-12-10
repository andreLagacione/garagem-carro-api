package com.andrelagacione.garagemcarroapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andrelagacione.garagemcarroapi.domain.Modelo;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Integer> {

}
