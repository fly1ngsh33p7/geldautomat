package model;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Model {
    private List<List<String>> data;
    public Model(File databaseFile) {
        this.data = getDataFromFile(databaseFile);
    }

    public List<List<String>> getDataFromFile(File sourceFile) {
        List<List<String>> data = new ArrayList<>();

        try {
            Scanner sc = null;
            sc = new Scanner(sourceFile);

            while (sc.hasNextLine()) {
                data.add(Arrays.stream((new String[]{sc.nextLine()})).toList());
            }
            
            sc.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return data;
    }
}