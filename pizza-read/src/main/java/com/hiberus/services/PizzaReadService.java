package com.hiberus.services;

import com.hiberus.models.Pizza;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PizzaReadService {

    List<Pizza> obtenerPrendas();

    List<Pizza> obtenerPrendasPorIdUsuario(Integer idUsuario);
}
