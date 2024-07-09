package com.parking.Repositories;

import com.parking.Modules.Gates;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GateRepository {
    private Map<Long, Gates> gates = new HashMap<>();
    public Optional<Gates> findById(Long gateid) {
        if (gates.containsKey(gateid)) {
            return Optional.of(gates.get(gateid));
        }

        return Optional.empty();
    }
}
