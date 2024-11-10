package org.os;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

class ParkingLot {
    int size = 4;
    private final Semaphore space = new Semaphore(4); // Semaphore to manage available spots
    private final Semaphore element = new Semaphore(0); // Semaphore to signal parked cars
    private final AtomicInteger occupiedSpots = new AtomicInteger(0); // Tracks currently occupied spots
    private final AtomicInteger totalCarsServed = new AtomicInteger(0); // Tracks total cars served
    private final ReentrantLock printLock = new ReentrantLock(); // Lock for synchronized printing
    static Gate g1 = new Gate();
    static Gate g2 = new Gate();
    static Gate g3 = new Gate();

    // Method to simulate parking a car
    public void parkCar(String carName, int parkDuration, int arrivalTime, int gate) throws InterruptedException {

        // Track cars served by each gate
        if (gate == 1) { g1.incrementServedCars(); }
        else if (gate == 2) { g2.incrementServedCars(); }
        else if (gate == 3) { g3.incrementServedCars(); }

        printLock.lock();
        System.out.println(carName + " arrived at time " + arrivalTime);
        long startWaitTime = System.currentTimeMillis();

        if (space.getvalue() > 0) { // If a spot is available, park immediately
            space.P(); // Acquire a parking spot
            log(carName + " parked. (Parking Status: " + occupiedSpots.incrementAndGet() + " spots occupied)");
            printLock.unlock();
            element.V(); // Signal car parked
            totalCarsServed.incrementAndGet();

            Thread.sleep(parkDuration * 1000); // Simulate parking duration

            occupiedSpots.decrementAndGet();
            log(carName + " left after " + parkDuration + " units of time. (Parking Status: " + occupiedSpots.get() + " spots occupied)");
            element.P(); // Signal car has left
            space.V(); // Release parking spot
        } else {
            log(carName + " waiting for a spot.");
            printLock.unlock();
            space.P(); // Wait for an available spot

            long waitedTime = (System.currentTimeMillis() - startWaitTime) / 1000;
            waitedTime++;
            log(carName + " parked after waiting for " + waitedTime + " units of time. (Parking Status: " + occupiedSpots.incrementAndGet() + " spots occupied)");
            element.V(); // Signal car parked
            totalCarsServed.incrementAndGet();

            Thread.sleep(parkDuration * 1000); // Simulate parking duration

            occupiedSpots.decrementAndGet();
            log(carName + " left after " + parkDuration + " units of time. (Parking Status: " + occupiedSpots.get() + " spots occupied)");
            element.P(); // Signal car has left
            space.V(); // Release parking spot
        }
    }

    // Log message with thread-safe access
    private void log(String message) {
        System.out.println(message);
    }

    // Print parking simulation summary
    public void printSummary() {
        System.out.println("\nSimulation finished. \nTotal cars served: " + totalCarsServed.get());
        System.out.println("Current cars in parking = " + occupiedSpots.get());
        System.out.println("Details: ");
        System.out.println("Gate 1 served " + g1.getServedCars() + " cars.");
        System.out.println("Gate 2 served " + g2.getServedCars() + " cars.");
        System.out.println("Gate 3 served " + g3.getServedCars() + " cars.");
    }
}
