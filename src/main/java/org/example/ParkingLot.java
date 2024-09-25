package org.example;


import org.example.Enums.CarColor;
import org.example.Exceptions.CarAlreadyPresentException;

import java.util.*;

public class ParkingLot {
    private final int lotSize;
    private boolean isFull;
    private final ParkingSlot[] parkingSlots;
    private final Map<Car, Integer> parkedCars;
    private final Map<Ticket, Car> tickets;

    public ParkingLot(int lotSize) throws Exception {
        if (lotSize < 1) {
            throw new Exception("LotSize must be greater than 0");
        }
        this.lotSize = lotSize;
        this.parkingSlots = new ParkingSlot[lotSize];
        this.parkedCars = new HashMap<>();
        this.tickets = new HashMap<>();
        this.isFull = false;
        for (int i = 0; i < lotSize; i++) {
            parkingSlots[i] = new ParkingSlot(i + 1);
        }
    }

    public Ticket park(Car car) throws Exception {
        if (parkedCars.containsKey(car)) {
            throw new CarAlreadyPresentException("This car is already parked in the lot.");
        }

        Integer parkingSlotNumber = findNearestSlot();
        String ticketNumber = generateTicketNumber();
        parkingSlots[parkingSlotNumber - 1].setSlotStatusOccupied();
        parkedCars.put(car, parkingSlotNumber);
        if (parkedCars.size() == lotSize) {
            this.isFull = true;
        }
        Ticket newTicket = new Ticket(ticketNumber, parkingSlotNumber);
        tickets.put(newTicket, car);
        return newTicket;
    }

    public boolean isParkingLotFull() {
        return this.isFull;
    }

    public Integer findNearestSlot() throws Exception {
        if(!isParkingLotFull()){
            for (int slotNumber = 0; slotNumber < lotSize; slotNumber++) {
                if (parkingSlots[slotNumber].isSlotAvailable()) return slotNumber+1;
            }
        }
        throw new Exception("Parking lot is full");
    }

    public int countCarsByColor(CarColor color) {
        return (int) parkedCars.keySet().stream()
                .filter(car -> car.isColor(color))
                .count();
    }

    public Car carParkedWithRegistrationNumber(String registrationNumber) throws Exception {
        return parkedCars.keySet().stream()
                .filter(car -> car.hasRegistrationNumber(registrationNumber))
                .findFirst()
                .orElseThrow(() -> new Exception("Car with registration number not found"));
    }


    public Car unPark(Ticket ticket) throws Exception {
        Car car = tickets.get(ticket);
        if (car == null) {
            throw new Exception("Invalid ticket number.");
        }

        Integer parkingSlotNumber = parkedCars.get(car);
        parkingSlots[parkingSlotNumber - 1].setSlotStatusEmpty();
        parkedCars.remove(car);
        tickets.remove(ticket);

        if (parkedCars.size() < lotSize) {
            isFull = false;
        }

        return car;
    }



    private String generateTicketNumber() {
        Random random = new Random();
        int ticketNumber = 1000 + random.nextInt(9000);
        return String.valueOf(ticketNumber);
    }
}
