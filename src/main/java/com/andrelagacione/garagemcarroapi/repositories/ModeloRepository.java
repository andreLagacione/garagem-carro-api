package com.andrelagacione.garagemcarroapi.repositories;

import com.andrelagacione.garagemcarroapi.domain.Modelo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ModeloRepository extends JpaRepository<Modelo, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT m FROM Modelo m WHERE m.marca.id = :idMarca ORDER BY m.nome")
    public List<Modelo> findByMarca(@Param("idMarca") Integer idMarca);

    @Transactional(readOnly = true)
    @Query("SELECT m FROM Modelo m WHERE m.marca.id = :idMarca ORDER BY m.nome")
    public Page<Modelo> findByMarcaPageable(@Param("idMarca") Integer idMarca, Pageable pageable);
}
