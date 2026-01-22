package com.example.flux.domain.exception;

public class ApplicationException  extends RuntimeException {
    public ApplicationException(String message) {
        super(message);
    }
}