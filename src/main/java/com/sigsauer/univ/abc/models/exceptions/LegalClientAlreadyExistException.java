package com.sigsauer.univ.abc.models.exceptions;

public class LegalClientAlreadyExistException extends RuntimeException{

    public LegalClientAlreadyExistException(String field, String value) {
        super("Legal client already exist with "+ field.toUpperCase() + " = " + value);
    }
}
