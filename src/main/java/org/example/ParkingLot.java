package org.example;



public class ParkingLot {
    int size = 4;
    private final Object[] store = new Object[size];

    private int inptr = 0  ;
    private int outptr = 0 ;

    Semaphore spaces = new Semaphore(size);

    Semaphore elements = new Semaphore(0);

    public void parkCar(String carName, int parkDuration) throws InterruptedException {

        if(spaces.value <= 0){
            System.out.println(carName + " waiting for a spot");
        }

        int waitTime = spaces.P();
        store[inptr] = carName;

        if(waitTime == 0){
            System.out.print(carName + " parked");
        }
        else{
            Thread.sleep(waitTime * 1000);
            System.out.print(carName + " parked after waiting for " + waitTime + " units of time ");
        }



        inptr = (inptr + 1) % size ;
        elements.V(); // no of cars increase

        System.out.print(" (Parking Status: " + (size-spaces.value) + " spots occupied) \n");
        Thread.sleep(parkDuration * 1000);
        elements.P(); // car is released.
        store[outptr] = null;
        outptr = (outptr + 1) % size;
        spaces.V();


        System.out.print(carName + " left after " + parkDuration  + " units of time");

        System.out.print(" (Parking Status: " + (size-spaces.value) + " spots occupied) \n");

    }
}

