package com.prlhspt.market.handler;

import com.prlhspt.market.dto.auth.ErrorDto;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

@ControllerAdvice
public class HttpRequestMethodNotSupportedHandler {

    @ResponseStatus(METHOD_NOT_ALLOWED)
    @ExceptionHandler
    @ResponseBody
    public ErrorDto methodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return new ErrorDto(METHOD_NOT_ALLOWED.value(), ex.getMessage());
    }
}