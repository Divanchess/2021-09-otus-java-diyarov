package ru.otus.exceptions;

public class DbServiceException extends Exception{

    public DbServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
