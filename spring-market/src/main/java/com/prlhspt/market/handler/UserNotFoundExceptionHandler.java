package com.prlhspt.market.handler;

import com.prlhspt.market.jwt.dto.ErrorDto;
import com.prlhspt.market.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class UserNotFoundExceptionHandler extends Throwable {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler
    @ResponseBody
    public ErrorDto methodArgumentNotValidException(UserNotFoundException ex) {
        return new ErrorDto(NOT_FOUND.value(), ex.getMessage());
    }
}