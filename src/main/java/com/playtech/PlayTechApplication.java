package com.playtech;

import com.playtech.factory.CSVGenerationFactory;
import com.playtech.service.CSVGeneration;
import com.playtech.service.ReadPlayerInfoFromService;
import com.playtech.service.WriteDataToCSV;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PlayTechApplication {

    public static void main(String[] args) {

        int inputValue = readIntBetween();
        ReadPlayerInfoFromService readPlayerInfoFromService = new ReadPlayerInfoFromService();
        WriteDataToCSV writeDataToCSV = new WriteDataToCSV();

        CSVGenerationFactory csvGenerationFactory = new CSVGenerationFactory();
        CSVGeneration csvGeneration = csvGenerationFactory.createCSVFile(inputValue,
            readPlayerInfoFromService,
            writeDataToCSV);
        boolean isCSVGenerated = csvGeneration.generateCSV();
        if (isCSVGenerated) {
            System.out.println("CSV Generated Successfully");
        } else {
            System.out.println("CSV Generation Failed");
        }
    }

    private static int readIntBetween() {
        Scanner sc = new Scanner(System.in);

        int number;
        while (true) {
            try {
                System.out.print(getUserInputDisplayString());
                number = sc.nextInt();
                if( number >= 1 && number <= 4){
                    break;
                }
                System.out.println("Out of range.");

            } catch (InputMismatchException e) {
                System.out.println("You did not enter a Integer.");
                sc.nextLine();
            }
        }
        sc.close();
        return number;

    }

    private static String getUserInputDisplayString() {
        return "Please choose the following options: \n" +
            "1.Generate a CSV file that shows each player’s total wagered and won amount per session \n" +
            "2.Generate a CSV file that shows each player’s total wagered and won amount per game \n" +
            "3.Generate a CSV file that shows each player’s total wagered and won amount per game per session \n" +
            "4.Generate a CSV file that contains IDs of 5 players that have been most profitable for the company \n";
    }
}
