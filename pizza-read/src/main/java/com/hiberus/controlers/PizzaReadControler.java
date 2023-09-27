package com.hiberus.controlers;

import com.hiberus.dto.PizzaDto;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.mappers.PizzaMapper;
import com.hiberus.services.PizzaReadService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "List all pizzas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pizzas successfully obtained"),
            @ApiResponse(code = 404, message = "Pizzas not found"),
    })
    @GetMapping
    public ResponseEntity<List<PizzaDto>> getPizzas(){
        List<PizzaDto> list = pizzaReadService.findPizzas()
                .stream()
                .map(pizza -> pizzaMapper.modelToDto(pizza))
                .toList();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @ApiOperation(value = "Get concrete pizza by idPizza")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pizza successfully obtained"),
            @ApiResponse(code = 404, message = "Pizza not found"),
    })
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
