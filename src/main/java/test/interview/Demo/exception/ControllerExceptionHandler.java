package test.interview.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleException(ResourceNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(BadUserRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(BadUserRequestException e) {
        return e.getMessage();
    }
}
