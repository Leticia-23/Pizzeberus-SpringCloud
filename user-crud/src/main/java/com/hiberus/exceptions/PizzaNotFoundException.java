package com.hiberus.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PizzaNotFoundException extends Exception {
    public PizzaNotFoundException(String message) {
        super(message);
    }
}
