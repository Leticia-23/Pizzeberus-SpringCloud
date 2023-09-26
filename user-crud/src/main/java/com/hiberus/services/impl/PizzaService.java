package com.hiberus.services.impl;

import com.hiberus.dto.UserDto;
import com.hiberus.exceptions.PizzaNotFoundException;
import com.hiberus.exceptions.PizzaReadMicroUnailable;
import com.hiberus.exceptions.UserNotFoundException;

public interface PizzaService {

    void checkFavPizzaForUser(String dni, Integer idPizza) throws UserNotFoundException, PizzaNotFoundException, PizzaReadMicroUnailable;

    void uncheckFavPizzaForUser(String dni, Integer idPizza) throws UserNotFoundException, PizzaNotFoundException;
}
