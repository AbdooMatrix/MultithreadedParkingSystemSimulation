package org.example;

public class Gate implements Runnable {
    int servedCars = 0;

    @Override
    public void run() {}

    int getServedCars() {
        return servedCars;
    }


}
