package org.example;


import java.util.concurrent.locks.ReentrantLock;

public class ParkingLot {
    int size = 4;
    private final Object[] store = new Object[size];

    private int inptr = 0  ;
    private int outptr = 0 ;

    private final ReentrantLock printLock = new ReentrantLock();

    Semaphore spaces = new Semaphore(size);

    Semaphore elements = new Semaphore(0);

    public void parkCar(String carName, int parkDuration, int arriveTime) throws InterruptedException {

        printLock.lock();

        System.out.println(carName + " arrived at time " + arriveTime);

        int waitTime = spaces.P();
        store[inptr] = carName;

        if(waitTime == 0){
            System.out.print(carName + " parked");
            System.out.print(" (Parking Status: " + (size-spaces.value) + " spots occupied) \n");
            printLock.unlock();
        }
        else{
            System.out.println(carName + " waiting for a spot");
            printLock.unlock();

            Thread.sleep(waitTime * 1000);
            printLock.lock();
            System.out.print(carName + " parked after waiting for " + waitTime + " units of time ");
            System.out.print(" (Parking Status: " + (size-spaces.value) + " spots occupied) \n");
            printLock.unlock();
        }


        inptr = (inptr + 1) % size ;
        elements.V(); // no of cars increase

        Thread.sleep(parkDuration * 1000);
        elements.P(); // car is released.
        store[outptr] = null;
        outptr = (outptr + 1) % size;
        spaces.V();

        printLock.lock();
        System.out.print(carName + " left after " + parkDuration  + " units of time");
        System.out.print(" (Parking Status: " + (size-spaces.value) + " spots occupied) \n");
        printLock.unlock();
    }
}

