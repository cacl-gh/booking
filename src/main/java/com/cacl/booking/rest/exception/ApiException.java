package com.cacl.booking.rest.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{

    private final Error error;

    public ApiException (String message, Error error) {
        super(message);
        this.error = error;
    }
}
