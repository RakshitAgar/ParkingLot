package org.example.Interfaces;

import org.example.ParkingLot;

import java.util.List;

public interface ParkingStrategy {
    ParkingLot selectParkingLot(List<ParkingLot> assignedParkingLots);
}
