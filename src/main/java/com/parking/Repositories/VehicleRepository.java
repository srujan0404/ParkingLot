package com.parking.Repositories;

import com.parking.Modules.Vehicle;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VehicleRepository {
    private Map<String, Vehicle> vehicles = new HashMap<>();
    public Optional<Vehicle> findByNumber(String vehiclenumber) {
        if (vehicles.containsKey(vehiclenumber)) {
            return Optional.of(vehicles.get(vehiclenumber));
        }
        return Optional.empty();
    }

    public Vehicle save(Vehicle vehicle) {
        vehicles.put(vehicle.getVehiclenumber(), vehicle);
        return vehicle;
    }
}
