package org.example;

public class ParkingSlot {
    private Integer slotNumber;
    private SlotStatus slotStatus;

    public ParkingSlot(Integer slotNumber) {
        this.slotNumber = slotNumber;
        this.slotStatus = SlotStatus.AVAILABLE ;

    }

    public boolean isSlotAvailable() {
        return slotStatus == SlotStatus.AVAILABLE;
    }

    public void setSlotStatusOccupied() {
        this.slotStatus = SlotStatus.OCCUPIED;
    }

    public void setSlotStatusEmpty() {
        this.slotStatus = SlotStatus.AVAILABLE;
    }
}
