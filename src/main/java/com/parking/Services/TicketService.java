package com.parking.Services;

import com.parking.Exceptions.InvalidException;
import com.parking.Modules.Gates;
import com.parking.Modules.Ticket;
import com.parking.Modules.Vehicle;
import com.parking.Modules.VehicleType;
import com.parking.Repositories.GateRepository;
import com.parking.Repositories.VehicleRepository;

import java.util.Date;
import java.util.Optional;

public class TicketService {
    private GateRepository gateRepository;
    private VehicleRepository vehicleRepository;


    public  TicketService(GateRepository gateRepository, VehicleRepository vehicleRepository){
        this.gateRepository = gateRepository;
        this.vehicleRepository = vehicleRepository;
    }
    public Ticket generateTicket(Long gateId, String VehicleNumber, VehicleType vehicleType, String ownerName) throws InvalidException {
        Ticket ticket = new Ticket();
        ticket.setEntryTime(new Date());
        Optional<Gates> optionalGate = gateRepository.findById(gateId);
        if(optionalGate.isEmpty()){
            throw new InvalidException("Invalid Gate Id");
        }

        Gates gate = optionalGate.get();
        ticket.setGenratedAt(gate);
        ticket.setGenratedBy(gate.getOperator());

        Optional<Vehicle> optionalVehicle = vehicleRepository.findByNumber(VehicleNumber);
        Vehicle vehicle ;
        if(optionalVehicle.isPresent()){
            vehicle = optionalVehicle.get();
        }
        else{
            vehicle = new Vehicle();
            vehicle.setOwnerName(ownerName);
            vehicle.setVehiclenumber(VehicleNumber);
            vehicle.setVehicleType(vehicleType);
            vehicleRepository.save(vehicle);
            ticket.setVehicle(vehicle);
        }
        return ticket;
    }
}
