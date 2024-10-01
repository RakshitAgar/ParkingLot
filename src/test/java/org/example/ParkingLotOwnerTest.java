package org.example;

import org.example.Enums.CarColor;
import org.example.Exceptions.InvalidTicketException;
import org.example.Exceptions.NoParkingLotAssignedException;
import org.example.Exceptions.ParkingSlotFilled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingLotOwnerTest {

    @Test
    public void testParkingLotOwnerAssignParkingLotParkingLot() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        assertDoesNotThrow(() -> {
            owner.createParkingLot(2);
        });
    }

    @Test
    public void testParkingLotOwnerCreatingParkingLotWithZeroSize() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        assertThrows(Exception.class, () -> {
            owner.createParkingLot(0);
        });
    }

    @Test
    public void testOwnerAssignParkingLotParkingLotToItself() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = owner.createParkingLot(2);
        ParkingLot secondParkingLot = owner.createParkingLot(3);
        owner.assignParkingLot(secondParkingLot);
        assertDoesNotThrow(() -> {
            owner.assignParkingLot(firstParkingLot);
        });
    }

    @Test
    public void testExceptionOwnerParkingCarWithOutParkingLot() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = owner.createParkingLot(2);
        Car firstCar = new Car("UP81", CarColor.BLUE);

        assertThrows(NoParkingLotAssignedException.class, () -> {
            owner.park(firstCar);
        });
    }

    @Test
    public void testParkingOwnerParkingACar() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = owner.createParkingLot(2);
        Car firstCar = new Car("UP81", CarColor.BLUE);

        owner.assignParkingLot(firstParkingLot);
        assertDoesNotThrow(() -> {
            owner.park(firstCar);
        });
    }

    @Test
    public void testParkingLotOwnerUnParkTheCar() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = owner.createParkingLot(2);
        Car firstCar = new Car("UP81", CarColor.BLUE);
        owner.assignParkingLot(firstParkingLot);

        Ticket ticket = owner.park(firstCar);
        Car actualCar = owner.unPark(ticket);

        assertEquals(firstCar, actualCar);
    }

    @Test
    public void testParkingLotOwnerUnParkingCarParkedInNonAssignedParkingLot() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = owner.createParkingLot(2);
        ParkingLot secondParkingLot = owner.createParkingLot(3);
        Car firstCar = new Car("UP81", CarColor.BLUE);
        ParkingLotAttendant attendant = new ParkingLotAttendant();
        attendant.assign(secondParkingLot);
        owner.assignParkingLot(firstParkingLot);

        Ticket ticket = attendant.park(firstCar);

        assertThrows(InvalidTicketException.class, () -> {
            owner.unPark(ticket);
        });
    }

    @Test
    public void testParkingOwnerParkingCarInFullParkingLot() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = owner.createParkingLot(1);
        Car firstCar = new Car("UP81", CarColor.BLUE);
        Car secondCar = new Car("UP82", CarColor.BLUE);
        owner.assignParkingLot(firstParkingLot);

        owner.park(firstCar);

        assertThrows(ParkingSlotFilled.class, () -> {
            owner.park(secondCar);
        });
    }


}