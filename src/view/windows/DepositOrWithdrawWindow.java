package view.windows;

import javax.swing.*;

import view.BooleanConsumer;
import view.PatternTextField;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The DepositOrWithdrawWindow class represents a small window that allows the user to input a positive amount in euros and cents,
 * either for depositing money into an account or withdrawing money from an account.
 * 
 * The window includes an input field where the user can enter the desired amount, a "Submit" button for confirming the action, and
 * an "Abbrechen" (Cancel) button for closing the window without taking any action. The window is not resizable and has a fixed size of 300x70 pixels.
 * 
 * The class extends the JFrame class, making it a custom window. It implements functionality to handle events such as clicking the submit button,
 * pressing the "Enter" key, or closing the window, and provides a simple way to interact with the user for deposit or withdrawal operations.
 * 
 * Usage:
 * To use the DepositOrWithdrawWindow, create an instance by providing the following parameters:
 * - parentFrame: The parent JFrame to which the small window will be associated.
 * - submitAndWindowLabel: A string representing the label to be displayed on the submit button and as the title of the window.
 * - depositOrWithdrawOnOk: A BooleanConsumer<Double> representing the action to be performed when the user clicks the submit button.
 *                           The action should handle the deposit or withdrawal of the specified amount (Double).
 *                           It should return true if the operation was successful, or false if there was an error.
 * - onCloseOperation: A Runnable representing an optional action to be executed when the window is closed. It can be used to clean up or handle
 *                     specific behavior when the window is closed. This action will be triggered before the window is closed.
 * 
 * Example Usage:
 * 
 * // Create a deposit window associated with the mainFrame
 * DepositOrWithdrawWindow depositWindow = new DepositOrWithdrawWindow(mainFrame, "Einzahlen", amount -> {
 *     // Perform the deposit operation here and return true if successful, false otherwise
 *     // Example: account.depositMoney(amount);
 *     return true; // or false if there was an error
 * }, () -> {
 *     // This action will be executed when the window is closed. Add any clean-up or handling code here.
 * });
 * 
 * // Make the window visible
 * depositWindow.setVisible(true);
 */
public class DepositOrWithdrawWindow extends JFrame {
	private PatternTextField inputField;
	private JButton submitButton;
	private JButton cancelButton;
	private JPanel panel;

	 /**
     * Creates a new DepositOrWithdrawWindow instance with the specified parameters.
     * 
     * @param parentFrame The parent JFrame to which the small window will be associated.
     * @param submitAndWindowLabel The label to be displayed on the submit button and as the title of the window.
     * @param depositOrWithdrawOnOk A BooleanConsumer<Double> representing the action to be performed when the user clicks the submit button.
     *                              The action should handle the deposit or withdrawal of the specified amount (Double).
     *                              It should return true if the operation was successful, or false if there was an error.
     * @param onCloseOperation A Runnable representing an optional action to be executed when the window is closed. It can be used to clean up or handle
     *                         specific behavior when the window is closed. This action will be triggered before the window is closed.
     */
	public DepositOrWithdrawWindow(JFrame parentFrame, String submitAndWindowLabel, BooleanConsumer<Double> depositOrWithdrawOnOk, Runnable onCloseOperation) {
		setPreferredSize(new Dimension(300, 70));
		setResizable(false);
		setTitle(submitAndWindowLabel);
		
		// Add a WindowListener to handle the closing event when clicking the (X)-button
		if (onCloseOperation != null) {
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    onCloseOperation.run();
                }
            });
        }
		
		// Create a panel to hold the components
		panel = new JPanel();

		// Create the input field for values in euros and cents
		initInputField();
		
		// Create the submit and cancel buttons
		initButtons(submitAndWindowLabel, depositOrWithdrawOnOk, onCloseOperation);

		// Add the panel to the small window and make it visible
		getContentPane().add(panel);
		setLocationRelativeTo(parentFrame);
		pack();
	}

	/**
	 * This method handles the submit action when the user clicks the "Submit" button in the DepositOrWithdrawWindow.
	 * It first checks if the input field is not empty. If it is not empty, it attempts to parse the entered text in the input field as a positive amount in euros and cents.
	 * If parsing is successful, the method runs the provided depositOrWithdrawOnOk action, passing the parsed amount as a Double parameter.
	 * The depositOrWithdrawOnOk action should handle the deposit or withdrawal operation based on the provided amount and return true if the operation was successful,
	 * or false if there was an error. If the operation was successful, the method triggers the onCloseOperation action (if provided) and closes the small window.
	 *
	 * @param depositOrWithdrawOnOk A BooleanConsumer<Double> representing the action to be performed when the user clicks the "Submit" button.
	 *                              The action should handle the deposit or withdrawal operation based on the provided amount (Double).
	 *                              It should return true if the operation was successful, or false if there was an error.
	 * @param onCloseOperation A Runnable representing an optional action to be executed when the window is closed. It can be used to clean up or handle
	 *                         specific behavior when the window is closed. This action will be triggered before the window is closed.
	 */
	private void onSubmitFunction(BooleanConsumer<Double> depositOrWithdrawOnOk, Runnable onCloseOperation) {
		if (!this.inputField.getText().equals("")) {
			String text = this.inputField.getText();
			text = text.replace(",", ".");
			
			// run depositOrWithdrawOnOk
			boolean wasOperationSuccessful = depositOrWithdrawOnOk.accept(Double.parseDouble(text));
			
			// Close the small window if it was successful:
			if (wasOperationSuccessful) {
				onCloseOperation.run();
				dispose();
			}
		}
	}

	// init methods
	
	private void initInputField() {
		inputField = new PatternTextField("[1-9][0-9]*[,.]?[0-9]*");
		inputField.setColumns(10);
		inputField.setToolTipText("positive Amount in €, no \"€\" necessary.");
		panel.add(inputField);
	}
	
	private void initButtons(String submitAndWindowLabel, BooleanConsumer<Double> depositOrWithdrawOnOk, Runnable onCloseOperation) {
		submitButton = new JButton("Submit");
		this.submitButton.setText(submitAndWindowLabel);
		
		cancelButton = new JButton("Abbrechen");

		ActionListener onSubmit = e -> onSubmitFunction(depositOrWithdrawOnOk, onCloseOperation);
		
		// Add the submit action 
		submitButton.addActionListener(onSubmit);
		inputField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	        		onSubmitFunction(depositOrWithdrawOnOk, onCloseOperation);
	            }
			}
		});
		
		// Add the onCloseAction
		cancelButton.addActionListener(e -> {
			// clear the field
			this.inputField.setText("");
			
			// Run onCloseAction and close the small window
			onCloseOperation.run();
			dispose();
		});
		
		panel.add(submitButton);
		panel.add(cancelButton);
	}
}
