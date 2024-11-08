package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ParkingSystem {

    static ParkingLot lot = new ParkingLot();
    static List<Car> cars = new ArrayList<>();

    private static Car parseInput(String line) {
        int gate = 0 ;
        int id  = 0  ;
        int arriveTime = 0  ;
        int parkingDuration = 0 ;


        String[] parts = line.split(","); // separate line according to comma (,)

        for (String part : parts) {
            part = part.trim();
            if (part.startsWith("Gate")) {
                gate = Integer.parseInt(part.split(" ")[1]) ;
            } else if (part.startsWith("Car")) {
                id = Integer.parseInt(part.split(" ")[1]) ;
            } else if (part.startsWith("Arrive")) {
                arriveTime = Integer.parseInt(part.split(" ")[1]) ;
            } else if (part.startsWith("Parks")) {
                parkingDuration = Integer.parseInt(part.split(" ")[1]) ;
            }
        }
        Car car = new Car(gate , id , arriveTime , parkingDuration , lot);
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


    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Welcome to Parking System Simulator, Please enter file name: ");
        String fileName = sc.nextLine();

        readFromFile(fileName);


        for(Car car : cars){
            car.start();
        }

        

        sc.close();

    }
}
