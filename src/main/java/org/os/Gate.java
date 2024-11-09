package org.os;

public class Gate {
    int servedCars ;



    Gate(){
        servedCars = 0 ;
    }

    public void incrementServedCars() {
        servedCars++ ;
    }


    public int getServedCars() {
        return servedCars ;
    }
}
