package view.windows;

import javax.swing.*;

import view.BooleanConsumer;
import view.KeyAdapterWithSelectSupport;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SmallWindow extends JFrame {
	private JTextField inputField;
	private JButton submitButton;
	private JButton cancelButton;
	private JPanel panel;

	public SmallWindow(JFrame parentFrame, String submitAndWindowLabel, BooleanConsumer<Double> depositOrWithdrawOnOk) {
		this(parentFrame, submitAndWindowLabel, depositOrWithdrawOnOk, null);
	}
	
	public SmallWindow(JFrame parentFrame, String submitAndWindowLabel, BooleanConsumer<Double> depositOrWithdrawOnOk, Runnable onCloseAction) {
		initSmallWindow(parentFrame, depositOrWithdrawOnOk, onCloseAction);
		
		setTitle(submitAndWindowLabel);
		this.submitButton.setText(submitAndWindowLabel);
	}

	private void initSmallWindow(JFrame parentFrame, BooleanConsumer<Double> depositOrWithdrawOnOk, Runnable onCloseAction) {
		setPreferredSize(new Dimension(300, 70));
		setResizable(false);
		
		// Add a WindowListener to handle the closing event when clicking the (X)-button
		if (onCloseAction != null) {
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    onCloseAction.run();
                }
            });
        }

		// Create the input field for values in euros and cents
		inputField = new JTextField(10);
		inputField.setToolTipText("Amount in €, no \"€\" necessary.");
		inputField.addKeyListener(new KeyAdapterWithSelectSupport(() -> formatToNumber(inputField)));
        
		// Create the submit and cancel buttons
		submitButton = new JButton("Submit");
		cancelButton = new JButton("Abbrechen");

		ActionListener onSubmit = e -> onSubmitFunction(depositOrWithdrawOnOk, onCloseAction);
		
		// Add the submit action 
		submitButton.addActionListener(onSubmit);
		inputField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	        		onSubmitFunction(depositOrWithdrawOnOk, onCloseAction);
	            }
			}
		});
		
		// Add the onCloseAction
		cancelButton.addActionListener(e -> {
			// clear the field
			this.inputField.setText("");
			
			// Run onCloseAction and close the small window
			onCloseAction.run();
			dispose();
		});

		// Create a panel to hold the components
		panel = new JPanel();
		panel.add(inputField);
		panel.add(submitButton);
		panel.add(cancelButton);

		// Add the panel to the small window
		getContentPane().add(panel);

		// Set the location of the small window relative to the parent frame
		setLocationRelativeTo(parentFrame);

		// Pack and make the small window visible
		pack();
	}
	
	private void onSubmitFunction(BooleanConsumer<Double> depositOrWithdrawOnOk, Runnable onCloseAction) {
		if (!this.inputField.getText().equals("")) {
			
			// run depositOrWithdrawOnOk
			boolean wasOperationSuccessful = depositOrWithdrawOnOk.accept(Double.parseDouble(this.inputField.getText()));
			
			// Close the small window if it was successful:
			if (wasOperationSuccessful) {
				onCloseAction.run();
				dispose();
			}
		}
	}
	
	private void formatToNumber(JTextField inputField) {
        try {
            String text = inputField.getText();
            
            if (text.equals("")) return;
            
            text = text.replaceAll("[^0-9.,-]", ""); // Remove non-digit characters except commas, dots, and minus sign
            inputField.setText(text);
            
            // Perform additional input validation or checks here TODO wie in dem loadData in BankManagementSystem
            double amount = Double.parseDouble(text);
        } catch (NumberFormatException ex) {
            // Handle format issues or invalid input
        }
    }
}
