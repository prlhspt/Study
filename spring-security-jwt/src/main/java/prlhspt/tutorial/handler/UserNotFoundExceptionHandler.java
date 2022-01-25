package prlhspt.tutorial.handler;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import prlhspt.tutorial.dto.ErrorDto;
import prlhspt.tutorial.exception.DuplicateMemberException;
import prlhspt.tutorial.exception.UserNotFoundException;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
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
