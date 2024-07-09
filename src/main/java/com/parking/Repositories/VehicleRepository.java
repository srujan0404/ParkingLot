package com.parking.Repositories;

import com.parking.Modules.Vehicle;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VehicleRepository {
    private final Map<String, Vehicle> vehicles = new HashMap<>();

    public Optional<Vehicle> findByNumber(String vehicleNumber) {
        return Optional.ofNullable(vehicles.get(vehicleNumber));
    }

    public Vehicle save(Vehicle vehicle) {
        vehicles.put(vehicle.getVehicleNumber(), vehicle);
        return vehicle;
    }
}
