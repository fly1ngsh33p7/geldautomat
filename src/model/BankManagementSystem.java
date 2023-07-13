package model;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BankManagementSystem {
    private List<List<String>> data;
    
    public BankManagementSystem(File databaseFile) {
        this.data = getDataByColumn(databaseFile);
    }
    
    public static List<List<String>> getDataByColumn(File file) {
        List<List<String>> columns = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String headerLine = reader.readLine();
            String[] columnNames = headerLine.split(";");

            // Initialize columns list with empty lists for each column
            for (int i = 0; i < columnNames.length; i++) {
                columns.add(new ArrayList<>());
            }

            // Add column names to the first row of the columns list
            for (int i = 0; i < columnNames.length; i++) {
                columns.get(i).add(columnNames[i].trim());
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");

                // Add each value to the corresponding column list
                for (int i = 0; i < values.length; i++) {
                    columns.get(i).add(values[i].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return columns;
    }


}