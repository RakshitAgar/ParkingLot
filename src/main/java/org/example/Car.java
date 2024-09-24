package org.example;

public class Car {
    private final String registrationNumber;
    private final CarColor color;

    public Car(String registrationNumber, CarColor color) {
        this.registrationNumber = registrationNumber;
        this.color = color;
    }

    public boolean isColor(CarColor color) {
        return this.color == color;
    }

    public boolean hasRegistrationNumber(String registrationNumber) {
        return this.registrationNumber.equals(registrationNumber);
    }

}
