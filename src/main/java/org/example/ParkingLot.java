package org.example;


import org.example.Enums.CarColor;
import org.example.Exceptions.CarAlreadyPresentException;
import org.example.Exceptions.CarNotFoundException;
import org.example.Exceptions.InvalidTicketException;
import org.example.Exceptions.ParkingSlotFilled;

import java.util.*;

public class ParkingLot {
    private boolean isFull;
    private final int lotSize;
    private final ParkingSlot[] parkingSlots;
    private final Map<Ticket,ParkingSlot> parkedSlotMap;
    private ParkingLotOwner observer;

    public ParkingLot(int lotSize) throws Exception {
        if (lotSize < 1) {
            throw new Exception("LotSize must be greater than 0");
        }
        this.lotSize = lotSize;
        this.isFull = false;
        this.parkingSlots = new ParkingSlot[lotSize];
        this.parkedSlotMap = new HashMap<>();
        for (int i = 0; i < lotSize; i++) {
            parkingSlots[i] = new ParkingSlot();
        }
    }

    public void setObserver(ParkingLotOwner observer) {
        this.observer = observer;
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
//            if (observer != null) {
//                observer.updateParkingLotStatus(this,true);
//            }
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
        if(isParkingLotFull()){
            isFull = false;
//            if (observer != null) {
//                observer.updateParkingLotStatus(this,false);
//            }
        }
        parkedSlotMap.remove(ticket);
        return car;

    }

    public boolean isParkingLotFull() {
        try {
            findNearestAvailableSlot();
            return false;
        }
        catch (ParkingSlotFilled e) {
            return true;
        }
    }

    public int availableSlots() {
        return parkedSlotMap.size();
    }

    private boolean isCarAlreadyParked(Car car) {
        return parkedSlotMap.values().stream()
                .anyMatch(slot -> slot.isOccupiedBy(car));
    }
}
