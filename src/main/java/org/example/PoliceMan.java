package org.example;

import org.example.Enums.SlotStatus;
import org.example.Interfaces.Notifiable;

import java.util.HashMap;
import java.util.Map;

public class PoliceMan implements Notifiable {
    private Map<ParkingLot, SlotStatus> parkingLotStatus = new HashMap<>();

    @Override
    public void notifyFull(ParkingLot parkingLot) {
        parkingLotStatus.put(parkingLot, SlotStatus.OCCUPIED);
    }

    @Override
    public void notifyAvailable(ParkingLot parkingLot) {
        parkingLotStatus.put(parkingLot, SlotStatus.OCCUPIED);
    }
}
