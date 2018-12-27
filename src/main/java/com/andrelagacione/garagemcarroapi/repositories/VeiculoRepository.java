package com.andrelagacione.garagemcarroapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andrelagacione.garagemcarroapi.domain.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Integer> {
	/*
	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Veiculo obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Veiculo> findByNome(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
	*/
}
