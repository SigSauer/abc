package com.sigsauer.univ.abc.models.exceptions;

public class LegalClientIsBlockedException extends RuntimeException{

    public LegalClientIsBlockedException(Long id) {
        super("Can't perform action, 'cause legal client with ID = "+id+" IS BLOCKED");
    }
}
