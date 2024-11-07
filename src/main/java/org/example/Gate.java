package org.example;

public class Gate {
    private static int servedCars = 0;

    int getServedCars() {
        return servedCars;
    }
    void incrementServedCars() {
        servedCars++;
    }

}
