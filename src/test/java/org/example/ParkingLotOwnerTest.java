package org.example;

import org.example.Enums.CarColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingLotOwnerTest {

    @Test
    public void testParkingLotOwnerAssignParkingLot() throws Exception {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        ParkingLot parkingLot = new ParkingLot(3);

        assertDoesNotThrow(()->{
            parkingLotOwner.assignParkingLotToSelf(parkingLot);
        });
    }

    @Test
    public void testParkingCarByParkingLotOwner() throws Exception {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        ParkingLot parkingLot = new ParkingLot(2);
        Car firstCar = new Car("UP81", CarColor.RED);
        Car secondCar = new Car("UP82", CarColor.RED);

        parkingLotOwner.assignParkingLotToSelf(parkingLot);
        parkingLotOwner.park(firstCar);
        parkingLotOwner.park(secondCar);

        assertTrue(parkingLot.isParkingLotFull());
    }

    @Test
    public void testUnParkCarByParkingLotOwner() throws Exception {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        ParkingLot parkingLot = new ParkingLot(2);
        Car firstCar = new Car("UP81", CarColor.RED);

        parkingLotOwner.assignParkingLotToSelf(parkingLot);
        Ticket ticket = parkingLotOwner.park(firstCar);

        Car actualCar = parkingLotOwner.unPark(ticket);
        assertEquals(firstCar, actualCar);
    }

}