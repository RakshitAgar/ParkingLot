package org.example;

import org.example.Enums.CarColor;
import org.example.Exceptions.CarAlreadyPresentException;
import org.example.Exceptions.InvalidTicketException;
import org.example.Exceptions.ParkingLotAlreadyAssigned;
import org.example.Exceptions.ParkingSlotFilled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class ParkingLotAttendantTest {

    @Test
    public void testParkingLotAttendantAssign() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = new ParkingLot(3,owner);
        ParkingLot secondParkingLot = new ParkingLot(3,owner);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant(new FirstAvailableSlotStrategy());

        assertDoesNotThrow(() -> firstAttendant.assign(firstParkingLot));
        assertDoesNotThrow(() -> firstAttendant.assign(secondParkingLot));
    }


    @Test
    public void testParkingLotAttendantToAssignSameParkingLotTwice() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = new ParkingLot(3,owner);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant(new FirstAvailableSlotStrategy());

        assertDoesNotThrow(() -> firstAttendant.assign(firstParkingLot));
        assertThrows(ParkingLotAlreadyAssigned.class, () -> firstAttendant.assign(firstParkingLot));
    }

    @Test
    public void testParkingCarByParkingLotAttendant() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = new ParkingLot(3,owner);
        Car carToBeParked = new Car("UP-81", CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant(new FirstAvailableSlotStrategy());

        firstAttendant.assign(firstParkingLot);

        assertDoesNotThrow(() -> firstAttendant.park(carToBeParked));
    }

    @Test
    public void testExceptionParkingLotAttendantToParkFullParkingLot() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = new ParkingLot(1,owner);
        Car firstCar = new Car("UP-81", CarColor.RED);
        Car secondCar = new Car("UP-82", CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant(new FirstAvailableSlotStrategy());
        firstAttendant.assign(firstParkingLot);

        firstAttendant.park(firstCar);

        assertThrows(ParkingSlotFilled.class, () -> {
            firstAttendant.park(secondCar);
        });
    }

    @Test
    public void testParkingCarByUnParkingParkedByAttendant() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = new ParkingLot(3,owner);
        Car carToBeParked = new Car("UP-81", CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant(new FirstAvailableSlotStrategy());
        firstAttendant.assign(firstParkingLot);

        Ticket parkedCarTicket = firstAttendant.park(carToBeParked);
        Car actual = firstAttendant.unPark(parkedCarTicket);
        assertEquals(carToBeParked, actual);
    }

    @Test
    public void testParkingLotAttendantToUnParkTheNotParkedCar() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = new ParkingLot(3,owner);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant(new FirstAvailableSlotStrategy());
        firstAttendant.assign(firstParkingLot);

        Ticket randomTicket = new Ticket();

        assertThrows(InvalidTicketException.class, () -> {
            firstAttendant.unPark(randomTicket);
        });
    }


    @Test
    public void testParkingTheParkedCarAgain() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = new ParkingLot(3,owner);
        Car carToBeParked = new Car("UP-81", CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant(new FirstAvailableSlotStrategy());
        firstAttendant.assign(firstParkingLot);
        firstAttendant.park(carToBeParked);

        assertThrows(CarAlreadyPresentException.class, () -> {
            firstAttendant.park(carToBeParked);
        });
    }


    //When trying to park the same car in different Slots by same ParkingLotAttendant
    @Test
    public void testParkingSameInDifferentSlots() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = new ParkingLot(2,owner);
        ParkingLot secondParkingLot = new ParkingLot(2,owner);
        Car firstCar = new Car("UP-81", CarColor.RED);
        Car secondCar = new Car("UP-82", CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant(new FirstAvailableSlotStrategy());

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
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = new ParkingLot(2,owner);
        ParkingLot secondParkingLot = new ParkingLot(2,owner);
        Car firstCar = new Car("UP81", CarColor.RED);
        Car secondCar = new Car("UP82", CarColor.RED);
        Car thirdCar = new Car("UP83", CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant(new FirstAvailableSlotStrategy());

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
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = new ParkingLot(2,owner);
        ParkingLot secondParkingLot = new ParkingLot(2,owner);
        Car firstCar = new Car("UP81", CarColor.RED);
        Car secondCar = new Car("UP82", CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant(new FirstAvailableSlotStrategy());
        ParkingLotAttendant secondAttendant = new ParkingLotAttendant(new FirstAvailableSlotStrategy());
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
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = new ParkingLot(2,owner);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant(new FirstAvailableSlotStrategy());
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
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = new ParkingLot(2,owner);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant(new FirstAvailableSlotStrategy());
        firstAttendant.assign(firstParkingLot);
        Car firstCar = new Car("UP81", CarColor.RED);
        Ticket firstTicket = firstAttendant.park(firstCar);

        Car actualCar = firstAttendant.unPark(firstTicket);

        Ticket reParkedCarTicket = firstAttendant.park(actualCar);

        assertEquals(actualCar, firstAttendant.unPark(reParkedCarTicket));
    }

    @Test
    public void testAssigningSameParkingLotToDifferentAttendant() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = new ParkingLot(4,owner);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant(new FirstAvailableSlotStrategy());
        ParkingLotAttendant secondAttendant = new ParkingLotAttendant(new FirstAvailableSlotStrategy());

        firstAttendant.assign(firstParkingLot);
        assertDoesNotThrow(() -> {
            secondAttendant.assign(firstParkingLot);
        });

    }

    @Test
    public void testParkingCarInSameParkingLotByDifferentAttendant() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = new ParkingLot(2,owner);
        Car firstCar = new Car("UP81", CarColor.RED);
        Car secondCar = new Car("UP82", CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant(new FirstAvailableSlotStrategy());
        ParkingLotAttendant secondAttendant = new ParkingLotAttendant(new FirstAvailableSlotStrategy());
        firstAttendant.assign(firstParkingLot);
        secondAttendant.assign(firstParkingLot);

        firstAttendant.park(firstCar);
        secondAttendant.park(secondCar);

        //Getting Parking Lot as full
        assertTrue(firstParkingLot.isParkingLotFull());
    }

    @Test
    public void testParkingLotAttendantParkingParkAccordingDistributedWay() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = new ParkingLot(2,owner);
        ParkingLot secondParkingLot = new ParkingLot(1,owner);
        Car firstCar = new Car("UP81", CarColor.RED);
        Car secondCar = new Car("UP82", CarColor.RED);
        Car thirdCar = new Car("UP83", CarColor.RED);
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant(new SmartStrategy());
        firstAttendant.assign(firstParkingLot);
        firstAttendant.assign(secondParkingLot);

        firstAttendant.park(firstCar);
        firstAttendant.park(secondCar);
        assertTrue(secondParkingLot.isParkingLotFull());
        firstAttendant.park(thirdCar);


        assertTrue(firstParkingLot.isParkingLotFull());
    }

    @Test
    public void testSmartAttendantCreation() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLotAttendant firstAttendant = new ParkingLotAttendant(new SmartStrategy());
        ParkingLot firstParkingLot = new ParkingLot(2,owner);
        ParkingLot secondParkingLot = new ParkingLot(1,owner);
        Car firstCar = new Car("UP81", CarColor.RED);
        Car secondCar = new Car("UP82", CarColor.RED);
        firstAttendant.assign(firstParkingLot);
        firstAttendant.assign(secondParkingLot);
        firstAttendant.park(firstCar);
        firstAttendant.park(secondCar);
        assertTrue(secondParkingLot.isParkingLotFull());

    }


    // --> Add the Spying in this test
    //Testing Having 3 ParkingLot With Smart and Normal parkingLot
    @Test
    public void testMixedParkingLotAttendant() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = new ParkingLot(3,owner);
        ParkingLot secondParkingLot = new ParkingLot(2,owner);
        ParkingLot thirdParkingLot = new ParkingLot(1,owner);
        Car firstCar = new Car("UP81", CarColor.RED);
        Car secondCar = new Car("UP82", CarColor.RED);
        Car thirdCar = new Car("UP83", CarColor.RED);
        Car fourthCar = new Car("UP84", CarColor.RED);
        Car fifthCar = new Car("UP85", CarColor.RED);
        ParkingLotAttendant attendant = new ParkingLotAttendant();
        ParkingLotAttendant smartAttendant = new ParkingLotAttendant(new SmartStrategy());
        attendant.assign(firstParkingLot);
        attendant.assign(secondParkingLot);
        attendant.assign(thirdParkingLot);
        smartAttendant.assign(firstParkingLot);
        smartAttendant.assign(secondParkingLot);
        smartAttendant.assign(thirdParkingLot);

        attendant.park(firstCar);
        smartAttendant.park(secondCar);
        attendant.park(thirdCar);
        smartAttendant.park(fourthCar);
        assertTrue(thirdParkingLot.isParkingLotFull());

    }

}