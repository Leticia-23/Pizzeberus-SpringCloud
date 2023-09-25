package com.hiberus.services.impl;

import com.hiberus.dto.UserDto;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.exceptions.UserNotFoundException;

public interface PizzaService {

    UserDto checkFavPizzaForUser(String dni, Integer idPizza) throws UserNotFoundException, PizzaNotFoundException;
}
