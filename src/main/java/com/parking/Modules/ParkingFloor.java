package com.parking.Modules;

import java.util.List;

public class ParkingFloor  extends BaseModel{
    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public List<ParkingSpots> getParkingSpots() {
        return parkingSpots;
    }

    public void setParkingSpots(List<ParkingSpots> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }



    private  int floorNumber;
    private List<ParkingSpots> parkingSpots;
    private Status status;

    public ParkingFloorStatus getParkingFloorStatus() {
        return ParkingFloorStatus;
    }

    public void setParkingFloorStatus(ParkingFloorStatus parkingFloorStatus) {
        ParkingFloorStatus = parkingFloorStatus;
    }

    private ParkingFloorStatus ParkingFloorStatus;
}