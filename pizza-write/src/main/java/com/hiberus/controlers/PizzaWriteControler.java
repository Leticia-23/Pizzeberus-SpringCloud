package com.hiberus.controlers;

import com.hiberus.dto.PizzaDto;
import com.hiberus.exceptions.PizzaAlreadyExistsException;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.mappers.PizzaMapper;
import com.hiberus.models.Pizza;
import com.hiberus.services.PizzaWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/pizzaWrite")
public class PizzaWriteControler {

    @Autowired
    PizzaWriteService pizzaWriteService;

    @Autowired
    PizzaMapper pizzaMapper;

    @PostMapping
    public ResponseEntity<PizzaDto> savePizza(@RequestBody PizzaDto pizza){
        try {
            pizzaWriteService.savePizza(pizzaMapper.dtoToModel(pizza));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (PizzaAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PizzaDto> updatePizza(@PathVariable Integer id, @RequestBody PizzaDto pizza){
        try {
            pizzaWriteService.updatePizza(id, pizzaMapper.dtoToModel(pizza));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (PizzaNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
