package de.trustedshops.user.management.handler;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import de.trustedshops.user.management.exception.DuplicateUserException;
import de.trustedshops.user.management.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class APIExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({UserNotFoundException.class})
    protected ResponseEntity<Object> handleCarNotFoundException(UserNotFoundException ex) {
        return buildResponseEntity(new ApiError(NOT_FOUND, ex));
    }

    @ExceptionHandler({DuplicateUserException.class})
    protected ResponseEntity<Object> handleDuplicateLicensePlateException(DuplicateUserException ex) {
        return buildResponseEntity(new ApiError(CONFLICT, ex));
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleException(Exception ex) {
        return buildResponseEntity(new ApiError(INTERNAL_SERVER_ERROR, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
