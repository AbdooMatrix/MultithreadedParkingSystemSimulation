package org.os;

class Car extends Thread {
    private final int id;
    private final int gate;
    private final int arrivalTime;
    private final int parkDuration;
    private final ParkingLot parkingLot;

    public Car(int gate, int id, int arrivalTime, int parkDuration, ParkingLot parkingLot) {
        this.id = id;
        this.gate = gate;
        this.arrivalTime = arrivalTime;
        this.parkDuration = parkDuration;
        this.parkingLot = parkingLot;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(arrivalTime * 1000); // Simulate arrival delay
            String carName = "Car " + id + " from Gate " + gate;
            parkingLot.parkCar(carName, parkDuration, arrivalTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}