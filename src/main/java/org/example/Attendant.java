package org.example;

import org.example.Exceptions.*;

import java.util.ArrayList;

public interface Attendant {

    void assign(ParkingLot parkingLot) throws ParkingLotAlreadyAssigned;
    Ticket park(Car carToBeParked) throws Exception;
    Car unPark(Ticket ticket) throws InvalidTicketException;

    default void assignParkingLot(ParkingLot parkingLot, ArrayList<ParkingLot> assignedParkingLots) throws ParkingLotAlreadyAssigned {
        if (assignedParkingLots.contains(parkingLot)) {
            throw new ParkingLotAlreadyAssigned("Parking lot already assigned.");
        }
        assignedParkingLots.add(parkingLot);
    }

    default Car unParkCar(Ticket ticket, ArrayList<ParkingLot> assignedParkingLots, ArrayList<Car> parkedCars) throws InvalidTicketException {
        for (ParkingLot parkingLot : assignedParkingLots) {
            try {
                Car unparkedCar = parkingLot.unPark(ticket);
                parkedCars.remove(unparkedCar);
                return unparkedCar;
            } catch (InvalidTicketException ignored) {
            }
        }
        throw new InvalidTicketException("Car not found in any assigned parking lot.");
    }

    default void validateIsCarParked(ArrayList<Car> parkedCars, Car carToBeParked) throws CarAlreadyPresentException {
        if (parkedCars.contains(carToBeParked)) {
            throw new CarAlreadyPresentException("Car already assigned to this parking lot.");
        }
    }

    default void validateIsParkingLotAssigned(ArrayList<ParkingLot> assignedParkingLot) throws NoParkingLotAssignedException {
        if (assignedParkingLot.isEmpty()) {
            throw new NoParkingLotAssignedException("No Parking Lot assigned.");
        }
    }
}
