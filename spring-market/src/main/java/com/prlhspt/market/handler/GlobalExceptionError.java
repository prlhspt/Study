package com.prlhspt.market.handler;

import com.prlhspt.market.dto.auth.ErrorDto;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionError {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler
    @ResponseBody
    public ErrorDto methodNotSupported(HttpMessageNotReadableException exception) {
        return new ErrorDto(BAD_REQUEST.value(), "파라미터가 부족합니다.");
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler
    @ResponseBody
    public final ErrorDto handleAccessDeniedException(AccessDeniedException exception) {
        return new ErrorDto(HttpServletResponse.SC_FORBIDDEN, exception.getMessage());
    }

    @ResponseStatus(UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler
    @ResponseBody
    public final ErrorDto handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception) {
        return new ErrorDto(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 미디어 타입 입니다.");
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public final ErrorDto authenticationException() {
        return new ErrorDto(HttpServletResponse.SC_UNAUTHORIZED, "계정 정보가 올바르지 않습니다.");
    }

}
