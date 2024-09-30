package org.example;

import org.example.Exceptions.CarAlreadyPresentException;
import org.example.Exceptions.InvalidTicketException;
import org.example.Exceptions.ParkingLotAlreadyAssigned;
import org.example.Exceptions.ParkingSlotFilled;

import java.util.ArrayList;

public class ParkingLotAttendant {
    private ArrayList<ParkingLot> assignedParkingLots = new ArrayList<>();
    private ArrayList<Car> parkedCars = new ArrayList<>();


    public void assign(ParkingLot parkingLot) throws ParkingLotAlreadyAssigned {
        if (assignedParkingLots.contains(parkingLot)) {
            throw new ParkingLotAlreadyAssigned("Parking lot already assigned ");
        }
        assignedParkingLots.add(parkingLot);
    }

    public Ticket park(Car carToBeParked) throws Exception {
        if (parkedCars.contains(carToBeParked)) {
            throw new CarAlreadyPresentException("Car already assigned to this parking lot");
        }

        ParkingLot selectedParkingLot = null;
        int lowestOccupancy = Integer.MAX_VALUE;
        for (ParkingLot lot : assignedParkingLots) {
            if (!lot.isParkingLotFull() && lot.availableSlots() < lowestOccupancy) {
                selectedParkingLot = lot;
                lowestOccupancy = lot.availableSlots();
            }
        }
        if(selectedParkingLot == null) {
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
