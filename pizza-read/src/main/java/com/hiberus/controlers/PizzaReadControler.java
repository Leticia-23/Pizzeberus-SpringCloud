package com.hiberus.controlers;

import com.hiberus.dto.PizzaDto;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.mappers.PizzaMapper;
import com.hiberus.services.PizzaReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pizzaRead")
public class PizzaReadControler {

    @Autowired
    PizzaReadService pizzaReadService;

    @Autowired
    PizzaMapper pizzaMapper;

    @GetMapping
    public ResponseEntity<List<PizzaDto>> getPizzas(){
        List<PizzaDto> list = pizzaReadService.findPizzas()
                .stream()
                .map(pizza -> pizzaMapper.modelToDto(pizza))
                .toList();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PizzaDto> getPizzaById(@PathVariable Integer id){
        try {
            PizzaDto pizza = pizzaMapper.modelToDto(pizzaReadService.findPizza(id));
            return new ResponseEntity<>(pizza, HttpStatus.OK);
        }
        catch (PizzaNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
