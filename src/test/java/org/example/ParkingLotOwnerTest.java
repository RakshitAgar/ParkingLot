package org.example;

import org.example.Enums.CarColor;
import org.example.Exceptions.InvalidTicketException;
import org.example.Exceptions.NoParkingLotAssignedException;
import org.example.Exceptions.ParkingSlotFilled;
import org.example.Exceptions.NotOwnedParkingLotException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingLotOwnerTest {

    @Test
    public void testCreatingParkingLotOwner() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        assertDoesNotThrow(() -> {
            owner.createParkingLot(2);
        });
    }

    @Test
    public void testCreatingParkingLotOfSizeZero() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        assertThrows(Exception.class, () -> {
            owner.createParkingLot(0);
        });
    }

    @Test
    public void testParkingLotOwnerAssignParkingLotToAttendant() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLotAttendant attendant = new ParkingLotAttendant();
        ParkingLot firstParkingLot = owner.createParkingLot(3);

        assertDoesNotThrow(()->{
            owner.assignParkingLotToAttendant(attendant, firstParkingLot);
        });
    }

    @Test
    public void testParkingLotOwnerAssignMultipleParkingLotToSingleAttendant() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLotAttendant attendant = new ParkingLotAttendant();
        ParkingLot firstParkingLot = owner.createParkingLot(3);
        ParkingLot secondParkingLot = owner.createParkingLot(3);

        assertDoesNotThrow(()->{
            owner.assignParkingLotToAttendant(attendant, firstParkingLot);
            owner.assignParkingLotToAttendant(attendant, secondParkingLot);
        });
    }

    @Test
    public void testParkingLotOwnerAssignMultipleParkingLotToMultipleAttendant() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();
        ParkingLotAttendant secondAttendant = new ParkingLotAttendant();
        ParkingLot firstParkingLot = owner.createParkingLot(3);
        ParkingLot secondParkingLot = owner.createParkingLot(3);

        assertDoesNotThrow(()->{
            owner.assignParkingLotToAttendant(firstAttendant, firstParkingLot);
            owner.assignParkingLotToAttendant(secondAttendant, secondParkingLot);
        });
    }

    @Test
    public void testExceptionWhenOwnerAssignNotOwnedParkingLot() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLotOwner otherOwner = new ParkingLotOwner();
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();
        ParkingLot firstParkingLot = otherOwner.createParkingLot(3);

        assertThrows(NotOwnedParkingLotException.class, () -> {
            owner.assignParkingLotToAttendant(firstAttendant, firstParkingLot);
        });
    }

    @Test
    public void testSingleAttendantCannotHaveMultipleOwners() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLotOwner otherOwner = new ParkingLotOwner();
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();
        ParkingLot firstParkingLot = owner.createParkingLot(3);
        ParkingLot secondParkingLot = otherOwner.createParkingLot(3);

        owner.assignParkingLotToAttendant(firstAttendant, firstParkingLot);
        otherOwner.assignParkingLotToAttendant(firstAttendant, secondParkingLot);

    }

    @Test
    public void testOwnerAssignParkingLotToAttendantToItself() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = owner.createParkingLot(2);
        ParkingLot secondParkingLot = owner.createParkingLot(3);
        owner.assignParkingLotToItself(secondParkingLot);
        assertDoesNotThrow(() -> {
            owner.assignParkingLotToItself(firstParkingLot);
        });
    }

    @Test
    public void testOwnerExceptionWhenNoParkingLotAssignedToIt() throws Exception {
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

        owner.assignParkingLotToItself(firstParkingLot);
        assertDoesNotThrow(() -> {
            owner.park(firstCar);
        });
    }

    @Test
    public void testParkingLotOwnerUnParkTheCar() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = owner.createParkingLot(2);
        Car firstCar = new Car("UP81", CarColor.BLUE);
        owner.assignParkingLotToItself(firstParkingLot);

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
        ParkingLotAttendant attendant = new ParkingLotAttendant(new SmartStrategy());
        attendant.assign(secondParkingLot);
        owner.assignParkingLotToItself(firstParkingLot);

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
        owner.assignParkingLotToItself(firstParkingLot);

        owner.park(firstCar);

        assertThrows(ParkingSlotFilled.class, () -> {
            owner.park(secondCar);
        });
    }

    // Notification Test

    @Test
    public void testNotifyFullWhenParkingLotIsFull() throws Exception {
        ParkingLotOwner owner = spy(new ParkingLotOwner());
        ParkingLot parkingLot = owner.createParkingLot(1);
        Car firstCar = new Car("UP81", CarColor.BLUE);
        owner.assignParkingLotToItself(parkingLot);


        verify(owner,times(0)).notifyFull(anyInt());
        owner.park(firstCar);
        verify(owner,times(1)).notifyFull(anyInt());
    }


    @Test
    public void testNotifyFullWhenParkingLotIsAvailable() throws Exception {
        ParkingLotOwner owner = spy(new ParkingLotOwner());
        ParkingLot parkingLot = owner.createParkingLot(1);
        Car firstCar = new Car("UP81", CarColor.BLUE);
        owner.assignParkingLotToItself(parkingLot);

        verify(owner,times(0)).notifyAvailable(anyInt());
        Ticket ticket = owner.park(firstCar);
        verify(owner,times(1)).notifyFull(anyInt());
        owner.unPark(ticket);

        verify(owner,times(1)).notifyAvailable(anyInt());
    }

    // testing when owner is using the SmartStrategy while Parking
    @Test
    public void testParkingLotOwnerUsingSmartStrategy() throws Exception {
        ParkingLotOwner smartOwner = new ParkingLotOwner(new SmartStrategy());
        ParkingLot firstParkingLot = smartOwner.createParkingLot(2);
        ParkingLot secondParkingLot = smartOwner.createParkingLot(1);
        Car firstCar = new Car("UP81", CarColor.BLUE);
        Car secondCar = new Car("UP82", CarColor.BLUE);
        Car thirdCar = new Car("UP83", CarColor.BLUE);
        smartOwner.assignParkingLotToItself(firstParkingLot);
        smartOwner.assignParkingLotToItself(secondParkingLot);

        smartOwner.park(firstCar);
        assertFalse(secondParkingLot.isParkingLotFull());
        assertFalse(firstParkingLot.isParkingLotFull());

        smartOwner.park(secondCar);
        assertTrue(secondParkingLot.isParkingLotFull());
        assertFalse(firstParkingLot.isParkingLotFull());

        smartOwner.park(thirdCar);
        assertTrue(firstParkingLot.isParkingLotFull());
    }


}