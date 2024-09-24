package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    public void testCarWithColorRed() {
        Car firstCar = new Car("AB1234" , CarColor.RED);

        assertTrue(firstCar.isColor(CarColor.RED));
    }

    @Test
    public void testCarWithColorRedWrongColor() {
        Car firstCar = new Car("AB1234" , CarColor.YELLOW);

        assertFalse(firstCar.isColor(CarColor.RED));
    }

    @Test
    public void testCarWithCorrectRegistrationNumber() {
        Car firstCar = new Car("AB1234" , CarColor.YELLOW);
        String expectedRegistrationNumber = "AB1234";

        assertTrue(firstCar.hasRegistrationNumber(expectedRegistrationNumber));
    }

    @Test
    public void testCarWithInCorrectRegistrationNumber() {
        Car firstCar = new Car("AB1234" , CarColor.YELLOW);
        String registrationNumber = "AB1235";

        assertFalse(firstCar.hasRegistrationNumber(registrationNumber));
    }

}