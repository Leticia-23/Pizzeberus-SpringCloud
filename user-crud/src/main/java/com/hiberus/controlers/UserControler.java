package com.hiberus.controlers;

import com.hiberus.dto.UserDto;
import com.hiberus.exceptions.UserAlreadyExistsException;
import com.hiberus.mappers.UserMapper;
import com.hiberus.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
