package com.andrelagacione.garagemcarroapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andrelagacione.garagemcarroapi.domain.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

}
