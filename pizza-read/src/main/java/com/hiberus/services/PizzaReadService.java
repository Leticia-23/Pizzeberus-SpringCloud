package com.hiberus.services;

import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.models.Pizza;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PizzaReadService {

    List<Pizza> findPizzas();

    Pizza findPizza(Integer id) throws PizzaNotFoundException;
}
