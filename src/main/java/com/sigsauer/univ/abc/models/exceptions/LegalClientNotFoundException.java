package com.sigsauer.univ.abc.models.exceptions;

public class LegalClientNotFoundException extends RuntimeException{

    public LegalClientNotFoundException(Long id) {
        super("Legal client not found with ID = "+id);
    }
}
