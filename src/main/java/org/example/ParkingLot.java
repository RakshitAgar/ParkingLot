package org.example;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    private final int lotSize;
    private boolean isFull;
    private final boolean[] slotAvailable;
    private final Map<Integer, Car> parkedCars;

    public ParkingLot(int lotSize) throws Exception {
        if (lotSize < 1) {
            throw new Exception("LotSize must be greater than 0");
        }
        this.lotSize = lotSize;
        this.slotAvailable = new boolean[lotSize+1];
        for (int i = 0; i < lotSize; i++) {
            slotAvailable[i] = true;
        }
        this.parkedCars = new HashMap<>();
        this.isFull = false;
    }

    public void parkCar(Car car) throws Exception {
        if(findNearestSlot() ==  null){
            throw new Exception("Slot not available");
        }
        Integer parkingSlotNumber = findNearestSlot();
        slotAvailable[parkingSlotNumber] = false;
        parkedCars.put(parkingSlotNumber, car);
        if (parkedCars.size() == lotSize) {
            this.isFull = true;
        }
    }

    public boolean isParkingLotFull() {
        return this.isFull;
    }

    public Integer findNearestSlot() throws Exception {
        for (int i = 0; i < lotSize; i++) {
            if (slotAvailable[i]) return i+1;
        }
        return null;
    }

    public boolean unParkCar(Car carToUnParked) throws Exception {
        for (int i = 0; i < lotSize; i++) {
            Car car = parkedCars.get(i);
            if (car != null && car.equals(carToUnParked)) {
                slotAvailable[i] = false;
                parkedCars.remove(i);
                return true;
            }
        }
        return false;
    }

}
