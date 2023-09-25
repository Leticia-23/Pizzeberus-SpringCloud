package com.hiberus.services.impl;

import com.hiberus.clients.PizzaClient;
import com.hiberus.dto.UserDto;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

// Este es el servicio que tiene inyectado a través del @AllArgsConstructor al cliente
@Service("feign-pizzas")
@AllArgsConstructor
// @Slf4j
public class PizzaServiceImpl implements PizzaService{

    private final PizzaClient pizzaClient;

    // Para usar el CircuitBreaker, se le da un nombre y él método que debe ejecutar por defecto en caso de no respuesta (fallo)
   // @CircuitBreaker(name = "prendas",fallbackMethod = "fallBackObtenerPrendasPorUsuario")
    // Se utiliza como si se tuviera un objeto de java que llama al método declarado dentro del @FeignClient
    // pero por debajo hace una llamada al endpoint del otro microservicio
    @Override
    public UserDto checkFavPizzaForUser(String dni, Integer idPizza) throws UserNotFoundException, PizzaNotFoundException {
        return pizzaClient.checkFavPizzaForUser(dni, idPizza).getBody();
    }
}
