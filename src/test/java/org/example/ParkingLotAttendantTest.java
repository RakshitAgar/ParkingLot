package org.example;

import org.example.Enums.CarColor;
import org.example.Exceptions.CarAlreadyPresentException;
import org.example.Exceptions.ParkingLotAlreadyAssigned;
import org.example.Exceptions.ParkingSlotFilled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingLotAttendantTest {

    @Test
    public void testParkingLotAttendantAssignParkingLot() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(3);
        ParkingLot secondParkingLot = new ParkingLot(3);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();

        assertDoesNotThrow(() -> firstAttendant.assignParkingLot(firstParkingLot));
        assertDoesNotThrow(() -> firstAttendant.assignParkingLot(secondParkingLot));
    }

    @Test
    public void testParkingLotAttendantToAssignSameParkingLotTwice() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(3);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();

        assertDoesNotThrow(() -> firstAttendant.assignParkingLot(firstParkingLot));
        assertThrows(ParkingLotAlreadyAssigned.class, () -> firstAttendant.assignParkingLot(firstParkingLot));
    }

    @Test
    public void testParkingCarByParkingLotAttendant() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(3);
        Car carToBeParked = new Car("UP-81" , CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();
        firstAttendant.assignParkingLot(firstParkingLot);
        assertDoesNotThrow(() -> firstAttendant.park(carToBeParked));
    }

    @Test
    public void testExceptionParkingLotAttendantToParkFullParkingLot() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(1);
        Car firstCar = new Car("UP-81"  , CarColor.RED);
        Car secondCar = new Car("UP-82"  , CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();
        firstAttendant.assignParkingLot(firstParkingLot);
        firstAttendant.park(firstCar);
        assertThrows(ParkingSlotFilled.class , ()->{firstAttendant.park(secondCar);});
    }

    @Test
    public void testParkingCarByUnParkingParkedByAttendant() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(3);
        Car carToBeParked = new Car("UP-81" , CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();
        firstAttendant.assignParkingLot(firstParkingLot);

        Ticket parkedCarTicket = firstAttendant.park(carToBeParked);
        Car actual = firstAttendant.unparkCar(parkedCarTicket);
        assertEquals(carToBeParked, actual);
    }

    @Test
    public void testParkingLotAttendantToUnParkTheNotParkedCar() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(3);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();
        firstAttendant.assignParkingLot(firstParkingLot);

        Ticket randomTicket = new Ticket("AB-12" , 5);

        assertThrows(Exception.class , ()->{firstAttendant.unparkCar(randomTicket);});
    }


    //When trying to park the same car in different Slots by same ParkingLotAttendant
    @Test
    public void testParkingSameInDifferentSlots() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(2);
        ParkingLot secondParkingLot = new ParkingLot(2);
        Car firstCar = new Car("UP-81"  , CarColor.RED);
        Car secondCar = new Car("UP-82"  , CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();

        firstAttendant.assignParkingLot(firstParkingLot);
        firstAttendant.assignParkingLot(secondParkingLot);
        firstAttendant.park(firstCar);
        firstAttendant.park(secondCar);

        assertThrows(CarAlreadyPresentException.class, ()->{firstAttendant.park(firstCar);});
    }

    @Test
    public void testParkingSameInDifferentSlotsByDifferentAttendant() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(2);
        ParkingLot secondParkingLot = new ParkingLot(2);
        ParkingLot thirdParkingLot = new ParkingLot(2);
        Car firstCar = new Car("UP81"  , CarColor.RED);
        Car secondCar = new Car("UP82"  , CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();
        ParkingLotAttendant secondAttendant = new ParkingLotAttendant();

        secondAttendant.assignParkingLot(thirdParkingLot);
        firstAttendant.assignParkingLot(firstParkingLot);
        firstAttendant.assignParkingLot(secondParkingLot);
        firstAttendant.park(firstCar);
        firstAttendant.park(secondCar);

        assertThrows(CarAlreadyPresentException.class, ()->{secondAttendant.park(firstCar);});
    }

}