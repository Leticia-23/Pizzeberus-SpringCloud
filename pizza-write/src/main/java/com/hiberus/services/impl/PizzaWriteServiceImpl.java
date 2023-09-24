package com.hiberus.services.impl;

import com.hiberus.exceptions.PizzaAlreadyExistsException;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.models.Pizza;
import com.hiberus.repositories.PizzaRepository;
import com.hiberus.services.PizzaWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaWriteServiceImpl implements PizzaWriteService {

    @Autowired
    PizzaRepository pizzaRepository;

    public void savePizza(Pizza pizza) throws PizzaAlreadyExistsException {
        if (pizzaRepository.existsById(pizza.getId())) {
            throw new PizzaAlreadyExistsException();
        }
        pizzaRepository.save(pizza);
    }

    public void updatePizza(Integer id,  Pizza newPizza) throws PizzaNotFoundException {

        Pizza pizza = pizzaRepository.findById(id).orElseThrow(PizzaNotFoundException::new);

        pizza.setName(newPizza.getName());

        pizzaRepository.save(pizza);
    }
}
