package view.windows;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextField;
import javax.swing.JButton;

public class TransferWindow extends JFrame {
	private JLabel transferScreenLabel;
	private JLabel toLabel;
	private JLabel bankCodeLabel;
	private JLabel amountLabel;
	private JTextField bankCodeField;
	private JTextField amountField;
	private JLabel currencyLabel;
	private JLabel accountNumberLabel;
	private JTextField accountNumberField;
	private JButton cancelButton;
	private JButton transferButton;
	
	private JPanel panel;
	private SpringLayout springLayout;

	public TransferWindow(JFrame parentFrame, Runnable onCloseOperation) {
		setPreferredSize(new Dimension(470, 140));
		setResizable(false);

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		setTitle("Überweisen");

		// Add a WindowListener to handle the closing event when clicking the (X)-button
		if (onCloseOperation != null) {
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					onCloseOperation.run();
				}
			});
		}

		initialize();

		setLocationRelativeTo(parentFrame);
		pack();
	}

	private void initialize() {
		initTransferScreenLabel();
		initToLabel();
		initBankCodeLabel();
		initAmountLabel();
		initBankCodeField();
		initAmountField();
		initCurrencyLabel();
		initAccountNumberLabel();
		initAccountNumberField();
		initCancelButton();
		initTransferButton();
	}

	private void initTransferScreenLabel() {
		springLayout = new SpringLayout();
		this.panel.setLayout(springLayout);
		this.transferScreenLabel = new JLabel("Überweisen");
		springLayout.putConstraint(SpringLayout.NORTH, this.transferScreenLabel, 10, SpringLayout.NORTH, this.panel);
		springLayout.putConstraint(SpringLayout.WEST, this.transferScreenLabel, 10, SpringLayout.WEST, this.panel);
		this.transferScreenLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		panel.add(this.transferScreenLabel);
	}

	private void initToLabel() {
		this.toLabel = new JLabel("An:");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.toLabel, 31, SpringLayout.NORTH, this.panel);
		this.springLayout.putConstraint(SpringLayout.WEST, this.toLabel, 10, SpringLayout.WEST, this.panel);
		panel.add(this.toLabel);
	}

	private void initBankCodeLabel() {
		this.bankCodeLabel = new JLabel("BLZ:");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.bankCodeLabel, 50, SpringLayout.NORTH, this.panel);
		this.springLayout.putConstraint(SpringLayout.WEST, this.bankCodeLabel, 51, SpringLayout.WEST, this.panel);
		panel.add(this.bankCodeLabel);
	}

	private void initAmountLabel() {
		this.amountLabel = new JLabel("Betrag:");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.amountLabel, 80, SpringLayout.NORTH, this.panel);
		this.springLayout.putConstraint(SpringLayout.WEST, this.amountLabel, 30, SpringLayout.WEST, this.panel);
		panel.add(this.amountLabel);
	}

	private void initBankCodeField() {
		this.bankCodeField = new JTextField();
		this.springLayout.putConstraint(SpringLayout.NORTH, this.bankCodeField, 50, SpringLayout.NORTH, this.panel);
		this.springLayout.putConstraint(SpringLayout.WEST, this.bankCodeField, 89, SpringLayout.WEST, this.panel);
		this.springLayout.putConstraint(SpringLayout.EAST, this.bankCodeField, 197, SpringLayout.WEST, this.panel);
		panel.add(this.bankCodeField);
		this.bankCodeField.setColumns(10);
	}

	private void initAmountField() {
		this.amountField = new JTextField();
		this.springLayout.putConstraint(SpringLayout.NORTH, this.amountField, 80, SpringLayout.NORTH, this.panel);
		this.springLayout.putConstraint(SpringLayout.WEST, this.amountField, 89, SpringLayout.WEST, this.panel);
		this.springLayout.putConstraint(SpringLayout.EAST, this.amountField, 181, SpringLayout.WEST, this.panel);
		this.amountField.setColumns(10);
		panel.add(this.amountField);
	}

	private void initCurrencyLabel() {
		this.currencyLabel = new JLabel("€");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.currencyLabel, 80, SpringLayout.NORTH, this.panel);
		this.springLayout.putConstraint(SpringLayout.WEST, this.currencyLabel, 187, SpringLayout.WEST, this.panel);
		panel.add(this.currencyLabel);
	}

	private void initAccountNumberLabel() {
		this.accountNumberLabel = new JLabel("Kontonummer:");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.accountNumberLabel, 50, SpringLayout.NORTH, this.panel);
		this.springLayout.putConstraint(SpringLayout.WEST, this.accountNumberLabel, 223, SpringLayout.WEST, this.panel);
		panel.add(this.accountNumberLabel);
	}

	private void initAccountNumberField() {
		this.accountNumberField = new JTextField();
		this.springLayout.putConstraint(SpringLayout.NORTH, this.accountNumberField, 50, SpringLayout.NORTH, this.panel);
		this.springLayout.putConstraint(SpringLayout.WEST, this.accountNumberField, 333, SpringLayout.WEST, this.panel);
		this.springLayout.putConstraint(SpringLayout.EAST, this.accountNumberField, 433, SpringLayout.WEST, this.panel);
		panel.add(this.accountNumberField);
		this.accountNumberField.setColumns(10);
	}

	private void initCancelButton() {
		this.cancelButton = new JButton("Abbrechen");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.cancelButton, 110, SpringLayout.NORTH, this.panel);
		this.springLayout.putConstraint(SpringLayout.WEST, this.cancelButton, 345, SpringLayout.WEST, this.panel);
		this.springLayout.putConstraint(SpringLayout.EAST, this.cancelButton, 464, SpringLayout.WEST, this.panel);
		panel.add(this.cancelButton);
	}

	private void initTransferButton() {
		this.transferButton = new JButton("Überweisen");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.transferButton, 110, SpringLayout.NORTH, this.panel);
		this.springLayout.putConstraint(SpringLayout.WEST, this.transferButton, 220, SpringLayout.WEST, this.panel);
		panel.add(this.transferButton);
	}
}
