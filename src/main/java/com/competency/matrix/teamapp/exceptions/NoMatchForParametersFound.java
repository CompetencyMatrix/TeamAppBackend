package com.competency.matrix.teamapp.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO: Is this good practice?
// @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NoMatchForParametersFound extends RuntimeException{
    public NoMatchForParametersFound(String message) {
        super(message);
    }
}
