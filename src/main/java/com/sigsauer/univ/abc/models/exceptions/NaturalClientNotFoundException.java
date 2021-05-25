package com.sigsauer.univ.abc.models.exceptions;

public class NaturalClientNotFoundException extends  RuntimeException{

    public NaturalClientNotFoundException(Long id) {
        super("Natural client not found with ID = "+id);
    }
}
