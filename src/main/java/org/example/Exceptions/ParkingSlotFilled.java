package org.example.Exceptions;

public class ParkingSlotFilled extends RuntimeException {
    public ParkingSlotFilled(String message) {
        super(message);
    }
}
