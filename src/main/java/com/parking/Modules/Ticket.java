package com.parking.Modules;

import java.util.Date;

public class Ticket extends BaseModel {
    private String number;
    private Vehicle vehicle;
    private Date entryTime;
    private ParkingSpots parkingSpot;
    private Gates gate;
    private Status status;
    private Gates generatedAt;
    private Operator generatedBy;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public ParkingSpots getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpots parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public Gates getGate() {
        return gate;
    }

    public void setGate(Gates gate) {
        this.gate = gate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Gates getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(Gates generatedAt) {
        this.generatedAt = generatedAt;
    }

    public Operator getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(Operator generatedBy) {
        this.generatedBy = generatedBy;
    }
}
