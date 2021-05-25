package com.sigsauer.univ.abc.models.exceptions;

public class NaturalClientIsBlockedException extends RuntimeException{

    public NaturalClientIsBlockedException(Long id) {
        super("Can't perform action, 'cause natural client with ID = "+id+" IS BLOCKED");
    }
}
