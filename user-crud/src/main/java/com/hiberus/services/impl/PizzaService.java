package com.hiberus.services.impl;

import com.hiberus.dto.UserDto;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.exceptions.UserNotFoundException;

public interface PizzaService {

    void checkFavPizzaForUser(String dni, Integer idPizza) throws UserNotFoundException, PizzaNotFoundException;

    void uncheckFavPizzaForUser(String dni, Integer idPizza) throws UserNotFoundException, PizzaNotFoundException;
}
