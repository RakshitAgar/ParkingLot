package org.example.Interfaces;

import org.example.ParkingLot;

public interface Notifiable {
    void notifyFull (ParkingLot parkingLot);
    void notifyAvailable(ParkingLot parkingLot);

}
