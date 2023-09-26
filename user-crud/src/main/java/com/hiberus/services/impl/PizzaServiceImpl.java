package com.hiberus.services.impl;

import com.hiberus.clients.PizzaClient;
import com.hiberus.dto.PizzaDto;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.models.User;
import com.hiberus.repositories.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;


import java.util.List;

// Este es el servicio que tiene inyectado a través del @AllArgsConstructor al cliente
@Service("feign-pizzas")
@AllArgsConstructor
@Slf4j
public class PizzaServiceImpl implements PizzaService{

    @Autowired
    UserRepository userRepository;

    private final PizzaClient pizzaClient;

    @CircuitBreaker(name = "pizzaRead",fallbackMethod = "fallBackCheckFavPizzaForUser")
    @Override
    public void checkFavPizzaForUser(String dni, Integer idPizza) throws UserNotFoundException, PizzaNotFoundException {

        ResponseEntity<PizzaDto> responseEntity = pizzaClient.checkFavPizzaForUser(idPizza);

        if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new PizzaNotFoundException();
        } else {
            PizzaDto pizza = responseEntity.getBody();

            // Add pizza as favorite for user
            User user = userRepository.findById(dni).orElseThrow(UserNotFoundException::new);

            List<Integer> pizzas = user.getFavPizzas();
            if (pizza != null) {
                pizzas.add(pizza.getId());
                user.setFavPizzas(pizzas);
            }
            userRepository.save(user);
        }
    }

    private void fallBackCheckFavPizzaForUser(String dni, Integer idPizza, Throwable throwable) throws Throwable {
        log.error("Unhandled exception calling pizza-read microservice: " + throwable.getMessage());
        throw throwable;

        // TODO: ¿Qué sería lo más correcto hacer si no se encuentra el servicio de pizzas?
    }
}
