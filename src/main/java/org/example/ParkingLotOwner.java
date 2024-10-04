package org.example;

import org.example.Enums.SlotStatus;
import org.example.Exceptions.NotOwnedParkingLotException;
import org.example.Interfaces.Notifiable;
import org.example.Interfaces.ParkingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLotOwner extends ParkingLotAttendant implements Notifiable {
    private List<ParkingLot> ownedParkingLot;
    private Map<Integer, SlotStatus> parkingLotStatus;
    private ParkingStrategy strategy;

    public ParkingLotOwner(ParkingStrategy strategy) {
        super(strategy);
        this.ownedParkingLot = new ArrayList<>();
        this.parkingLotStatus = new HashMap<>();
        this.strategy = strategy;
    }

    public ParkingLotOwner() {
        this.ownedParkingLot = new ArrayList<>();
        this.parkingLotStatus = new HashMap<>();
        this.strategy = new FirstAvailableSlotStrategy();
    }

    public ParkingLot createParkingLot(int size) throws Exception {
        ParkingLot parkingLot = new ParkingLot(size, this);
        parkingLot.registerObserver(this);
        this.ownedParkingLot.add(parkingLot);
        return parkingLot;
    }

    public void assignParkingLotToAttendant(ParkingLotAttendant attendant, ParkingLot parkingLot) {
        if (!ownedParkingLot.contains(parkingLot)) {
            throw new NotOwnedParkingLotException("This ParkingLot is not owned by this Owner");
        }
        attendant.assign(parkingLot);
    }

    public void assignParkingLotToItself(ParkingLot parkingLot) {
        if (!ownedParkingLot.contains(parkingLot)) {
            throw new NotOwnedParkingLotException("This ParkingLot is not owned by this Owner");
        }
        super.assign(parkingLot);
    }

    @Override
    public void notifyFull(int parkingLotID) {
        parkingLotStatus.put(parkingLotID, SlotStatus.OCCUPIED);
    }

    @Override
    public void notifyAvailable(int parkingLotID) {
        parkingLotStatus.put(parkingLotID, SlotStatus.AVAILABLE);
    }
}
