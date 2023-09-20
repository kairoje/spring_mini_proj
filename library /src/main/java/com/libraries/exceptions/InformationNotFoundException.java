package com.libraries.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @returns a message describing that the entered data can not be found
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class InformationNotFoundException extends RuntimeException{
    public InformationNotFoundException(String message) { super(message); }
}
