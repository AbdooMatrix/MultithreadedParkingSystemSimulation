package org.example;

public class Car extends Thread {
    private int gate ;
    private int id  ;
    private int arriveTime ;
    private int parkingDuration ;
    private String name ;

    Car(int gate , int id , int arriveTime , int parkingDuration) {
        this.gate = gate ; // gate number
        this.id = id; // car id
        this.arriveTime = arriveTime ;
        this.parkingDuration = parkingDuration ;
        name = "Car " + id + " from gate " + gate + " arrive at time " + arriveTime  ;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(arriveTime * 1000);
            System.out.println(name); // EX: Car 0 from Gate 1 arrived at time 0
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}