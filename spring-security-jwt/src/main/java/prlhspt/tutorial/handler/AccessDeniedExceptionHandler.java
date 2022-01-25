package prlhspt.tutorial.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import prlhspt.tutorial.dto.ErrorDto;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@ControllerAdvice
public class AccessDeniedExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public final ErrorDto handleAccessDeniedException(AccessDeniedException exception) {
        return new ErrorDto(HttpServletResponse.SC_FORBIDDEN, exception.getMessage());
    }
}
