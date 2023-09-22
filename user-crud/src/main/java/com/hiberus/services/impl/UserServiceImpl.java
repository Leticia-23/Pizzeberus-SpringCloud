package com.hiberus.services.impl;

import com.hiberus.exceptions.UserAlreadyExistsException;
import com.hiberus.exceptions.UserNotFoundException;
import com.hiberus.models.User;
import com.hiberus.repositories.UserRepository;
import com.hiberus.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
     @Autowired
     UserRepository userRepository;

    public void saveUser(User user) throws UserAlreadyExistsException {
        if (userRepository.existsById(user.getDni())) {
            throw new UserAlreadyExistsException();
        }
        userRepository.save(user);
    }
    public List<User> findUsers(){
        return userRepository.findAll();
    }

    public User findUser(String dni) throws UserNotFoundException {
        return userRepository.findById(dni).orElseThrow(UserNotFoundException::new);
    }

    public void updateUser(String dni, User user) throws UserNotFoundException {
        if (!userRepository.existsById(dni)) {
            throw new UserNotFoundException();
        }

        // TODO: this is not working well
        //If try update fav pizzas, it will not be possible
        User oldUser = findUser(dni);
        List<Integer> pizzas = oldUser.getFavPizzas();
        user.setFavPizzas(pizzas);
        userRepository.save(user);
    }

   /* public void updateUser(String dni, String name) throws UserNotFoundException {
        if (!userRepository.existsById(dni)) {
            throw new UserNotFoundException();
        }

        User oldUser = findUser(dni);
        // TODO: correct this put name as json body
        oldUser.setName(name);
        userRepository.save(oldUser);
    }*/
    public void deleteUser(String dni) {
        // TODO: hacer alguna comprobación si no existe para indicar que no se está elimiando ?
        if (userRepository.existsById(dni)) {
            userRepository.deleteById(dni);
        }
    }
}
