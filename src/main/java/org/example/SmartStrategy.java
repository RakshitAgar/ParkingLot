package org.example;

import org.example.Interfaces.ParkingStrategy;

import java.util.List;

public class SmartStrategy implements ParkingStrategy {
    @Override
    public ParkingLot selectParkingLot(List<ParkingLot> assignedParkingLots) {
        ParkingLot selectedLot = null;
        int lowestOccupancy = Integer.MAX_VALUE;

        for (ParkingLot lot : assignedParkingLots) {
            int availableSlots = lot.availableSlots();
            if (!lot.isParkingLotFull() && availableSlots < lowestOccupancy) {
                selectedLot = lot;
                lowestOccupancy = availableSlots;
            }
        }

        return selectedLot;
    }
}
