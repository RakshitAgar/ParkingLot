package org.example;


import org.example.Enums.CarColor;
import org.example.Exceptions.*;
import org.example.Interfaces.Notifiable;

import java.util.*;

public class ParkingLot {
    private boolean isFull;
    private final int lotSize;
    private final ParkingSlot[] parkingSlots;
    private final Map<Ticket,ParkingSlot> parkedSlotMap;
    private ParkingLotOwner owner;
    private final List<Notifiable> observers;
    private final int parkingLotId;

    public ParkingLot(int lotSize, ParkingLotOwner owner) throws Exception {
        if (lotSize < 1) {
            throw new Exception("LotSize must be greater than 0");
        }
        if (owner == null) {
            throw new NoOwnerAssignedException("No owner assigned to parking lot");
        }
        this.lotSize = lotSize;
        this.parkingLotId = hashCode();
        this.owner = owner;
        this.isFull = false;
        this.parkingSlots = new ParkingSlot[lotSize];
        this.parkedSlotMap = new HashMap<>();
        this.observers = new ArrayList<>();
        for (int i = 0; i < lotSize; i++) {
            parkingSlots[i] = new ParkingSlot();
        }
    }

    public Ticket park(Car car) throws Exception {
        if (isCarAlreadyParked(car)) {
            throw new CarAlreadyPresentException("This car is already parked in the lot.");
        }

        ParkingSlot availableSlot = findNearestAvailableSlot();
        Ticket ticket = availableSlot.occupyWithCar(car);
        parkedSlotMap.put(ticket, availableSlot);

        //Updating the Parking Lot Status
        if(parkedSlotMap.size() == lotSize) {
            isFull = true;
           notifyIfFull();
        }
        return ticket;

    }

    public ParkingSlot findNearestAvailableSlot() throws ParkingSlotFilled {
        for (ParkingSlot slot : parkingSlots) {
            if (slot.isAvailable()) {
                return slot;
            }
        }
        throw new ParkingSlotFilled("Parking lot is full");
    }

    public int countCarsByColor(CarColor color) {
        return (int) parkedSlotMap.values().stream()
                .filter(slot -> slot.isCarOfColor(color))
                .count();
    }

    public Car carParkedWithRegistrationNumber(String registrationNumber) throws CarNotFoundException {
        for (ParkingSlot slot : parkedSlotMap.values()) {
            try {
                return slot.getCarByRegistrationNumber(registrationNumber);
            } catch (CarNotFoundException e) {
                // Continue searching in the next slot
            }
        }
        throw new CarNotFoundException("Car with given registration number not found");
    }

    public Car unPark(Ticket ticket) throws InvalidTicketException {
        ParkingSlot slot = parkedSlotMap.get(ticket);
        if (slot == null) {
            throw new InvalidTicketException("Invalid ticket number.");
        }

        Car car = slot.vacate();
        if (isParkingLotFull()) {
            isFull = false;
            notifyIfAvailable();
        }

        parkedSlotMap.remove(ticket);
        return car;

    }

    public boolean isParkingLotFull() {
        return isFull;
    }

    public int availableSlots() {
        return parkedSlotMap.size();
    }

    private boolean isCarAlreadyParked(Car car) {
        return parkedSlotMap.values().stream()
                .anyMatch(slot -> slot.isOccupiedBy(car));
    }

    private void notifyIfFull() {
        for (Notifiable observer : observers) {
            observer.notifyFull(parkingLotId);
        }
    }

    private void notifyIfAvailable() {
        for (Notifiable observer : observers) {
            observer.notifyAvailable(parkingLotId);
        }
    }
    public void registerObserver(Notifiable observer) {
        observers.add(observer);
    }
}
