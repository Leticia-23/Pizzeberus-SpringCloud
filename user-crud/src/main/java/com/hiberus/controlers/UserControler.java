package com.hiberus.controlers;

import com.hiberus.dto.PizzaDto;
import com.hiberus.dto.UserDto;
import com.hiberus.dto.UserInDto;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.exceptions.PizzaReadMicroUnailable;
import com.hiberus.exceptions.UserAlreadyExistsException;
import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.mappers.UserMapper;
import com.hiberus.services.UserService;
import com.hiberus.services.impl.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserControler {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PizzaService pizzaService;

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody UserInDto user){
        try {
            userService.saveUser(userMapper.dtoToUser(user));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(){
        List<UserDto> list = userService.findUsers()
                .stream()
                .map(user -> userMapper.modelToDto(user))
                .toList();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/{dni}")
    public ResponseEntity<UserDto> getUserByDni(@PathVariable String dni){
        try {
            UserDto user = userMapper.modelToDto(userService.findUser(dni));
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(value = "/{dni}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String dni, @RequestBody UserInDto user){
        try {
            userService.updateUser(dni, userMapper.dtoToUser(user));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{dni}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable String dni){
        try {
            userService.deleteUser(dni);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // users endpoint that it will call pizzas endpoint
    @PatchMapping(value = "/checkFavPizza")
    public ResponseEntity<String> checkFavPizzaForUser(@RequestParam String dni, @RequestParam Integer idPizza){
        try {
            pizzaService.checkFavPizzaForUser(dni, idPizza);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (UserNotFoundException | PizzaNotFoundException e) {
            return new ResponseEntity<>(e.toString(),HttpStatus.NOT_FOUND);
        } catch ( PizzaReadMicroUnailable e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PatchMapping(value = "/uncheckFavPizza")
    public ResponseEntity<PizzaDto> uncheckFavPizzaForUser(@RequestParam String dni, @RequestParam Integer idPizza){
        try {
            pizzaService.uncheckFavPizzaForUser(dni, idPizza);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (UserNotFoundException | PizzaNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
