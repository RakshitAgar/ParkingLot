package org.example;


import org.example.Enums.CarColor;
import org.example.Exceptions.CarAlreadyPresentException;
import org.example.Exceptions.InvalidTicketException;

import java.util.*;

public class ParkingLot {
    private final int lotSize;
    private boolean isFull;
    private final ParkingSlot[] parkingSlots;
    private final Map<Ticket,ParkingSlot> parkedSlotMap;

    public ParkingLot(int lotSize) throws Exception {
        if (lotSize < 1) {
            throw new Exception("LotSize must be greater than 0");
        }
        this.lotSize = lotSize;
        this.parkingSlots = new ParkingSlot[lotSize];
        this.parkedSlotMap = new HashMap<>();
        this.isFull = false;
        for (int i = 0; i < lotSize; i++) {
            parkingSlots[i] = new ParkingSlot(i + 1);
        }
    }

    public Ticket park(Car car) throws Exception {
        if (isCarAlreadyParked(car)) {
            throw new CarAlreadyPresentException("This car is already parked in the lot.");
        }

        ParkingSlot availableSlot = findNearestAvailableSlot();
        Ticket ticket = availableSlot.occupyWithCar(car);
        parkedSlotMap.put(ticket, availableSlot);
        if (parkedSlotMap.size() == lotSize) {
            this.isFull = true;
        }
        return ticket;

    }

    public boolean isParkingLotFull() {
        return this.isFull;
    }

    public ParkingSlot findNearestAvailableSlot() throws Exception {
        for (ParkingSlot slot : parkingSlots) {
            if (slot.isAvailable()) {
                return slot;
            }
        }
        throw new Exception("Parking lot is full");
    }

    public int countCarsByColor(CarColor color) {
        return (int) parkedSlotMap.values().stream()
                .filter(slot -> slot.isCarOfColor(color))
                .count();
    }

    public boolean carParkedWithRegistrationNumber(String registrationNumber) {
        return parkedSlotMap.values().stream()
                .anyMatch(slot -> slot.hasCarWithRegistrationNumber(registrationNumber));
    }



    public Car unPark(Ticket ticket) throws InvalidTicketException {
        ParkingSlot slot = parkedSlotMap.get(ticket);
        if (slot == null) {
            throw new InvalidTicketException("Invalid ticket number.");
        }
        Car car = slot.vacate();
        parkedSlotMap.remove(ticket);
        return car;

    }

    private boolean isCarAlreadyParked(Car car) {
        return parkedSlotMap.values().stream()
                .anyMatch(slot -> slot.isOccupiedBy(car));
    }
}
