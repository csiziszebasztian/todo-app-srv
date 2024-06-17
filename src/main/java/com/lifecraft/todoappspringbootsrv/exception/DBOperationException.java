package com.lifecraft.todoappspringbootsrv.exception;

public class DBOperationException extends RuntimeException {
    public DBOperationException(String message) { super(message); }
}
