package com.cacl.booking.rest.exception;

public class InvalidDataException extends RuntimeException {

    private final String code;

    public InvalidDataException(final String code, final String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
