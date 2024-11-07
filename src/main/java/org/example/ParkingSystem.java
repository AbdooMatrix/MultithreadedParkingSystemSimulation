package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ParkingSystem {
    public static void main(String[] args) {
        // Scanner for user input
        Scanner userInputScanner = new Scanner(System.in);

        System.out.print("Welcome to the Parking System, Please enter your file name: ");
        String fileName = userInputScanner.nextLine();

        // Try-catch block for handling FileNotFoundException
        try {
            File file = new File(fileName);
            // Separate Scanner for file reading
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                System.out.println(line);
            }

            // Close the file scanner
            fileScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            e.printStackTrace();
        } finally {
            // Close the user input scanner
            userInputScanner.close();
        }
    }
}
