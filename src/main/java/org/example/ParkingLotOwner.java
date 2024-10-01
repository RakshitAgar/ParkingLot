package org.example;

import org.example.Exceptions.ParkingLotAlreadyAssigned;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLotOwner extends ParkingLotAttendant{
    private List<ParkingLot> parkingLots;
    private Map<ParkingLot, Boolean> parkingLotStatusMap;

    public ParkingLotOwner() {
        this.parkingLots = new ArrayList<>();
        this.parkingLotStatusMap = new HashMap<>();
    }

    public ParkingLot createParkingLot(int size) throws Exception {
        ParkingLot parkingLot = new ParkingLot(size);
        parkingLotStatusMap.put(parkingLot, false);
        this.parkingLots.add(parkingLot);
        return parkingLot;
    }

    public void updateParkingLotStatus(ParkingLot parkingLot, boolean isFull) {
        this.parkingLotStatusMap.put(parkingLot, isFull);
    }

    public void assignParkingLotToSelf(ParkingLot parkingLot) throws ParkingLotAlreadyAssigned {
        super.assign(parkingLot);
    }
}
