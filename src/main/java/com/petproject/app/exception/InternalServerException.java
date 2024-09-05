package com.petproject.app.exception;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String errorUpdatingRoom) {
        super(errorUpdatingRoom);
    }
}
