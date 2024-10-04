package org.example.Interfaces;

import org.example.ParkingLot;

public interface Notifiable {
    void notifyFull (int parkingLotID);
    void notifyAvailable(int parkingLotID);

}
