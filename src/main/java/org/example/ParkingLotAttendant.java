package org.example;

import org.example.Exceptions.*;

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
        if (assignedParkingLots.isEmpty()) {
            throw new NoParkingLotAssignedException("Parking lot not assigned ");
        }

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

            throw new ParkingSlotFilled("All assigned ParkingLot are filled");
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
