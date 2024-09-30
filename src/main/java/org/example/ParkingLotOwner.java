package org.example;

import org.example.Exceptions.ParkingLotAlreadyAssigned;

public class ParkingLotOwner extends ParkingLotAttendant{

    public void assignParkingLotToSelf(ParkingLot parkingLot) throws ParkingLotAlreadyAssigned {
        super.assign(parkingLot);
    }
}
