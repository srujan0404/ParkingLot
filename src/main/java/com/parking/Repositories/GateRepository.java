package com.parking.Repositories;

import com.parking.Modules.Gates;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GateRepository {
    private Map<Long, Gates> gateMap = new HashMap<>();

    public Optional<Gates> findById(Long gateId) {
        if (gateId == null) {
            throw new IllegalArgumentException("Gate ID must not be null");
        }

        return Optional.ofNullable(gateMap.get(gateId));
    }
}
