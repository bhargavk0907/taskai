package com.ai.task.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String description) {
        super("Bad Request: " + description);
    }
}