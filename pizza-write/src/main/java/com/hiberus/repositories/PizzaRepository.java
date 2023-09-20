package com.hiberus.repositories;

import com.hiberus.models.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza,Integer> {
    List<Pizza> findByIdUsuario(Integer idUsuario);
}