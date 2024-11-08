package org.example;

public class ParkingLot {
    int size = 4;
    private final Object[] store = new Object[size];

    private int inptr = 0  ;
    private int outptr = 0 ;

    Semaphore spaces = new Semaphore(size);

    Semaphore elements = new Semaphore(0);

    public void parkCar(String carName, int parkDuration) throws InterruptedException {
        spaces.P(); // no of spaces decreases

        store[inptr] = carName;

        inptr = (inptr + 1) % size ;
        elements.V(); // no of cars increase

        System.out.println("(Parking Status: " + (size-spaces.value) + "spots occupied");
        Thread.sleep(parkDuration * 1000);
        elements.P(); // car is released.
        store[outptr] = null;
        outptr = (outptr + 1) % size;
        spaces.V();


        System.out.println("(Parking Status: " + (size-spaces.value) + "spots occupied");

    }
}

