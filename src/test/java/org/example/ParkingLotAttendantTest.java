package org.example;

import org.example.Enums.CarColor;
import org.example.Exceptions.CarAlreadyPresentException;
import org.example.Exceptions.InvalidTicketException;
import org.example.Exceptions.ParkingLotAlreadyAssigned;
import org.example.Exceptions.ParkingSlotFilled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingLotAttendantTest {

    @Test
    public void testParkingLotAttendantAssign() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(3);
        ParkingLot secondParkingLot = new ParkingLot(3);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();

        assertDoesNotThrow(() -> firstAttendant.assign(firstParkingLot));
        assertDoesNotThrow(() -> firstAttendant.assign(secondParkingLot));
    }


    @Test
    public void testParkingLotAttendantToAssignSameParkingLotTwice() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(3);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();

        assertDoesNotThrow(() -> firstAttendant.assign(firstParkingLot));
        assertThrows(ParkingLotAlreadyAssigned.class, () -> firstAttendant.assign(firstParkingLot));
    }

    @Test
    public void testParkingCarByParkingLotAttendant() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(3);
        Car carToBeParked = new Car("UP-81", CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();

        firstAttendant.assign(firstParkingLot);

        assertDoesNotThrow(() -> firstAttendant.park(carToBeParked));
    }

    @Test
    public void testExceptionParkingLotAttendantToParkFullParkingLot() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(1);
        Car firstCar = new Car("UP-81", CarColor.RED);
        Car secondCar = new Car("UP-82", CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();
        firstAttendant.assign(firstParkingLot);

        firstAttendant.park(firstCar);

        assertThrows(ParkingSlotFilled.class, () -> {
            firstAttendant.park(secondCar);
        });
    }

    @Test
    public void testParkingCarByUnParkingParkedByAttendant() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(3);
        Car carToBeParked = new Car("UP-81", CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();
        firstAttendant.assign(firstParkingLot);

        Ticket parkedCarTicket = firstAttendant.park(carToBeParked);
        Car actual = firstAttendant.unPark(parkedCarTicket);
        assertEquals(carToBeParked, actual);
    }

    @Test
    public void testParkingLotAttendantToUnParkTheNotParkedCar() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(3);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();
        firstAttendant.assign(firstParkingLot);

        Ticket randomTicket = new Ticket();

        assertThrows(InvalidTicketException.class, () -> {
            firstAttendant.unPark(randomTicket);
        });
    }


    @Test
    public void testParkingTheParkedCarAgain() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(3);
        Car carToBeParked = new Car("UP-81", CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();
        firstAttendant.assign(firstParkingLot);
        firstAttendant.park(carToBeParked);

        assertThrows(CarAlreadyPresentException.class, () -> {
            firstAttendant.park(carToBeParked);
        });
    }


    //When trying to park the same car in different Slots by same ParkingLotAttendant
    @Test
    public void testParkingSameInDifferentSlots() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(2);
        ParkingLot secondParkingLot = new ParkingLot(2);
        Car firstCar = new Car("UP-81", CarColor.RED);
        Car secondCar = new Car("UP-82", CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();

        firstAttendant.assign(firstParkingLot);
        firstAttendant.assign(secondParkingLot);
        firstAttendant.park(firstCar);
        firstAttendant.park(secondCar);

        assertThrows(CarAlreadyPresentException.class, () -> {
            firstAttendant.park(firstCar);
        });
    }

    @Test
    public void testUnParkCarInDifferentSlot() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(2);
        ParkingLot secondParkingLot = new ParkingLot(2);
        Car firstCar = new Car("UP81", CarColor.RED);
        Car secondCar = new Car("UP82", CarColor.RED);
        Car thirdCar = new Car("UP83", CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();

        firstAttendant.assign(firstParkingLot);
        firstAttendant.assign(secondParkingLot);
        firstAttendant.park(firstCar);
        firstAttendant.park(secondCar);
        Ticket ticket = firstAttendant.park(thirdCar);

        Car actualCar = firstAttendant.unPark(ticket);
        assertEquals(thirdCar, actualCar);

    }

    //Attendant trying to unPark the car parked by second Attendant
    @Test
    public void testUnParkingCarWithWrongTicket() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(2);
        ParkingLot secondParkingLot = new ParkingLot(2);
        Car firstCar = new Car("UP81", CarColor.RED);
        Car secondCar = new Car("UP82", CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();
        ParkingLotAttendant secondAttendant = new ParkingLotAttendant();
        firstAttendant.assign(firstParkingLot);
        secondAttendant.assign(secondParkingLot);

        Ticket firstCarTicket = firstAttendant.park(firstCar);
        Ticket secondCarTicket = secondAttendant.park(secondCar);

        assertThrows(InvalidTicketException.class, () -> {
            firstAttendant.unPark(secondCarTicket);
        });
    }

    @Test
    public void testUnParkTheSameCarAgain() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(2);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();
        firstAttendant.assign(firstParkingLot);
        Car firstCar = new Car("UP81", CarColor.RED);
        Ticket firstTicket = firstAttendant.park(firstCar);

        Car actualCar = firstAttendant.unPark(firstTicket);

        assertThrows(InvalidTicketException.class, () -> {
            firstAttendant.unPark(firstTicket);
        });
    }

    @Test
    public void testUnParkAndParKAgain() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(2);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();
        firstAttendant.assign(firstParkingLot);
        Car firstCar = new Car("UP81", CarColor.RED);
        Ticket firstTicket = firstAttendant.park(firstCar);

        Car actualCar = firstAttendant.unPark(firstTicket);

        Ticket reParkedCarTicket = firstAttendant.park(actualCar);

        assertEquals(actualCar, firstAttendant.unPark(reParkedCarTicket));
    }

    @Test
    public void testAssigningSameParkingLotToDifferentAttendant() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(4);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();
        ParkingLotAttendant secondAttendant = new ParkingLotAttendant();

        firstAttendant.assign(firstParkingLot);
        assertDoesNotThrow(() -> {
            secondAttendant.assign(firstParkingLot);
        });

    }

    @Test
    public void testParkingCarInSameParkingLotByDifferentAttendant() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(2);
        Car firstCar = new Car("UP81", CarColor.RED);
        Car secondCar = new Car("UP82", CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();
        ParkingLotAttendant secondAttendant = new ParkingLotAttendant();
        firstAttendant.assign(firstParkingLot);
        secondAttendant.assign(firstParkingLot);

        firstAttendant.park(firstCar);
        secondAttendant.park(secondCar);

        //Getting Parking Lot as full
        assertTrue(firstParkingLot.isParkingLotFull());
    }

    @Test
    public void testParkingLotAttendantParkingParkAccordingDistributedSystem() throws Exception {
        ParkingLot firstParkingLot = new ParkingLot(2);
        ParkingLot secondParkingLot = new ParkingLot(1);
        Car firstCar = new Car("UP81", CarColor.RED);
        Car secondCar = new Car("UP82", CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant();
        firstAttendant.assign(firstParkingLot);
        firstAttendant.assign(secondParkingLot);

        firstAttendant.park(firstCar);
        firstAttendant.park(secondCar);

        assertTrue(secondParkingLot.isParkingLotFull());
    }

}