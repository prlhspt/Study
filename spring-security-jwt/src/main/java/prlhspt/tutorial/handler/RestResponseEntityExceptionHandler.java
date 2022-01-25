package prlhspt.tutorial.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import prlhspt.tutorial.dto.ErrorDto;
import prlhspt.tutorial.exception.DuplicateMemberException;

import static org.springframework.http.HttpStatus.CONFLICT;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(CONFLICT)
    @ExceptionHandler
    @ResponseBody
    protected ErrorDto badRequest(DuplicateMemberException ex) {
        return new ErrorDto(CONFLICT.value(), ex.getMessage());
    }
}
