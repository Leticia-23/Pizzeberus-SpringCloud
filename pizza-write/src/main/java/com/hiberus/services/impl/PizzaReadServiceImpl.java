package com.hiberus.services.impl;

import com.hiberus.models.Pizza;
import com.hiberus.repositories.PizzaRepository;
import com.hiberus.services.PizzaReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaReadServiceImpl implements PizzaReadService {

    @Autowired
    PizzaRepository pizzaRepository;

    @Override
    public List<Pizza> obtenerPrendas() {
        return pizzaRepository.findAll();
    }

    @Override
    public List<Pizza> obtenerPrendasPorIdUsuario(Integer idUsuario) {
        return pizzaRepository.findByIdUsuario(idUsuario);
    }
}
