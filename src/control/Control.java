package control;

import model.Model;
import view.View;

public class Control {
    private static Model model;
    private static View view;

    public static void initialize(Model m, View v) {
        model = m;
        view = v;
    }

    public static void processUserInput(String input) {
        // Process the user input
        // Example implementation:
        //model.processInput(input);

        // Retrieve the updated data from the Model
        //String data = model.getData();

        // Update the View with the updated data
        //view.displayData(data);
    }

    public static void cancelAction() {
        // Handle cancel action if needed
        // Example implementation:
        //view.clearInputField();
    }
}