package com.competency.matrix.teamapp.exceptions;

import com.competency.matrix.teamapp.employee.exceptions.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

//TODO: Should we use RestControllerAdvice?
//@ControllerAdvice(annotations = RestController.class)
@RestControllerAdvice
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


    @ExceptionHandler(value = Exception.class)
    private ResponseEntity<Object> handleDefaultException(
            RuntimeException exception
    ) {
        String bodyOfResponse = "Couldn't process the request.";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bodyOfResponse + " " + exception.getMessage());
    }
}
