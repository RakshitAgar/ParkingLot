package org.example;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    private final int lotSize;
    private boolean isFull;
    private final ParkingSlot[] parkingSlots;
    private final Map<Integer, Car> parkedCars;

    public ParkingLot(int lotSize) throws Exception {
        if (lotSize < 1) {
            throw new Exception("LotSize must be greater than 0");
        }
        this.lotSize = lotSize;
        this.parkingSlots = new ParkingSlot[lotSize];
        this.parkedCars = new HashMap<>();
        this.isFull = false;
        for (int i = 0; i < lotSize; i++) {
            parkingSlots[i] = new ParkingSlot(i + 1);
        }
    }

    public void park(Car car) throws Exception {
        Integer parkingSlotNumber = findNearestSlot();
        parkingSlots[parkingSlotNumber-1].setSlotStatusOccupied();
        parkedCars.put(parkingSlotNumber - 1, car);
        if (parkedCars.size() == lotSize) {
            this.isFull = true;
        }
    }

    public boolean isParkingLotFull() {
        return this.isFull;
    }

    public Integer findNearestSlot() throws Exception {
        if(!isParkingLotFull()){
            for (int slotNumber = 0; slotNumber < lotSize; slotNumber++) {
                if (parkingSlots[slotNumber].isSlotAvailable()) return slotNumber+1;
            }
        }
        throw new Exception("Parking lot is full");
    }

    public int countCarsByColor(CarColor color) {
        int count = 0;
        for (Car car : parkedCars.values()) {
            if (car.isColor(color)) {
                count++;
            }
        }
        return count;
    }

    public Car carParkedWithRegistrationNumber(String registrationNumber) throws Exception {
        for (Car car : parkedCars.values()) {
            if (car.hasRegistrationNumber(registrationNumber)) {
                return car;
            }
        }
        throw new Exception("Car with registration number not found");
    }

    public Car unPark(String registrationNumber) throws Exception {
        for (int i = 0; i < lotSize; i++) {
            Car car = parkedCars.get(i);
            if (car != null && car.hasRegistrationNumber(registrationNumber)){
                if(isParkingLotFull()) isFull = false;
                parkingSlots[i].setSlotStatusEmpty();
                parkedCars.remove(i);
                return car;
            }
        }
        throw new Exception("Car not found in the parking lot");
    }
}
