package com.parking.Services;

import com.parking.Exceptions.InvalidException;
import com.parking.Modules.Gates;
import com.parking.Modules.Ticket;
import com.parking.Modules.Vehicle;
import com.parking.Modules.VehicleType;
import com.parking.Repositories.GateRepository;
import com.parking.Repositories.VehicleRepository;

import java.util.Date;

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
