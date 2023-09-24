package com.hiberus.services.impl;

import com.hiberus.exceptions.PizzaNotFoundException;
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

    public List<Pizza> findPizzas(){
        return pizzaRepository.findAll();
    }

    public Pizza findPizza(Integer id) throws PizzaNotFoundException {
        return pizzaRepository.findById(id).orElseThrow(PizzaNotFoundException::new);
    }
}
