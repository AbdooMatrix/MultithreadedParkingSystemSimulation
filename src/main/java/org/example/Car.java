package org.example;

public class Car extends Thread {
    private int gate ;
    private int id  ;
    private int arriveTime ;
    private int parkingDuration ;
    public String name ;
    private ParkingLot lot ;

    Car(int gate , int id , int arriveTime , int parkingDuration , ParkingLot lot) {
        this.gate = gate ; // gate number
        this.id = id; // car id
        this.arriveTime = arriveTime ;
        this.parkingDuration = parkingDuration ;
        this.lot = lot ;
        name = "Car " + id + " from gate " + gate    ;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(arriveTime * 1000);

            lot.parkCar(name , parkingDuration, arriveTime);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}