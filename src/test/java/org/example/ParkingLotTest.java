package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    public void testParkingLotFullFalse() throws Exception {
        ParkingLot parkingLot = new ParkingLot(2);
        Car carToBeParked = new Car("AB12" , CarColor.YELLOW);
        parkingLot.parkCar(carToBeParked);
        assertFalse(parkingLot.isParkingLotFull());
    }

    @Test
    public void testParkingLotFullTrue() throws Exception {
        ParkingLot parkingLot = new ParkingLot(2);
        Car firstCar = new Car("AB12" , CarColor.RED);
        Car secondCar = new Car("AB12" , CarColor.YELLOW);
        
        parkingLot.parkCar(firstCar);
        parkingLot.parkCar(secondCar);
        assertTrue(parkingLot.isParkingLotFull());
    }

    @Test
    public void testParkingLotPark1Car() throws Exception {
        ParkingLot parkingLot = new ParkingLot(1);
        assertFalse(parkingLot.isParkingLotFull());
        Car carToBeParked = new Car("AB12" , CarColor.RED);
        parkingLot.parkCar(carToBeParked);
        assertTrue(parkingLot.isParkingLotFull());
    }

    @Test
    public void testParkingLotParkingMoreThanOneCar() throws Exception {
        ParkingLot parkingLot = new ParkingLot(3);
        Car firstCar = new Car("AB12" , CarColor.RED);
        Car secondCar = new Car("AB12" , CarColor.RED);
        parkingLot.parkCar(firstCar);
        parkingLot.parkCar(secondCar);

        assertFalse(parkingLot.isParkingLotFull());

    }

    @Test
    public void testParkingLotExceptionParkCarWithFilledSlots() throws Exception {
        ParkingLot parkingLot = new ParkingLot(1);
        Car firstCar = new Car("AB12" , CarColor.RED);
        parkingLot.parkCar(firstCar);
        Car secondCar = new Car("AB12" , CarColor.YELLOW);
        assertThrows(Exception.class, () -> {parkingLot.parkCar(secondCar);});
    }

    @Test
    public void testFindingNearestSlot() throws Exception {
        ParkingLot parkingLot = new ParkingLot(2);

        assertEquals(parkingLot.findNearestSlot() , 1);
    }





    //UnParked Function Test // Leave for Now

    @Test
    public void testParkingLotUnParkCar() throws Exception {
        ParkingLot parkingLot = new ParkingLot(3);
        Car carToBeUnParked = new Car("AB12" , CarColor.RED);
        parkingLot.parkCar(carToBeUnParked);
        assertTrue(parkingLot.unParkCar(carToBeUnParked));
    }

}