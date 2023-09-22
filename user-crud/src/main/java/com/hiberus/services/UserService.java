package com.hiberus.services;

import com.hiberus.exceptions.UserAlreadyExistsException;
import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.models.User;


import java.util.List;
import java.util.Map;

public interface UserService {

    void saveUser(User user) throws UserAlreadyExistsException;
    List<User> findUsers();

    User findUser(String dni) throws UserNotFoundException;

    void updateUser(String dni, User user) throws UserNotFoundException;

    void deleteUser(String dni) throws UserNotFoundException;
}
