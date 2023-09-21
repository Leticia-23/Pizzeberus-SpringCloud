package com.hiberus.services;

import com.hiberus.exceptions.UserAlreadyExistsException;
import com.hiberus.models.User;


import java.util.List;

public interface UserService {

    void saveUser(User user) throws UserAlreadyExistsException;
    List<User> getUsers();
}
