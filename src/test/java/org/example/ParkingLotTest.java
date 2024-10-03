package org.example;

import org.example.Enums.CarColor;
import org.example.Exceptions.CarAlreadyPresentException;
import org.example.Exceptions.CarNotFoundException;
import org.example.Exceptions.NoOwnerAssignedException;
import org.example.Exceptions.ParkingSlotFilled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingLotTest {

    @Test
    public void testParkingLotCreationWithoutTheOwner() {
        assertThrows(NoOwnerAssignedException.class, () -> {
            new ParkingLot(3,null);
        });
    }

    @Test
    public void testParkingLotException() {
        ParkingLotOwner owner = new ParkingLotOwner();
        assertThrows(Exception.class, () -> {new ParkingLot(0,owner);});
    }

    @Test
    public void testParkingLotWith5Slots() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot parkingLot = new ParkingLot(5,owner);
        assertNotNull(parkingLot);
    }

    @Test
    public void testParkingLotParkCar() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot parkingLot = new ParkingLot(2,owner);
        Car carToBeParked = new Car("AB12" , CarColor.YELLOW);
        assertDoesNotThrow(() -> {parkingLot.park(carToBeParked);});
    }

    @Test
    public void testParkingLotParkedCarTwice() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot parkingLot = new ParkingLot(2,owner);
        Car firstCar = new Car("AB12" , CarColor.YELLOW);
        Car secondCar = new Car("AB12" , CarColor.YELLOW);

        parkingLot.park(firstCar);
        assertThrows(CarAlreadyPresentException.class, () -> {parkingLot.park(firstCar);});

        assertDoesNotThrow(() -> {parkingLot.park(secondCar);});
    }

    @Test
    public void testParkingLotFullTrue() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot parkingLot = new ParkingLot(2,owner);
        Car firstCar = new Car("AB12" , CarColor.RED);
        Car secondCar = new Car("AB13" , CarColor.YELLOW);
        Car thirdCar = new Car("AB14" , CarColor.RED);
        
        parkingLot.park(firstCar);
        parkingLot.park(secondCar);
        assertThrows(ParkingSlotFilled.class, () -> {parkingLot.park(thirdCar);});
    }

    @Test
    public void testParkingLotPark1Car() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot parkingLot = new ParkingLot(1,owner);
        Car firstCar = new Car("AB12" , CarColor.RED);
        Car secondCar = new Car("AB13" , CarColor.YELLOW);
        assertDoesNotThrow(() -> {parkingLot.park(firstCar);});

        assertThrows(ParkingSlotFilled.class, () -> {parkingLot.park(secondCar);});
    }

    @Test
    public void testParkingLotParkingMoreThanOneCar() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot parkingLot = new ParkingLot(3,owner);
        Car firstCar = new Car("AB12" , CarColor.RED);
        Car secondCar = new Car("AB15" , CarColor.RED);
        Car thirdCar = new Car("AB13" , CarColor.RED);
        parkingLot.park(firstCar);
        parkingLot.park(secondCar);

        assertDoesNotThrow(() -> {parkingLot.park(thirdCar);});

    }

    @Test
    public void testParkingLotExceptionParkWithFilledSlots() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot parkingLot = new ParkingLot(1,owner);
        Car firstCar = new Car("AB12" , CarColor.RED);
        parkingLot.park(firstCar);
        Car secondCar = new Car("AB12" , CarColor.YELLOW);
        assertThrows(Exception.class, () -> {parkingLot.park(secondCar);});
    }

    @Test
    public void testFindCarByColorRedCorrect() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot parkingLot = new ParkingLot(5,owner);
        Car firstCar = new Car("AB12" , CarColor.RED);
        Car secondCar = new Car("AB12" , CarColor.RED);
        Car thirdCar = new Car("AB12" , CarColor.BLUE);

        parkingLot.park(firstCar);
        parkingLot.park(secondCar);
        parkingLot.park(thirdCar);

        assertEquals(parkingLot.countCarsByColor(CarColor.RED), 2);
    }

    @Test
    public void testFindCarByColorYellowCorrect() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot parkingLot = new ParkingLot(5,owner);
        Car firstCar = new Car("AB12" , CarColor.YELLOW);
        Car secondCar = new Car("AB12" , CarColor.YELLOW);
        Car thirdCar = new Car("AB12" , CarColor.YELLOW);

        parkingLot.park(firstCar);
        parkingLot.park(secondCar);
        parkingLot.park(thirdCar);

        assertEquals(parkingLot.countCarsByColor(CarColor.YELLOW), 3);
    }

    @Test
    public void testFindCarByRegistrationNumber() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot parkingLot = new ParkingLot(5,owner);
        Car firstCar = new Car("AB12" , CarColor.RED);
        Car secondCar = new Car("AB13" , CarColor.YELLOW);

        parkingLot.park(firstCar);
        parkingLot.park(secondCar);

        Car actualCar = parkingLot.carParkedWithRegistrationNumber("AB12");
        assertEquals(actualCar, firstCar);
    }

    @Test
    public void testFindCarByRegistrationNumberForCarNotPresent() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot parkingLot = new ParkingLot(5,owner);
        Car firstCar = new Car("AB12" , CarColor.RED);
        Car secondCar = new Car("AB13" , CarColor.YELLOW);

        parkingLot.park(firstCar);

        assertThrows(CarNotFoundException.class, () -> {parkingLot.carParkedWithRegistrationNumber("AB13");});
    }

    //Mocking Test

    @Test
    public void testCountCarsByColorWithMock() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot parkingLot = new ParkingLot(3,owner);
        Car mockedCar = mock(Car.class);
        when(mockedCar.isColor(CarColor.RED)).thenReturn(true);

        parkingLot.park(mockedCar);
        int redCarCount = parkingLot.countCarsByColor(CarColor.RED);
        assertEquals(1, redCarCount);
    }






    //Spying Test

    @Test
    public void testParkingLotParkVerifyApiCall() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        //spying
        ParkingLot spyParkingLot = spy(new ParkingLot(3,owner));
        Car firstCar = new Car("AB12" , CarColor.RED);

        spyParkingLot.park(firstCar);

        verify(spyParkingLot, times(1)).findNearestAvailableSlot();
    }


    @Test
    public void testParkingLotUnParkCar() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot parkingLot = new ParkingLot(3,owner);
        Car carToBeUnParked = new Car("AB12" , CarColor.RED);
        Ticket parkedTicket = parkingLot.park(carToBeUnParked);

        Car actualCar = parkingLot.unPark(parkedTicket);

        assertEquals(carToBeUnParked, actualCar);
    }

    @Test
    public void testParkingLotUnParkingCarThatIsNotParked() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot parkingLot = new ParkingLot(3,owner);
        Car firstCar = new Car("AB12" , CarColor.RED);

        Ticket parkedTicket = parkingLot.park(firstCar);
        parkingLot.unPark(parkedTicket);
        Ticket wrongTicket = new Ticket();

        assertThrows(Exception.class, () -> parkingLot.unPark(wrongTicket));

    }

    @Test
    public void testParkingLotUnParkCarWithWrongTicket() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = new ParkingLot(3,owner);
        ParkingLot secondParkingLot = new ParkingLot(3,owner);
        Car firstCar = new Car("AB12" , CarColor.RED);
        Car secondCar = new Car("AB13" , CarColor.RED);

        Ticket ticketFromFirstParkingLot = firstParkingLot.park(firstCar);
        Ticket ticketFromSecondParkingLot = secondParkingLot.park(secondCar);

        assertThrows(Exception.class, () -> firstParkingLot.unPark(ticketFromSecondParkingLot));
    }

    @Test
    public void testParkingLotIsFullAfterUnParkingCar() throws Exception {
        ParkingLotOwner owner = new ParkingLotOwner();
        ParkingLot firstParkingLot = new ParkingLot(1,owner);
        Car firstCar = new Car("AB12" , CarColor.RED);
        Car secondCar = new Car("AB13" , CarColor.RED);
        Ticket ticket = firstParkingLot.park(firstCar);

        assertThrows(ParkingSlotFilled.class, () -> firstParkingLot.park(secondCar));

        firstParkingLot.unPark(ticket);

        assertDoesNotThrow(() -> firstParkingLot.park(secondCar));
    }





}