package com.competency.matrix.teamapp.exceptions.server_data_exceptions;

public class DatabaseDeleteFailException extends RuntimeException {
    public DatabaseDeleteFailException(String message) {
        super(message);
    }
}
