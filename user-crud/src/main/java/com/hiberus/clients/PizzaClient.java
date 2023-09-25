package com.hiberus.clients;

import com.hiberus.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "pizzaWrite")
public interface PizzaClient {
        @PatchMapping(value = "/pizzaWrite/checkFavPizza")
        ResponseEntity<UserDto> checkFavPizzaForUser(@RequestParam String dni, @RequestParam Integer idPizza);
}
