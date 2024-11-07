package org.example;

public class Car {
    private int id;
    private int gate;
    private int arrivalTime;
    private int parkingDuration;

    void setId (int id) {
        this.id = id;
    }
    void setGate (int gate) {
        this.gate = gate;
    }
    void setArrivalTime (int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    void setParkingDuration (int parkingDuration) {
        this.parkingDuration = parkingDuration;
    }
    int getId () {
        return id;
    }
    int getGate () {
        return gate;
    }
    int getArrivalTime () {
        return arrivalTime;
    }
    int getParkingDuration () {
        return parkingDuration;
    }
}
