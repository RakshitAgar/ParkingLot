package org.example;

import org.example.Exceptions.notOwnedParkingLotException;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotOwner extends ParkingLotAttendant{
    private List<ParkingLot> ownedParkingLot;

    public ParkingLotOwner() {
        this.ownedParkingLot = new ArrayList<>();
    }

    public ParkingLot createParkingLot(int size) throws Exception {
        ParkingLot parkingLot = new ParkingLot(size);
        this.ownedParkingLot.add(parkingLot);
        return parkingLot;
    }

    public void assignParkingLot(ParkingLot parkingLot) {
        if(!ownedParkingLot.contains(parkingLot)) {
            throw new notOwnedParkingLotException("This ParkingLot is Not Owned by this Owner");
        }
        super.assign(parkingLot);
    }
}
