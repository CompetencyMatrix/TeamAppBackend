package com.competency.matrix.teamapp.exceptions.server_data_exceptions;

public class DatabaseSaveFailException extends RuntimeException {
    public DatabaseSaveFailException(String message) {
        super(message);
    }
}
