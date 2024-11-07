package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class ParkingSystem {
    static List<Car> cars = new ArrayList<>();
    static Semaphore parkingSemaphore = new Semaphore(4);

    private static Car parseInput(String line) {
        Car car = new Car();
        String[] parts = line.split(",");

        for (String part : parts) {
            part = part.trim();
            if (part.startsWith("Gate")) {
                car.setGate(Integer.parseInt(part.split(" ")[1]));
            }
            else if (part.startsWith("Car")) {
                car.setId(Integer.parseInt(part.split(" ")[1]));
            }
            else if (part.startsWith("Arrive")) {
                car.setArrivalTime(Integer.parseInt(part.split(" ")[1]));
            }
            else if (part.startsWith("Parks")) {
                car.setParkingDuration(Integer.parseInt(part.split(" ")[1]));
            }
        }

        return car;
    }

    private static void readFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                cars.add(parseInput(line));
            }
            fileScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            e.printStackTrace();
        }
    }

    private static void simulateParkingSystem() {
        for (Car car : cars) {
            // Each car is a separate thread
            new Thread(() -> {
                try {
                    // Simulate the arrival time
                    Thread.sleep(car.getArrivalTime() * 1000);
                    System.out.println("Car " + car.getId() + " from Gate " + car.getGate() + " arrived at time " + car.getArrivalTime());

                    // Attempt to acquire a parking spot
                    if (parkingSemaphore.tryAcquire()) {
                        // Car parks if a spot is available
                        System.out.println("Car " + car.getId() + " from Gate " + car.getGate() + " parked. " +
                                "(Parking Status: " + (4 - parkingSemaphore.availablePermits()) + " spots occupied)");

                        // Simulate the parking duration
                        Thread.sleep(car.getParkingDuration() * 1000);

                        // Car leaves the parking spot
                        System.out.println("Car " + car.getId() + " from Gate " + car.getGate() + " left after " +
                                car.getParkingDuration() + " units of time. " +
                                "(Parking Status: " + (4 - parkingSemaphore.availablePermits()) + " spots occupied)");
                        parkingSemaphore.release();
                    }
                    else {
                        // Car waits if no spot is available
                        System.out.println("Car " + car.getId() + " from Gate " + car.getGate() + " waiting for a spot.");

                        // Wait for a spot, then acquire it when available
                        parkingSemaphore.acquire();
                        System.out.println("Car " + car.getId() + " from Gate " + car.getGate() + " parked after waiting. " +
                                "(Parking Status: " + (4 - parkingSemaphore.availablePermits()) + " spots occupied)");

                        // Simulate the parking duration after waiting
                        Thread.sleep(car.getParkingDuration() * 1000);

                        // Car leaves the parking spot
                        System.out.println("Car " + car.getId() + " from Gate " + car.getGate() + " left after " +
                                car.getParkingDuration() + " units of time. " +
                                "(Parking Status: " + (4 - parkingSemaphore.availablePermits()) + " spots occupied)");
                        parkingSemaphore.release();
                    }
                } catch (InterruptedException e) {
                    System.out.println("Car " + car.getId() + " was interrupted.");
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        Scanner userInputScanner = new Scanner(System.in);

        System.out.print("Welcome to Parking System Simulator, Please enter file name: ");
        String fileName = userInputScanner.nextLine();
        userInputScanner.close();

        readFromFile(fileName);

        simulateParkingSystem();
    }
}
