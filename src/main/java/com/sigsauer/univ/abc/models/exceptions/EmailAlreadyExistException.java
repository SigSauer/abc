package com.sigsauer.univ.abc.models.exceptions;

public class EmailAlreadyExistException extends RuntimeException{

    public EmailAlreadyExistException(String email) {
        super("User already exist with EMAIL = " + email);
    }

}
