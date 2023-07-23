package view.windows;

import javax.swing.*;

import model.Account;
import view.BooleanConsumer;
import view.KeyAdapterWithSelectSupport;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DepositOrWithdrawWindow extends JFrame {
	private JTextField inputField;
	private JButton submitButton;
	private JButton cancelButton;
	private JPanel panel;

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

	private void initInputField() {
		inputField = new JTextField(10);
		inputField.setToolTipText("positive Amount in €, no \"€\" necessary.");
		inputField.addKeyListener(new KeyAdapterWithSelectSupport(() -> formatToNumber(inputField)));//TODO in der mitte reinschreiben geht iwi nicht MEHR -> vllt doch JFormattedField oder so? oder erst gar nicht formatieren sondern hinweisen, falls ungültig? und TODO: "," nicht als comma erkannt
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
	
	private void onSubmitFunction(BooleanConsumer<Double> depositOrWithdrawOnOk, Runnable onCloseOperation) {
		if (!this.inputField.getText().equals("")) {
			
			// run depositOrWithdrawOnOk
			boolean wasOperationSuccessful = depositOrWithdrawOnOk.accept(Double.parseDouble(this.inputField.getText()));
			
			// Close the small window if it was successful:
			if (wasOperationSuccessful) {
				onCloseOperation.run();
				dispose();
			}
		}
	}
	
	private void formatToNumber(JTextField inputField) {
        try {
            String text = inputField.getText();
            
            if (text.equals("")) return;
            
            text = text.replaceAll("[^0-9.,]", ""); // Remove non-digit characters except commas, dots, and minus sign
            inputField.setText(text);
            
            // Perform additional input validation or checks here TODO wie in dem loadData in BankManagementSystem
            double amount = Double.parseDouble(text);
        } catch (NumberFormatException ex) {
            // Handle format issues or invalid input
        }
    }
}
