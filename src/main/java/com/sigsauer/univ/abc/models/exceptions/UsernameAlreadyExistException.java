package com.sigsauer.univ.abc.models.exceptions;

public class UsernameAlreadyExistException extends RuntimeException{

    public UsernameAlreadyExistException(String username) {
        super("User already exist with USERNAME = " + username);
    }

}
