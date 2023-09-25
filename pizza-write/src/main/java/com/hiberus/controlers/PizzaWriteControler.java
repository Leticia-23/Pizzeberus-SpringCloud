package com.hiberus.controlers;

import com.hiberus.dto.PizzaWDto;
import com.hiberus.exceptions.PizzaAlreadyExistsException;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.mappers.PizzaMapper;
import com.hiberus.services.PizzaWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/pizzaWrite")
public class PizzaWriteControler {

    @Autowired
    PizzaWriteService pizzaWriteService;

    @Autowired
    PizzaMapper pizzaMapper;

    @PostMapping
    public ResponseEntity<PizzaWDto> savePizza(@RequestBody PizzaWDto pizza){
        try {
            pizzaWriteService.savePizza(pizzaMapper.dtoToModel(pizza));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (PizzaAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PizzaWDto> updatePizza(@PathVariable Integer id, @RequestBody PizzaWDto pizza){
        try {
            pizzaWriteService.updatePizza(id, pizzaMapper.dtoToModel(pizza));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (PizzaNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
