package org.example;

import org.example.Enums.SlotStatus;
import org.example.Interfaces.Notifiable;

import java.util.HashMap;
import java.util.Map;

public class PoliceMan implements Notifiable {
    private Map<Integer, SlotStatus> parkingLotStatus = new HashMap<>();

    @Override
    public void notifyFull(int parkingLotID) {
        parkingLotStatus.put(parkingLotID, SlotStatus.OCCUPIED);
    }

    @Override
    public void notifyAvailable(int parkingLotID) {
        parkingLotStatus.put(parkingLotID, SlotStatus.AVAILABLE);
    }
}
