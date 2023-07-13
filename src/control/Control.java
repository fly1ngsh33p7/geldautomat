package control;

import model.BankManagementSystem;
import view.View;

public class Control {
    private static BankManagementSystem bms;
    private static View view;

    public Control(BankManagementSystem bms, View view) {
        this.bms = bms;
        this.view = view;
    }

    public static void processUserInput(String input) {
        // Process the user input
        // Example implementation:
        //bms.processInput(input);

        // Retrieve the updated data from the bms
        //String data = bms.getData();

        // Update the View with the updated data
        //view.displayData(data);
    }

    public static void cancelAction() {
        // Handle cancel action if needed
        // Example implementation:
        //view.clearInputField();
    }
}