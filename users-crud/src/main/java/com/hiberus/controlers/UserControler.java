package com.hiberus.controlers;

import com.hiberus.dto.UserDto;
import com.hiberus.models.User;
import com.hiberus.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UserControler {

    @Autowired
    UserService userService;

    @GetMapping(value = "/obtenerUsuarios")
    public ResponseEntity <List<UserDto>> getUsers(){
        List<User> listUsers = userService.getUsers();
        List<UserDto> listUsersDto = new ArrayList<>();
        for(User user : listUsers){
            UserDto userDTO = UserDto.builder()
                    .id(user.getId())
                    .nombre(user.getNombre())
                    .apellidos(user.getApellidos())
                    .email(user.getEmail())
                    .build();
            listUsersDto.add(userDTO);
        }
            return new ResponseEntity<>(listUsersDto, HttpStatus.OK);
    }

}
