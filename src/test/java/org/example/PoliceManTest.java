package org.example;

import org.example.Enums.CarColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PoliceManTest {
    @Test
    public void testPoliceManCreation() {
        assertDoesNotThrow(() -> new PoliceMan());
    }

    @Test
    public void testPoliceManNotifyFull() throws Exception {
        PoliceMan policeMan = spy(new PoliceMan());
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        Car firstCar = new Car("UP81", CarColor.BLUE);
        ParkingLot parkingLot = parkingLotOwner.createParkingLot(1);
        parkingLot.registerObserver(policeMan);
        parkingLotOwner.assignParkingLotToItself(parkingLot);

        verify(policeMan,times(0)).notifyFull(anyInt());
        parkingLotOwner.park(firstCar);
        verify(policeMan,times(1)).notifyFull(anyInt());

    }

    @Test
    public void testPoliceManDoesNotTriggerIsAvailableWhenFull() throws Exception {
        PoliceMan policeMan = spy(new PoliceMan());
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        Car firstCar = new Car("UP81", CarColor.BLUE);
        ParkingLot parkingLot = parkingLotOwner.createParkingLot(1);
        parkingLot.registerObserver(policeMan);
        parkingLotOwner.assignParkingLotToItself(parkingLot);

        verify(policeMan,times(0)).notifyAvailable(anyInt());
        parkingLotOwner.park(firstCar);
        verify(policeMan,times(0)).notifyAvailable(anyInt());

    }

    @Test
    public void testPoliceManNotifyAvailable() throws Exception {
        PoliceMan policeMan = spy(new PoliceMan());
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        Car firstCar = new Car("UP81", CarColor.BLUE);
        ParkingLot parkingLot = parkingLotOwner.createParkingLot(1);
        parkingLot.registerObserver(policeMan);
        parkingLotOwner.assignParkingLotToItself(parkingLot);

        verify(policeMan,times(0)).notifyAvailable(anyInt());
        Ticket ticket = parkingLotOwner.park(firstCar);
        parkingLotOwner.unPark(ticket);
        verify(policeMan,times(1)).notifyFull(anyInt());
    }

    @Test
    public void testPoliceManNotifyAvailableAfterTheMultipleUnPark() throws Exception {
        PoliceMan policeMan = spy(new PoliceMan());
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        Car firstCar = new Car("UP81", CarColor.BLUE);
        ParkingLot parkingLot = parkingLotOwner.createParkingLot(1);
        parkingLot.registerObserver(policeMan);
        parkingLotOwner.assignParkingLotToItself(parkingLot);

        verify(policeMan,times(0)).notifyAvailable(anyInt());
        Ticket ticket = parkingLotOwner.park(firstCar);
        parkingLotOwner.unPark(ticket);
        verify(policeMan,times(1)).notifyFull(anyInt());
        Ticket newTicket = parkingLotOwner.park(firstCar);
        parkingLotOwner.unPark(newTicket);
        verify(policeMan,times(2)).notifyAvailable(anyInt());
    }

}