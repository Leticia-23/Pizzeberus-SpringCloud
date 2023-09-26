package com.hiberus.clients;

import com.hiberus.dto.PizzaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "pizzaRead")
public interface PizzaClient {
        @GetMapping(value = "/pizzaRead")
        ResponseEntity<PizzaDto> checkFavPizzaForUser(@RequestParam Integer idPizza);
}
