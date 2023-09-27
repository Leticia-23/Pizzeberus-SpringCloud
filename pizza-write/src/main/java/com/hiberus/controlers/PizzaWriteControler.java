package com.hiberus.controlers;

import com.hiberus.dto.PizzaWDto;
import com.hiberus.exceptions.PizzaAlreadyExistsException;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.mappers.PizzaMapper;
import com.hiberus.services.PizzaWriteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Create pizza")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pizza created successfully"),
    })
    @PostMapping
    public ResponseEntity<PizzaWDto> savePizza(@RequestBody PizzaWDto pizza){

        pizzaWriteService.savePizza(pizzaMapper.dtoToModel(pizza));
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @ApiOperation(value = "Update pizza")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pizza successfully updated"),
            @ApiResponse(code = 404, message = "Pizza not found"),
    })
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
