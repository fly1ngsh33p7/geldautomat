package view.windows;

import javax.swing.*;
import java.awt.*;

/**
 * The Popup class provides utility methods for displaying popup dialogs with customizable messages and button actions.
 * <p>
 * The class contains static methods for creating and displaying popup dialogs. These dialogs can have a title, a message
 * to display, and customizable OK and Cancel buttons. The dialog can be displayed modally, meaning it will block user
 * interaction with other windows until it is closed.
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * // Display a popup with an OK button only
 * Popup.displayOnlyWithOkButton("Info", "This is an information message.", "OK");
 *
 * // Display a popup with OK and Cancel buttons, and actions for each button
 * Runnable okButtonAction = () -> { System.out.println("OK button clicked!"); };
 * Runnable cancelButtonAction = () -> { System.out.println("Cancel button clicked!"); };
 * Popup.display("Confirmation", "Are you sure you want to proceed?", "OK", "Cancel", okButtonAction, cancelButtonAction);
 * }
 * </pre>
 */
public class Popup {
	/**
     * Displays a popup dialog with a title, a message, an OK button, and an optional Cancel button.
     *
     * @param title           The title of the popup dialog.
     * @param message         The message to display in the popup dialog.
     * @param okButtonText    The text to display on the OK button.
     * @param cancelButtonText The text to display on the Cancel button. Set to null to display no Cancel button.
     * @param okButtonAction  The action to be executed when the OK button is clicked. Set to null for no action.
     * @param cancelButtonAction The action to be executed when the Cancel button is clicked. Set to null for no action.
     */
	public static void display(String title, String message, String okButtonText, String cancelButtonText, Runnable okButtonAction, Runnable cancelButtonAction) {
    	JDialog dialog = createDialog(title, message, okButtonText, cancelButtonText, okButtonAction, cancelButtonAction);
        dialog.setVisible(true);
    }
	
	/**
     * Displays a popup dialog with a title, a message, an OK button, and an optional Cancel button.
     * This method is suitable for scenarios where the Cancel button does not require an action.
     *
     * @param title           The title of the popup dialog.
     * @param message         The message to display in the popup dialog.
     * @param okButtonText    The text to display on the OK button.
     * @param cancelButtonText The text to display on the Cancel button. Set to null to display no Cancel button.
     * @param cancelButtonAction The action to be executed when the Cancel button is clicked. Set to null for no action.
     */
	public static void display(String title, String message, String okButtonText, String cancelButtonText, Runnable cancelButtonAction) {
    	JDialog dialog = createDialog(title, message, okButtonText, cancelButtonText, null, cancelButtonAction);
        dialog.setVisible(true);
    }
    
	/**
     * Displays a popup dialog with a title, a message, and an OK button only.
     *
     * @param title        The title of the popup dialog.
     * @param message      The message to display in the popup dialog.
     * @param okButtonText The text to display on the OK button.
     */
    public static void displayOnlyWithOkButton(String title, String message, String okButtonText) {
    	display(title, message, okButtonText, null, null);
    }
    
    /**
     * Creates a custom dialog with a specified title, message, and buttons, and associates actions with the buttons.
     * The method generates a JDialog with a title bar, message, and one or two buttons, based on the provided parameters.
     *
     * @param title             The title of the dialog window.
     * @param message           The message to be displayed in the dialog.
     * @param okButtonText      The text to be displayed on the "OK" button.
     * @param cancelButtonText  The text to be displayed on the "Cancel" button. Set to null for no "Cancel" button.
     * @param okButtonAction    The action to be executed when the "OK" button is clicked. Set to null for no action.
     * @param cancelButtonAction The action to be executed when the "Cancel" button is clicked. Set to null for no action.
     * @return A JDialog containing the specified title, message, and buttons, ready to be displayed.
     */
    private static JDialog createDialog(String title, String message, String okButtonText, String cancelButtonText, Runnable okButtonAction, Runnable cancelButtonAction) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        // If the message is very long: make the popup wider
        if (message.length() > 35) {
        	dialog.setSize(600, 150);
        } else {
        	dialog.setSize(300, 150);
        }
        
        dialog.setResizable(false);
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel(message);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(messageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);

        JButton okButton = new JButton(okButtonText);
        okButton.addActionListener(e -> {
        	if (okButtonAction != null) {
        		okButtonAction.run();
        	}
        	dialog.dispose();
        });
        buttonPanel.add(okButton);

        // if cancelButtonText and cancelButtonAction are null, display no cancelButton
        if (cancelButtonText != null) {
            JButton cancelButton = new JButton(cancelButtonText);
            cancelButton.addActionListener(e -> {
                dialog.dispose();
                if (cancelButtonAction != null) {
                	cancelButtonAction.run();
                }
            });
            buttonPanel.add(cancelButton);
        }

        dialog.getContentPane().add(panel);
        
        return dialog;
    }
}
