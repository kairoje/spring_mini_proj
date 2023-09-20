package com.libraries.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InformationExistException extends RuntimeException{

    /**
     * @returns a message describing that the entered data does not exist
     */
    public InformationExistException(String message) { super(message); }
}
