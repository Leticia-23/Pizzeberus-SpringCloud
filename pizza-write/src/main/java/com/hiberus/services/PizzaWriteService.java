package com.hiberus.services;

import com.hiberus.exceptions.PizzaAlreadyExistsException;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.models.Pizza;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PizzaWriteService {

    void savePizza(Pizza pizza) throws PizzaAlreadyExistsException;
    void updatePizza(Integer id, Pizza pizza) throws PizzaNotFoundException;

}
