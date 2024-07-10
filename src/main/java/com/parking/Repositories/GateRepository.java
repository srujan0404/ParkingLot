package com.parking.repositories;

import com.parking.modules.Gate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GateRepository {
    private Map<Long, Gate> gateMap = new HashMap<>();

    public Optional<Gate> findById(Long gateId) {
        if (gateId == null) {
            throw new IllegalArgumentException("Gate ID must not be null");
        }

        return Optional.ofNullable(gateMap.get(gateId));
    }
}
