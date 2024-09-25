package org.example;

import org.example.Enums.CarColor;
import org.example.Enums.SlotStatus;

import java.util.Random;

public class ParkingSlot {
    private final Integer slotNumber;
    private SlotStatus slotStatus;
    private Car parkedCar;

    public ParkingSlot(Integer slotNumber) {
        this.slotNumber = slotNumber;
        this.slotStatus = SlotStatus.AVAILABLE;
        this.parkedCar = null;
    }

    public boolean isAvailable() {
        return slotStatus == SlotStatus.AVAILABLE;
    }

    public Ticket occupyWithCar(Car car) {
        if (isAvailable()) {
            this.slotStatus = SlotStatus.OCCUPIED;
            this.parkedCar = car;
            return new Ticket(generateTicketNumber(), this.slotNumber);
        }
        return null;
    }

    public Car vacate() {
        if (!isAvailable()) {
            Car car = this.parkedCar;
            this.slotStatus = SlotStatus.AVAILABLE;
            this.parkedCar = null;
            return car;
        }
        return null;
    }

    private String generateTicketNumber() {
        Random random = new Random();
        int ticketNumber = 1000 + random.nextInt(9000);
        return String.valueOf(ticketNumber);
    }

    public boolean isCarOfColor(CarColor color) {
        return !isAvailable() && parkedCar.isColor(color);
    }

    public boolean hasCarWithRegistrationNumber(String registrationNumber) {
        return !isAvailable() && parkedCar.hasRegistrationNumber(registrationNumber);
    }

    public Car retrieveParkedCar() {
        return parkedCar;
    }

    public boolean isOccupiedBy(Car car) {
        return !isAvailable() && parkedCar.equals(car);
    }
}