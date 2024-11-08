package org.example;

class Car extends Thread {
    private final String carName;
    private final int arrivalTime;
    private final int parkDuration;
    private final ParkingLot parkingLot;

    public Car(String carName, int arrivalTime, int parkDuration, ParkingLot parkingLot) {
        this.carName = carName;
        this.arrivalTime = arrivalTime;
        this.parkDuration = parkDuration;
        this.parkingLot = parkingLot;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(arrivalTime * 1000); // Simulate arrival delay
            System.out.println(carName + " arrived at time " + arrivalTime);
            parkingLot.parkCar(carName, parkDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}