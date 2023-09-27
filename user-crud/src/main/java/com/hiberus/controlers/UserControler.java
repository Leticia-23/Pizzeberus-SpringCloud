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
import com.hiberus.services.PizzaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

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

    private Gson gson = new Gson();

    @ApiOperation(value = "Create user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User created successfully"),
            @ApiResponse(code = 409, message = "User already exist")
    })
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

    @ApiOperation(value = "List all users")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Users successfully obtained"),
            @ApiResponse(code = 404, message = "Users not found"),
    })
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(){
        List<UserDto> list = userService.findUsers()
                .stream()
                .map(user -> userMapper.modelToDto(user))
                .toList();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @ApiOperation(value = "Get concrete user by DNI")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User successfully obtained"),
            @ApiResponse(code = 404, message = "User not found"),
    })
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

    @ApiOperation(value = "Update user", notes = "In this case just modify name")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User successfully updated"),
            @ApiResponse(code = 404, message = "User not found"),
    })
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

    @ApiOperation(value = "Delete concrete user by DNI")
    @ApiResponses({
            @ApiResponse(code = 204, message = "User successfully deleted"),
            @ApiResponse(code = 404, message = "User or pizza not found")
    })
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
    @ApiOperation(value = "Check pizza as favorite for concrete user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User successfully updated - checked pizza as favorite"),
            @ApiResponse(code = 404, message = "User or pizza not found"),
            @ApiResponse(code = 503, message = "Service Unavailable"),
    })
    @PatchMapping(value = "/checkFavPizza")
    public ResponseEntity<String> checkFavPizzaForUser(@RequestParam String dni, @RequestParam Integer idPizza){
        try {
            pizzaService.checkFavPizzaForUser(dni, idPizza);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (UserNotFoundException | PizzaNotFoundException e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()),HttpStatus.NOT_FOUND);
        } catch ( PizzaReadMicroUnailable e) {
            return new ResponseEntity<>(gson.toJson(e.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @ApiOperation(value = "Uncheck pizza as favorite for concrete user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User successfully updated - unchecked pizza as favorite"),
            @ApiResponse(code = 404, message = "User or pizza not found"),
    })
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
