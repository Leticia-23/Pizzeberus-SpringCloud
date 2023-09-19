package com.hiberus.controlers;

import com.hiberus.dto.PizzaDto;
import com.hiberus.models.Pizza;
import com.hiberus.services.PizzaReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/prendas")
public class PizzaWriteControler {

    @Autowired
    PizzaReadService pizzaReadService;

    @GetMapping(value = "/obtenerPrendas")
    ResponseEntity<List<PizzaDto>> obtenerPrendas(){
        List<Pizza> listaPizzas = pizzaReadService.obtenerPrendas();
        List<PizzaDto> listaPrendasDto = new ArrayList<>();
        for(Pizza pizza : listaPizzas){
            PizzaDto pizzaDto = PizzaDto.builder()
                    .id(pizza.getId())
                    .nombre(pizza.getNombre())
                    .talla(pizza.getTalla())
                    .color(pizza.getColor())
                    .idUsuario(pizza.getIdUsuario())
                    .build();
            listaPrendasDto.add(pizzaDto);
        }
        return new ResponseEntity<>(listaPrendasDto, HttpStatus.OK);
    }

    @GetMapping(value = "/obtenerPrendasPorUsuario")
    ResponseEntity<List<PizzaDto>> obtenerPrendasPorUsuario(@RequestParam Integer idUsuario){
        List<Pizza> listaPizzas = pizzaReadService.obtenerPrendasPorIdUsuario(idUsuario);
        List<PizzaDto> listaPrendasDto = new ArrayList<>();
        for(Pizza pizza : listaPizzas){
            PizzaDto pizzaDto = PizzaDto.builder()
                    .id(pizza.getId())
                    .nombre(pizza.getNombre())
                    .talla(pizza.getTalla())
                    .color(pizza.getColor())
                    .idUsuario(pizza.getIdUsuario())
                    .build();
            listaPrendasDto.add(pizzaDto);
        }
        return new ResponseEntity<>(listaPrendasDto, HttpStatus.OK);
    }

}
