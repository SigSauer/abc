package com.sigsauer.univ.abc.models.exceptions;

public class NaturalClientAlreadyExistException extends RuntimeException{

    public NaturalClientAlreadyExistException(String field, String value) {
        super("Natural client already exist with "+ field.toUpperCase() + " = " + value);
    }
}
