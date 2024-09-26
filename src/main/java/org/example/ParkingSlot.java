package org.example;

import org.example.Enums.CarColor;
import org.example.Enums.SlotStatus;
import org.example.Exceptions.CarNotFoundException;
import org.example.Exceptions.ParkingSlotEmptyException;
import org.example.Exceptions.ParkingSlotFilled;

import java.util.Random;

public class ParkingSlot {
    private SlotStatus slotStatus;
    private Car parkedCar;

    public ParkingSlot() {
        this.slotStatus = SlotStatus.AVAILABLE;
        this.parkedCar = null;
    }

    public boolean isAvailable() {
        return slotStatus == SlotStatus.AVAILABLE;
    }

    public Ticket occupyWithCar(Car car) throws ParkingSlotFilled {
        if(isAvailable()) {
            this.slotStatus = SlotStatus.OCCUPIED;
            this.parkedCar = car;
            return new Ticket();
        }
        throw new ParkingSlotFilled("Parking Slot is already occupied");
    }

    public Car vacate() throws ParkingSlotEmptyException {
        if (!isAvailable()) {
            Car car = this.parkedCar;
            this.slotStatus = SlotStatus.AVAILABLE;
            this.parkedCar = null;
            return car;
        }
        throw new ParkingSlotEmptyException("Parking Slot is empty");
    }

    public boolean isCarOfColor(CarColor color) {
        return !isAvailable() && parkedCar.isColor(color);
    }

    public Car getCarByRegistrationNumber(String registrationNumber) throws CarNotFoundException {
        if (!isAvailable() && parkedCar.hasRegistrationNumber(registrationNumber)) {
            return parkedCar;
        }
        throw new CarNotFoundException("Car with given registration number not found");
    }

    public boolean isOccupiedBy(Car car) {
        return !isAvailable() && parkedCar.equals(car);
    }
}