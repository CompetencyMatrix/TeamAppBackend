package com.competency.matrix.teamapp.exceptions;

import com.competency.matrix.teamapp.employee.exceptions.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//TODO: Consider using RestControllerAdvice
@ControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler(value = EmployeeNotFoundException.class)
    private ResponseEntity<Object> handleEmployeeNotFoundException(
            RuntimeException exception
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = InvalidParameterException.class)
    private ResponseEntity<Object> handleInvalidParameterException(
            RuntimeException exception
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(value = NoMatchForParametersFound.class)
    private ResponseEntity<Object> handleNoMatchForParametersFound( RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    private ResponseEntity<Object> handleDefaultException(
            Exception exception
    ) {
        String bodyOfResponse = "Couldn't process the request.";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bodyOfResponse + " " + exception.getMessage());
    }
}
