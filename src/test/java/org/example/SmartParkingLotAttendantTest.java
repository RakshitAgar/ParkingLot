package org.example;

import org.example.Enums.CarColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmartParkingLotAttendantTest {

    @Test
    public void testSmartParkingLotAttendant() throws Exception {
        ParkingLot firstLot = new ParkingLot(2);
        ParkingLot secondLot = new ParkingLot(1);

        Car firstCar = new Car("UP-81", CarColor.BLUE);
        Car secondCar = new Car("UP-82", CarColor.BLUE);
        Car thirdCar = new Car("UP-83", CarColor.BLUE);
        SmartParkingLotAttendant firstAttendant = new SmartParkingLotAttendant();
        firstAttendant.assign(firstLot);
        firstAttendant.assign(secondLot);

        firstAttendant.park(firstCar);
        firstAttendant.park(secondCar);

        assertTrue(secondLot.isParkingLotFull());

    }
}