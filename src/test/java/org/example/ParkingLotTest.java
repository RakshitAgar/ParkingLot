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
    public void testFindingNearestSlot() throws Exception {
        ParkingLot parkingLot = new ParkingLot(2);

        assertEquals(parkingLot.findNearestSlot() , 1);
    }

    @Test
    public void testFindingNearestSlotAfterParkingOneCar() throws Exception {
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.park(new Car("AB12" , CarColor.RED));
        assertEquals(parkingLot.findNearestSlot() , 2);
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

        assertEquals(parkingLot.carParkedWithRegistrationNumber("AB12"), firstCar);
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

    @Test
    public void testCarWithRegistrationNumberMock() throws Exception {
        ParkingLot parkingLot = new ParkingLot(3);
        String registrationNumber = "AB12";
        Car mockedCar = mock(Car.class);
        when(mockedCar.hasRegistrationNumber(registrationNumber)).thenReturn(true);
        parkingLot.park(mockedCar);

        Car expectedCar = parkingLot.carParkedWithRegistrationNumber(registrationNumber);
        assertEquals(expectedCar, mockedCar);
    }





    //Spying Test

    @Test
    public void testFindNearestSlotIsParkingFullCallCheck() throws Exception {
        ParkingLot spyParkingLot = spy(new ParkingLot(2));
        Car firstCar = new Car("UP-81" , CarColor.RED);
        spyParkingLot.park(firstCar);
        Integer expectedSlot = spyParkingLot.findNearestSlot();

        verify(spyParkingLot,times(2)).findNearestSlot();
        assertEquals(expectedSlot, 2);
    }


    @Test
    public void testParkingLotParkVerifyApiCall() throws Exception {
        //spying
        ParkingLot spyParkingLot = spy(new ParkingLot(3));
        Car firstCar = new Car("AB12" , CarColor.RED);

        spyParkingLot.park(firstCar);

        verify(spyParkingLot, times(1)).findNearestSlot();
    }

    @Test
    public void testParkingLotVerifyIsColorAPI() throws Exception {
        ParkingLot spyParkingLot = spy(new ParkingLot(3));
        Car firstCar = new Car("AB12" , CarColor.RED);
        Car secondCar = new Car("AB15" , CarColor.RED);
        Car thirdCar = spy(new Car("AB14" , CarColor.BLUE));
        spyParkingLot.park(firstCar);
        spyParkingLot.park(secondCar);
        spyParkingLot.park(thirdCar);

        Car expectedCar = spyParkingLot.carParkedWithRegistrationNumber("AB14");

        //checking that the function is called proper number of times only
        verify(thirdCar, times(1)).hasRegistrationNumber("AB14");
        assertEquals(thirdCar , expectedCar);
        verify(thirdCar, never()).isColor(CarColor.RED);
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