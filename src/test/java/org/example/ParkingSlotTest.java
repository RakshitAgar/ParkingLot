package org.example;

import org.example.Enums.CarColor;
import org.example.Exceptions.CarNotFoundException;
import org.example.Exceptions.ParkingSlotEmptyException;
import org.example.Exceptions.ParkingSlotFilled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingSlotTest {

    @Test
    public void testParkingSlotOccupyTheCar() {
        ParkingSlot parkingSlot = new ParkingSlot();
        Car firstCar = new Car("UP18" , CarColor.RED);

        Ticket ticket = parkingSlot.occupyWithCar(firstCar);
        assertNotNull(ticket);
    }

    @Test
    public void testParkingSlotParkingParInFilledSlot() {
        ParkingSlot parkingSlot = new ParkingSlot();
        Car firstCar = new Car("UP18" , CarColor.RED);
        Car secondCar = new Car("UP19" , CarColor.RED);


        parkingSlot.occupyWithCar(firstCar);
        assertThrows(ParkingSlotFilled.class, () -> parkingSlot.occupyWithCar(secondCar));
    }

    @Test
    public void testParkingSlotVacateCar() throws ParkingSlotEmptyException {
        ParkingSlot parkingSlot = new ParkingSlot();
        Car firstCar = new Car("UP18" , CarColor.RED);

        Ticket ticket = parkingSlot.occupyWithCar(firstCar);
        assertNotNull(ticket);

        Car actualCar = parkingSlot.vacate();
        assertEquals(firstCar, actualCar);
    }

    @Test
    public void testVacateEmptyParkingSlot() throws ParkingSlotEmptyException {
        ParkingSlot parkingSlot = new ParkingSlot();

        assertThrows(ParkingSlotEmptyException.class, () -> parkingSlot.vacate());
    }

    @Test
    public void testParkedCarColorPositiveCase() {
        ParkingSlot parkingSlot = new ParkingSlot();
        Car firstCar = new Car("UP18" , CarColor.RED);
        Ticket ticket = parkingSlot.occupyWithCar(firstCar);

        assertTrue(parkingSlot.isCarOfColor(CarColor.RED));
    }

    @Test
    public void testParkedCarColorNegativeCase() {
        ParkingSlot parkingSlot = new ParkingSlot();
        Car firstCar = new Car("UP18" , CarColor.RED);
        Ticket ticket = parkingSlot.occupyWithCar(firstCar);

        assertFalse(parkingSlot.isCarOfColor(CarColor.YELLOW));
    }

    @Test
    public void testGetCarByRegistrationNumber() {
        ParkingSlot parkingSlot = new ParkingSlot();
        Car firstCar = new Car("UP18" , CarColor.RED);
        Ticket ticket = parkingSlot.occupyWithCar(firstCar);

        Car actualCar = parkingSlot.getCarByRegistrationNumber("UP18");
        assertEquals(firstCar, actualCar);
    }

    @Test
    public void testGetCarByRegistrationNumberNotFound() throws CarNotFoundException {
        ParkingSlot parkingSlot = new ParkingSlot();
        Car firstCar = new Car("UP18" , CarColor.RED);
        Ticket ticket = parkingSlot.occupyWithCar(firstCar);

        assertThrows(CarNotFoundException.class, () -> parkingSlot.getCarByRegistrationNumber("UP19"));
    }


}