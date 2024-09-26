package org.example.Exceptions;

public class ParkingSlotEmptyException extends RuntimeException {
    public ParkingSlotEmptyException(String message) {
        super(message);
    }
}
