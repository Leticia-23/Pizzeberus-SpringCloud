package com.hiberus.services.impl;

import com.hiberus.clients.PizzaClient;
import com.hiberus.dto.PizzaDto;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.exceptions.PizzaReadMicroUnailable;
import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.models.User;
import com.hiberus.repositories.UserRepository;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PizzaServiceImpl implements PizzaService{

    @Autowired
    UserRepository userRepository;

    private final PizzaClient pizzaClient;

    @CircuitBreaker(name = "pizzaRead",fallbackMethod = "fallBackCheckFavPizza")
    public void checkFavPizzaForUser(String dni, Integer idPizza) throws UserNotFoundException, PizzaNotFoundException, PizzaReadMicroUnailable {

       /* try {*/
            User user = userRepository.findById(dni)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            /*ResponseEntity<PizzaDto> responseEntity = checkFavPizza(idPizza);*/

            ResponseEntity<PizzaDto> responseEntity  = pizzaClient.checkFavPizza(idPizza);

            PizzaDto pizza = responseEntity.getBody();

            // Add pizza as favorite for user
            List<Integer> pizzas = user.getFavPizzas();
            if (pizza != null && (!pizzas.contains(pizza.getId()))) {
                pizzas.add(pizza.getId());
                user.setFavPizzas(pizzas);
            }
            userRepository.save(user);

        /*} catch (FeignException.NotFound e) {
            throw new PizzaNotFoundException("Pizza not found in pizza microservice");
        }*/
    }

    /*@CircuitBreaker(name = "pizzaRead",fallbackMethod = "fallBackCheckFavPizza")
    private ResponseEntity<PizzaDto> checkFavPizza(Integer idPizza) {
        log.info("Va a hacer feign client");
        return pizzaClient.checkFavPizza(idPizza);
    }*/


    private void fallBackCheckFavPizza(String dni, Integer idPizza, Throwable throwable) throws Throwable {
        log.error("Unhandled exception calling pizza-read microservice: " + throwable.getMessage());

        if (throwable instanceof FeignException.NotFound) {
            throw new PizzaNotFoundException("Pizza not found in pizza microservice");
        } else if (throwable instanceof ConnectException) {
            throw new PizzaReadMicroUnailable("pizza-read microservice not up");
        } else {
            throw throwable;
        }

    }

    public  void uncheckFavPizzaForUser(String dni, Integer idPizza) throws UserNotFoundException, PizzaNotFoundException {
        User user = userRepository.findById(dni).orElseThrow(UserNotFoundException::new);
        List<Integer> pizzas = user.getFavPizzas();

        if (pizzas.contains(idPizza)) {
            pizzas.remove(idPizza);
            user.setFavPizzas(pizzas);
        } else {
            throw new PizzaNotFoundException();
        }
        userRepository.save(user);
    }
}
