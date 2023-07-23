package view.windows;

import java.lang.Runnable;

/**
 * The ErrorPopup class provides a simple way to display an error popup window with an error message to the user.
 * It extends the Popup class, which is a utility class for creating custom popup windows.
 * 
 * The ErrorPopup class contains a static method displayError, which can be used to display an error popup with a given error message.
 * The popup will have an "OK" button to close the window and an additional "Programm beenden" button, which can be used to trigger a specified
 * action (as a Runnable) when clicked. This additional button can be used to handle the scenario where the error is critical and requires the program
 * to be terminated immediately.
 * 
 * Example Usage:
 * ErrorPopup.displayError("An unexpected error occurred.", () -> System.exit(1));
 * 
 * In the above example, an error popup will be displayed with the message "An unexpected error occurred." The popup will have an "OK" button
 * to close the window, and a "Programm beenden" button. When the "Programm beenden" button is clicked, the System.exit(1) method will be executed,
 * terminating the program.
 */
public class ErrorPopup extends Popup {
	/**
     * Displays an error popup window with the given error message and an additional "Programm beenden" button.
     * 
     * @param errorMessage The error message to be displayed in the popup window.
     * @param cancelButtonAction A Runnable representing the action to be performed when the "Programm beenden" button is clicked.
     *                           This action will be triggered in addition to closing the popup.
     *                           It can be used to handle critical errors that require the program to be terminated immediately.
     */
	public static void displayError(String errorMessage, Runnable cancelButtonAction) {
		display(errorMessage, errorMessage, "OK", "Programm beenden", cancelButtonAction);
	}
}
