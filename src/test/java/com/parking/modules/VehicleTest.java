package com.parking.Modules;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Vehicle Entity Tests")
class VehicleTest {

    @Test
    @DisplayName("Should create vehicle with all properties")
    void testCreateVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber("KA-01-AB-1234");
        vehicle.setVehicleType(VehicleType.CAR);
        vehicle.setOwnerName("John Doe");

        assertEquals("KA-01-AB-1234", vehicle.getVehicleNumber());
        assertEquals(VehicleType.CAR, vehicle.getVehicleType());
        assertEquals("John Doe", vehicle.getOwnerName());
    }

    @Test
    @DisplayName("Should handle different vehicle types")
    void testVehicleTypes() {
        Vehicle car = new Vehicle();
        car.setVehicleType(VehicleType.CAR);
        assertEquals(VehicleType.CAR, car.getVehicleType());

        Vehicle bike = new Vehicle();
        bike.setVehicleType(VehicleType.BIKE);
        assertEquals(VehicleType.BIKE, bike.getVehicleType());

        Vehicle truck = new Vehicle();
        truck.setVehicleType(VehicleType.TRUCK);
        assertEquals(VehicleType.TRUCK, truck.getVehicleType());
    }

    @Test
    @DisplayName("Should allow null owner name")
    void testNullOwnerName() {
        Vehicle vehicle = new Vehicle();
        vehicle.setOwnerName(null);

        assertNull(vehicle.getOwnerName());
    }

    @Test
    @DisplayName("Should allow updating vehicle properties")
    void testUpdateVehicleProperties() {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber("KA-01-AB-1234");
        vehicle.setOwnerName("Original Owner");

        vehicle.setVehicleNumber("KA-02-CD-5678");
        vehicle.setOwnerName("New Owner");

        assertEquals("KA-02-CD-5678", vehicle.getVehicleNumber());
        assertEquals("New Owner", vehicle.getOwnerName());
    }
}
