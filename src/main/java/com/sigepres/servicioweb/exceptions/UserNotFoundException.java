package com.sigepres.servicioweb.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("El usuario ingresado no existe.");
    }
}
