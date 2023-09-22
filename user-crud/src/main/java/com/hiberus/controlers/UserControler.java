package com.hiberus.controlers;

import com.hiberus.dto.UserDto;
import com.hiberus.exceptions.UserAlreadyExistsException;
import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.mappers.UserMapper;
import com.hiberus.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserControler {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto user){
        try {
            userService.saveUser(userMapper.dtoToUser(user));
            return new ResponseEntity<>(user, HttpStatus.CREATED);
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

    @PutMapping(value = "/{dni}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String dni, @RequestBody UserDto user){
        try {
            userService.updateUser(dni, userMapper.dtoToUser(user));
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{dni}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable String dni){
        userService.deleteUser(dni);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
