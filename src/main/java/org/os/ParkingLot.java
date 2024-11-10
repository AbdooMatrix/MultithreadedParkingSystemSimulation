package org.os;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

class ParkingLot {
    int size = 4; // Maximum parking lot capacity
    private final Semaphore space = new Semaphore(4); // Controls the number of available spots in the lot
    private final Semaphore element = new Semaphore(0); // Signals the presence of parked cars
    private final AtomicInteger occupiedSpots = new AtomicInteger(0); // Current count of parked cars
    private final AtomicInteger totalCarsServed = new AtomicInteger(0); // Tracks total cars served during simulation
    private final ReentrantLock printLock = new ReentrantLock(); // Lock to synchronize output printing
    static Gate g1 = new Gate(); // First gate instance
    static Gate g2 = new Gate(); // Second gate instance
    static Gate g3 = new Gate(); // Third gate instance

    // Simulates parking a car, including managing parking spots and tracking car activities
    public void parkCar(String carName, int parkDuration, int arrivalTime, int gate) throws InterruptedException {

        // Increment the served car count for the specific gate
        if (gate == 1) { g1.incrementServedCars(); }
        else if (gate == 2) { g2.incrementServedCars(); }
        else if (gate == 3) { g3.incrementServedCars(); }

        // Lock output printing to ensure ordered messages
        printLock.lock();
        System.out.println(carName + " arrived at time " + arrivalTime);
        long startWaitTime = System.currentTimeMillis(); // Track wait time if parking spot is occupied

        // Check if a parking spot is available immediately
        if (space.getvalue() > 0) { // If available, park immediately
            space.P(); // Decrease available spots by acquiring a spot
            log(carName + " parked. (Parking Status: " + occupiedSpots.incrementAndGet() + " spots occupied)");
            printLock.unlock();
            element.V(); // Signal that a car is parked
            totalCarsServed.incrementAndGet();

            Thread.sleep(parkDuration * 1000); // Simulate the duration for which the car remains parked

            occupiedSpots.decrementAndGet(); // Decrement occupied spots as the car leaves
            log(carName + " left after " + parkDuration + " units of time. (Parking Status: " + occupiedSpots.get() + " spots occupied)");
            element.P(); // Signal a car has left
            space.V(); // Release the parking spot
        } else {
            // If no spots are available, log wait status and wait for a spot to open up
            log(carName + " waiting for a spot.");
            printLock.unlock();
            space.P(); // Wait until a parking spot is available

            // Calculate how long the car waited
            long waitedTime = (System.currentTimeMillis() - startWaitTime) / 1000;
            waitedTime++;
            log(carName + " parked after waiting for " + waitedTime + " units of time. (Parking Status: " + occupiedSpots.incrementAndGet() + " spots occupied)");
            element.V(); // Signal that the car is now parked
            totalCarsServed.incrementAndGet();

            Thread.sleep(parkDuration * 1000); // Simulate parking duration

            occupiedSpots.decrementAndGet(); // Car leaves after parking duration
            log(carName + " left after " + parkDuration + " units of time. (Parking Status: " + occupiedSpots.get() + " spots occupied)");
            element.P(); // Signal car has left
            space.V(); // Release the parking spot
        }
    }

    // Logs a message with synchronized output for orderly console messages
    private void log(String message) {
        System.out.println(message);
    }

    // Outputs a summary of the simulation, including the total cars served and cars per gate
    public void printSummary() {
        System.out.println("\nSimulation finished. " +
                "\nTotal cars served: " + totalCarsServed.get() +
                "\nCurrent cars in parking = " + occupiedSpots.get());

        System.out.println("Details: " +
                "Gate 1 served " + g1.getServedCars() + " cars." +
                "Gate 2 served " + g2.getServedCars() + " cars." +
                "Gate 3 served " + g3.getServedCars() + " cars.");
    }
}
