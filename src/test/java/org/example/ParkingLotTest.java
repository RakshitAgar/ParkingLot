package org.example;

import org.example.Enums.CarColor;
import org.example.Exceptions.CarAlreadyPresentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingLotTest {

    @Test
    public void testParkingLotException() {
        assertThrows(Exception.class, () -> {new ParkingLot(0);});
    }

    @Test
    public void testParkingLotWith5Slots() throws Exception {
        ParkingLot parkingLot = new ParkingLot(5);
        assertNotNull(parkingLot);
    }

    @Test
    public void testParkingLotParkCar() throws Exception {
        ParkingLot parkingLot = new ParkingLot(2);
        Car carToBeParked = new Car("AB12" , CarColor.YELLOW);
        assertDoesNotThrow(() -> {parkingLot.park(carToBeParked);});
    }

    @Test
    public void testParkingLotParkedCarTwice() throws Exception {
        ParkingLot parkingLot = new ParkingLot(2);
        Car firstCar = new Car("AB12" , CarColor.YELLOW);

        parkingLot.park(firstCar);
        assertThrows(CarAlreadyPresentException.class, () -> {parkingLot.park(firstCar);});

        assertFalse(parkingLot.isParkingLotFull());
    }

    @Test
    public void testParkingLotFullTrue() throws Exception {
        ParkingLot parkingLot = new ParkingLot(2);
        Car firstCar = new Car("AB12" , CarColor.RED);
        Car secondCar = new Car("AB12" , CarColor.YELLOW);
        
        parkingLot.park(firstCar);
        parkingLot.park(secondCar);
        assertTrue(parkingLot.isParkingLotFull());
    }

    @Test
    public void testParkingLotPark1Car() throws Exception {
        ParkingLot parkingLot = new ParkingLot(1);
        assertFalse(parkingLot.isParkingLotFull());
        Car carToBeParked = new Car("AB12" , CarColor.RED);
        parkingLot.park(carToBeParked);
        assertTrue(parkingLot.isParkingLotFull());
    }

    @Test
    public void testParkingLotParkingMoreThanOneCar() throws Exception {
        ParkingLot parkingLot = new ParkingLot(3);
        Car firstCar = new Car("AB12" , CarColor.RED);
        Car secondCar = new Car("AB12" , CarColor.RED);
        parkingLot.park(firstCar);
        parkingLot.park(secondCar);

        assertFalse(parkingLot.isParkingLotFull());

    }

    @Test
    public void testParkingLotExceptionParkWithFilledSlots() throws Exception {
        ParkingLot parkingLot = new ParkingLot(1);
        Car firstCar = new Car("AB12" , CarColor.RED);
        parkingLot.park(firstCar);
        Car secondCar = new Car("AB12" , CarColor.YELLOW);
        assertThrows(Exception.class, () -> {parkingLot.park(secondCar);});
    }

    @Test
    public void testFindCarByColorRedCorrect() throws Exception {
        ParkingLot parkingLot = new ParkingLot(5);
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
        ParkingLot parkingLot = new ParkingLot(5);
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
        ParkingLot parkingLot = new ParkingLot(5);
        Car firstCar = new Car("AB12" , CarColor.RED);
        Car secondCar = new Car("AB13" , CarColor.YELLOW);
        parkingLot.park(firstCar);
        parkingLot.park(secondCar);

        assertTrue(parkingLot.carParkedWithRegistrationNumber("AB12"));
    }

    //Mocking Test

    @Test
    public void testCountCarsByColorWithMock() throws Exception {
        ParkingLot parkingLot = new ParkingLot(3);
        Car mockedCar = mock(Car.class);
        when(mockedCar.isColor(CarColor.RED)).thenReturn(true);

        parkingLot.park(mockedCar);
        int redCarCount = parkingLot.countCarsByColor(CarColor.RED);
        assertEquals(1, redCarCount);
    }






    //Spying Test


    @Test
    public void testParkingLotParkVerifyApiCall() throws Exception {
        //spying
        ParkingLot spyParkingLot = spy(new ParkingLot(3));
        Car firstCar = new Car("AB12" , CarColor.RED);

        spyParkingLot.park(firstCar);

        verify(spyParkingLot, times(1)).findNearestAvailableSlot();
    }




    @Test
    public void testParkingLotUnParkCar() throws Exception {
        ParkingLot parkingLot = new ParkingLot(3);
        Car carToBeUnParked = new Car("AB12" , CarColor.RED);
        Ticket parkedTicket = parkingLot.park(carToBeUnParked);
        Car actualCar = parkingLot.unPark(parkedTicket);
        assertEquals(carToBeUnParked, actualCar);
    }

    @Test
    public void testParkingLotUnParkWithWrongTicket() throws Exception {
        ParkingLot parkingLot = new ParkingLot(3);
        Car firstCar = new Car("AB12" , CarColor.RED);
        Ticket parkedTicket = parkingLot.park(firstCar);
        parkingLot.unPark(parkedTicket);
        Ticket wrongTicket = new Ticket("AA",2);
        assertThrows(Exception.class, () -> parkingLot.unPark(wrongTicket));

    }




}