package org.example.Exceptions;

public class CarAlreadyPresentException extends RuntimeException {
    public CarAlreadyPresentException(String message) {
        super(message);
    }
}
