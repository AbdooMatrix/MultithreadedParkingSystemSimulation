package org.os;

import java.util.concurrent.atomic.AtomicInteger;

class ParkingLot {
    int size=4;
    private final Semaphore space = new Semaphore(4); ;
    private final Semaphore element= new Semaphore(0);;
    private final AtomicInteger occupiedSpots = new AtomicInteger(0); // Tracks currently parked cars
    private final AtomicInteger totalCarsServed = new AtomicInteger(0); // Tracks total cars served


    // Method to simulate parking a car
    public void parkCar(String carName, int parkDuration, int arrivalTime) throws InterruptedException {
        System.out.println(carName + " arrived at time " + arrivalTime);

        long startWaitTime = System.currentTimeMillis();

        // Try to park the car
        if (space.getvalue() > 0) { // If a spot is available, park immediately
            space.P(); // Acquire a parking spot
            log(carName + " parked. (Parking Status: " + occupiedSpots.incrementAndGet() + " spots occupied)");
            element.V(); // Indicate a car is parked
            totalCarsServed.incrementAndGet();

            // Simulate the parking duration
            Thread.sleep(parkDuration * 1000);

            // Car leaves
            occupiedSpots.decrementAndGet();
            log(carName + " left after " + parkDuration + " units of time. (Parking Status: " + occupiedSpots.get() + " spots occupied)");
            element.P(); // Indicate a car has left
            space.V(); // Release the parking spot
        } else {
            // Car waits for a spot
            log(carName + " waiting for a spot.");
            space.P(); // Wait until a spot is available

            long waitedTime = (System.currentTimeMillis() - startWaitTime) / 1000;
            waitedTime++;
            log(carName + " parked after waiting for " + waitedTime + " units of time. (Parking Status: " + occupiedSpots.incrementAndGet() + " spots occupied)");
            element.V(); // Signal that a car has parked
            totalCarsServed.incrementAndGet();
            // Simulate parking duration
            Thread.sleep(parkDuration * 1000);
            // Car leaves
            occupiedSpots.decrementAndGet();
            log(carName + " left after " + parkDuration + " units of time. (Parking Status: " + occupiedSpots.get() + " spots occupied)");
            element.P(); // Signal that a car has left
            space.V(); // Release the parking spot
        }
    }

    private void log(String message) {
        System.out.println(message);
    }

    public int getTotalCarsServed() {
        return totalCarsServed.get();
    }
}
