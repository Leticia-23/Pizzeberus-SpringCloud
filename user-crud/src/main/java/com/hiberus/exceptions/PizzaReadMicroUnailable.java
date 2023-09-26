package com.hiberus.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PizzaReadMicroUnailable extends Exception {
    public PizzaReadMicroUnailable(String message) {
        super(message);
    }
}
