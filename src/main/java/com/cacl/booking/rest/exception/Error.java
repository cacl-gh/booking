package com.cacl.booking.rest.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Error {
    INTERNAL_ERROR("0001", HttpStatus.INTERNAL_SERVER_ERROR),
    MISSING_REQUIRED("1001", HttpStatus.BAD_REQUEST),
    INCORRECT_FORMAT("1002", HttpStatus.BAD_REQUEST),
    SAVING_TICKET_ERROR("2001", HttpStatus.INTERNAL_SERVER_ERROR);

    private static final String PREFIX = "BK";

    private final String code;
    private final HttpStatus httpStatus;

    Error(final String code, final HttpStatus httpStatus) {
        this.code = PREFIX + '-' + code;
        this.httpStatus = httpStatus;
    }

    public static Error fromCode(final String code) {
        for (Error error : Error.values()) {
            if (error.code.equals(PREFIX + '-' + code)) {
                return error;
            }
        }
        throw new IllegalArgumentException(String.format("Code [%s] not found in Error",code));
    }
}
