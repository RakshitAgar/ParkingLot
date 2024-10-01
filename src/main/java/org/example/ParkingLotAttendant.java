package org.example;

import org.example.Exceptions.*;

import java.util.ArrayList;

public class ParkingLotAttendant implements Attendant {
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

        for (ParkingLot parkingLot : assignedParkingLots) {
            if (!parkingLot.isParkingLotFull()) {
                parkedCars.add(carToBeParked);
                return parkingLot.park(carToBeParked);
            }
        }

        throw new ParkingSlotFilled("All assigned parking lots are full.");
    }

    @Override
    public Car unPark(Ticket ticket) throws InvalidTicketException {
        return unParkCar(ticket, assignedParkingLots, parkedCars);
    }

}
