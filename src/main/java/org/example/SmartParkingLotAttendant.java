package org.example;

import org.example.Exceptions.*;

import java.util.ArrayList;

public class SmartParkingLotAttendant implements Attendant {
    private ArrayList<ParkingLot> assignedParkingLots = new ArrayList<>();
    private ArrayList<Car> parkedCars = new ArrayList<>();

    @Override
    public void assign(ParkingLot parkingLot) throws ParkingLotAlreadyAssigned {
        assignParkingLot(parkingLot, assignedParkingLots);
    }

    @Override
    public Ticket park(Car carToBeParked) throws Exception {
        validateIsCarParked(parkedCars, carToBeParked);
        validateIsParkingLotAssigned(assignedParkingLots);

        ParkingLot selectedParkingLot = null;
        int lowestOccupancy = Integer.MAX_VALUE;

        for (ParkingLot lot : assignedParkingLots) {
            int lotSize = lot.availableSlots();
            if (!lot.isParkingLotFull() && lotSize < lowestOccupancy) {
                selectedParkingLot = lot;
                lowestOccupancy = lotSize;
            }
        }

        if (selectedParkingLot == null) {
            throw new ParkingSlotFilled("All assigned parking lots are full.");
        }

        parkedCars.add(carToBeParked);
        return selectedParkingLot.park(carToBeParked);
    }

    @Override
    public Car unPark(Ticket ticket) throws InvalidTicketException {
        return unParkCar(ticket, assignedParkingLots, parkedCars);
    }
}
