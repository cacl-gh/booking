package com.cacl.booking.rest.exception;

import com.cacl.booking.api.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    private final MessageSource messages;

    @Autowired
    public ControllerExceptionHandler(final MessageSource messages) {
        this.messages = messages;
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleInternalError(ApiException e) {
        final ErrorResponse response = new ErrorResponse(e.getError().getCode(), getMessageFromError(e.getError().getCode()));
        return new ResponseEntity<>(response, e.getError().getHttpStatus());
    }

    private String getMessageFromError(String code) {
        return messages.getMessage(code, null, LocaleContextHolder.getLocale());
    }
}
