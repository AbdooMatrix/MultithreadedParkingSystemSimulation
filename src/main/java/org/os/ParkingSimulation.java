package org.os;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ParkingSimulation {
    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot();

        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Parse each line into gate, car name, arrival time, and park duration
                String[] parts = line.split(", ");
                String carName = parts[0] + ", " + parts[1];
                int arrivalTime = Integer.parseInt(parts[2].split(" ")[1]);
                int parkDuration = Integer.parseInt(parts[3].split(" ")[1]);

                // Create and start a new Car thread
                Car car = new Car(carName, arrivalTime, parkDuration, lot);
                car.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Report total cars served after all threads have finished
        System.out.println("Simulation finished. Total cars served: " + lot.getTotalCarsServed());
    }
}