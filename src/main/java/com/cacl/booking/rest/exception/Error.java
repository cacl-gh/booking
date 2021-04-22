package com.cacl.booking.rest.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Error {
    INTERNAL_ERROR("0001", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_DATA("1001", HttpStatus.BAD_REQUEST);

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
