package org.example;

import org.example.Interfaces.ParkingStrategy;

import java.util.List;

public class FirstAvailableSlotStrategy implements ParkingStrategy {
    @Override
    public ParkingLot selectParkingLot(List<ParkingLot> assignedParkingLots) {
        for (ParkingLot parkingLot : assignedParkingLots) {
            if (!parkingLot.isParkingLotFull()) {
                return parkingLot;
            }
        }
        return null;
    }
}
