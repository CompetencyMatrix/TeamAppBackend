package com.competency.matrix.teamapp.exceptions.handlers;

import com.competency.matrix.teamapp.employee.exceptions.EmployeeNotFoundException;
import com.competency.matrix.teamapp.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

    @ExceptionHandler(value = NoMatchForParametersFoundException.class)
    private ResponseEntity<Object> handleNoMatchForParametersFound(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    private ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }

    @ExceptionHandler(value = PutIdMismatchException.class)
    private ResponseEntity<Object> handlePutIdMismatchException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    private ResponseEntity<Object> handleResourceNotFoundException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = DatabaseSaveFailException.class)
    private ResponseEntity<Object> handleDatabaseSaveFailException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(value = DatabaseDeleteFailException.class)
    private ResponseEntity<Object> handleDatabaseDeleteFailException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    private ResponseEntity<Object> handleDefaultException(
            Exception exception
    ) {
        String bodyOfResponse = "Couldn't process the request.";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bodyOfResponse + " " + exception.toString());
    }
}
