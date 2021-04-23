package com.cacl.booking.app.exception;

public class TicketException extends RuntimeException {

    private final String code;

    public TicketException(final String code, final String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
