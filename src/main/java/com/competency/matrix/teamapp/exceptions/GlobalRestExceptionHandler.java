package com.competency.matrix.teamapp.exceptions;

import com.competency.matrix.teamapp.exceptions.request_data_exceptions.InvalidParameterException;
import com.competency.matrix.teamapp.exceptions.request_data_exceptions.InvalidRequestBodyException;
import com.competency.matrix.teamapp.exceptions.request_data_exceptions.UpdateIdMismatchException;
import com.competency.matrix.teamapp.exceptions.server_data_exceptions.ConflictWithServerDataException;
import com.competency.matrix.teamapp.exceptions.server_data_exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalRestExceptionHandler {
    @ExceptionHandler(value = InvalidParameterException.class)
    private ResponseEntity<Object> handleInvalidParameterException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    private ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }

    @ExceptionHandler(value = InvalidRequestBodyException.class)
    private ResponseEntity<Object> handleInvalidRequestBodyException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }

    @ExceptionHandler(value = UpdateIdMismatchException.class)
    private ResponseEntity<Object> handlePutIdMismatchException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    private ResponseEntity<Object> handleResourceNotFoundException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = ConflictWithServerDataException.class)
    private ResponseEntity<Object> handleConflictWithServerDataException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    private ResponseEntity<Object> handleDefaultException(
            Exception exception
    ) {
        String bodyOfResponse = "Couldn't process the request.";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bodyOfResponse + " " + exception.getMessage());
    }
}
