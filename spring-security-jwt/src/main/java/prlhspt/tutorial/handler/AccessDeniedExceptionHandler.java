package prlhspt.tutorial.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import prlhspt.tutorial.dto.ErrorDto;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class AccessDeniedExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public final ErrorDto handleAccessDeniedException(AccessDeniedException exception) {
        return new ErrorDto(HttpServletResponse.SC_FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler
    @ResponseBody
    public final ErrorDto runTimeErrorException(RuntimeException exception) {
        return new ErrorDto(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
    }

}
