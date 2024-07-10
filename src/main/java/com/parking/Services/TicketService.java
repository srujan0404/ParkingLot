package com.parking.services; 

import com.parking.exceptions.InvalidException;
import com.parking.modules.Gates;
import com.parking.modules.Ticket;
import com.parking.modules.Vehicle;
import com.parking.modules.VehicleType;
import com.parking.repositories.GateRepository;
import com.parking.repositories.VehicleRepository;

import java.util.Date;
import java.util.Optional;

public class TicketService {
    private final GateRepository gateRepository;
    private final VehicleRepository vehicleRepository;

    public TicketService(GateRepository gateRepository, VehicleRepository vehicleRepository) {
        this.gateRepository = gateRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public Ticket generateTicket(Long gateId, String vehicleNumber, VehicleType vehicleType, String ownerName)
            throws InvalidException {
        Ticket ticket = new Ticket();
        ticket.setEntryTime(new Date());

        Gates gate = gateRepository.findById(gateId)
                .orElseThrow(() -> new InvalidException("Invalid Gate Id"));

        ticket.setGeneratedAt(gate);
        ticket.setGeneratedBy(gate.getOperator());

        Vehicle vehicle = vehicleRepository.findByNumber(vehicleNumber)
                .orElseGet(() -> {
                    Vehicle newVehicle = new Vehicle();
                    newVehicle.setOwnerName(ownerName);
                    newVehicle.setVehicleNumber(vehicleNumber);
                    newVehicle.setVehicleType(vehicleType);
                    vehicleRepository.save(newVehicle);
                    return newVehicle;
                });

        ticket.setVehicle(vehicle);
        return ticket;
    }
}
