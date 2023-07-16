package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SmallWindow extends JFrame {
	private JTextField inputField;
	private JButton submitButton;
	private JButton cancelButton;
	private JPanel panel;

	/**
	 * @wbp.parser.constructor
	 */
	public SmallWindow(JFrame parentFrame) {
		initSmallWindow(parentFrame);
	}

	public SmallWindow(JFrame parentFrame, String submitAndWindowlabel) {
		this(parentFrame);

		setTitle(submitAndWindowlabel);
		this.submitButton.setText(submitAndWindowlabel);
	}

	private void initSmallWindow(JFrame parentFrame) {
		setTitle("Small Window");
		setPreferredSize(new Dimension(300, 150));
		setResizable(false);

		// Create the input field for values in euros and cents
		inputField = new JTextField(10);

		// Create the submit and cancel buttons
		submitButton = new JButton("Submit");
		cancelButton = new JButton("Abbrechen");

		// Add action listeners to the buttons
		submitButton.addActionListener(e -> {
			// Process the submitted value
			String value = inputField.getText();
			// Perform any necessary actions with the submitted value
			// ...

			// Close the small window
			dispose();
		});

		cancelButton.addActionListener(e -> {
			// Close the small window
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
}
