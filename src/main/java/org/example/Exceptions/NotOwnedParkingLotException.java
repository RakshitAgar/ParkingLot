package org.example.Exceptions;

public class NotOwnedParkingLotException extends RuntimeException {
    public NotOwnedParkingLotException(String message) {
        super(message);
    }
}
