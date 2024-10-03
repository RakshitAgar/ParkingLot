package org.example;

import org.example.Exceptions.*;
import org.example.Interfaces.ParkingStrategy;

import java.util.ArrayList;

public class ParkingLotAttendant {
    private ArrayList<ParkingLot> assignedParkingLots = new ArrayList<>();
    private ArrayList<Car> parkedCars = new ArrayList<>();
    private ParkingStrategy strategy;

    public ParkingLotAttendant(ParkingStrategy strategy) {
        this.strategy = strategy;
    }

    public ParkingLotAttendant() {
        this.strategy = new FirstAvailableSlotStrategy();
    }

    public void assign(ParkingLot parkingLot) throws ParkingLotAlreadyAssigned {
        if (assignedParkingLots.contains(parkingLot)) {
            throw new ParkingLotAlreadyAssigned("Parking lot already assigned.");
        }
        assignedParkingLots.add(parkingLot);
    }

    public Ticket park(Car carToBeParked) throws Exception {
        if (parkedCars.contains(carToBeParked)) {
            throw new CarAlreadyPresentException("Car already assigned to this parking lot");
        }
        if (assignedParkingLots.isEmpty()) {
            throw new NoParkingLotAssignedException("Parking lot already assigned ");
        }

        ParkingLot selectedParkingLot = strategy.selectParkingLot(assignedParkingLots);

        if (selectedParkingLot == null) {
            throw new ParkingSlotFilled("No available parking slots in assigned parking lots.");
        }

        parkedCars.add(carToBeParked);
        return selectedParkingLot.park(carToBeParked);
    }

    public Car unPark(Ticket ticket) throws InvalidTicketException {
        for (ParkingLot parkingLot : assignedParkingLots) {
            try {
                Car unparkedCar = parkingLot.unPark(ticket);
                parkedCars.remove(unparkedCar);
                return unparkedCar;
            } catch (InvalidTicketException ignored) {
            }
        }
        throw new InvalidTicketException("Car not found in any assigned parking lot");
    }
}
