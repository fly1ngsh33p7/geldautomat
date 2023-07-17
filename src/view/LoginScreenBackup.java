package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginScreenBackup extends JPanel {
	private JButton loginButton;
	private JPasswordField passwordField;
	private JTextField inputBlz;
	private JTextField inputAccountNumber;
	private JLabel bankCodeRequiredLabel;
	private JLabel accountNumberRequiredLabel;
	private JLabel passwordRequiredLabel;

	public LoginScreenBackup() {
		//super.setPreferredSize(new Dimension(300, 180));
		this.setLayout(null);

		initLoginButton();
		initLabels();
		initInputRequiredLabels();
		initFields();
	}

	public void initFields() {
		this.passwordField = new JPasswordField();
		this.passwordField.setBounds(150, 120, 114, 19);
		add(this.passwordField);

		this.inputBlz = new JTextField();
		this.inputBlz.setForeground(new Color(51, 51, 51));
		this.inputBlz.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// Check if pressed key is alphanumeric
				String numbers = "0123456789";
				String abc = "abcdefghijklmnopqrstuvwxyz";
				if ((numbers + abc + abc.toUpperCase()).indexOf(e.getKeyChar()) == -1) {
					// TODO
					return;
				}
			}
		});
		this.inputBlz.setBounds(150, 57, 114, 19);
		add(this.inputBlz);
		this.inputBlz.setColumns(10);

		this.inputAccountNumber = new JTextField();
		this.inputAccountNumber.setColumns(10);
		this.inputAccountNumber.setBounds(150, 89, 114, 19);
		add(this.inputAccountNumber);
	}

	/**
	 * initializes the labels that need no interaction, therefore local variables
	 * are enough.
	 */
	public void initLabels() {
		JLabel pageLabel = new JLabel("Bankautomat");
		pageLabel.setBounds(12, 12, 95, 15);
		add(pageLabel);

		// JTextField labels
		JLabel bankCodeLabel = new JLabel("BLZ");
		bankCodeLabel.setBounds(105, 59, 27, 15);
		add(bankCodeLabel);

		JLabel accountNumberLabel = new JLabel("Kontonummer");
		accountNumberLabel.setBounds(33, 91, 99, 15);
		add(accountNumberLabel);

		JLabel passwordLabel = new JLabel("Passwort");
		passwordLabel.setBounds(65, 122, 67, 15);
		add(passwordLabel);
	}

	public void initInputRequiredLabels() {
		this.bankCodeRequiredLabel = new JLabel("* bitte ausfüllen");
		this.bankCodeRequiredLabel.setVisible(false);
		this.bankCodeRequiredLabel.setForeground(Color.RED);
		this.bankCodeRequiredLabel.setBounds(270, 59, 114, 15);
		add(this.bankCodeRequiredLabel);

		this.accountNumberRequiredLabel = new JLabel("* bitte ausfüllen");
		this.accountNumberRequiredLabel.setVisible(false);
		this.accountNumberRequiredLabel.setForeground(Color.RED);
		this.accountNumberRequiredLabel.setBounds(270, 91, 114, 15);
		add(this.accountNumberRequiredLabel);

		this.passwordRequiredLabel = new JLabel("* bitte ausfüllen");
		this.passwordRequiredLabel.setVisible(false);
		this.passwordRequiredLabel.setForeground(Color.RED);
		this.passwordRequiredLabel.setBounds(270, 122, 114, 15);
		add(this.passwordRequiredLabel);
	}

	public void initLoginButton() {
		this.loginButton = new JButton("Login");
		this.loginButton.setFont(new Font("DejaVu Sans", Font.BOLD, 11));
		this.loginButton.setBackground(new Color(192, 192, 192));
		this.loginButton.setBounds(164, 156, 73, 25);
		add(loginButton);
	}

	public JButton getLoginButton() {
		return this.loginButton;
	}

	public JPasswordField getPasswordField() {
		return this.passwordField;
	}

	public JTextField getInputBlz() {
		return this.inputBlz;
	}

	public JTextField getInputAccountNumber() {
		return this.inputAccountNumber;
	}

	public boolean areFieldsFilledAndShowHints() {
		// TODO change names if "inputBlz" and "inputAccountNumber" and "passwortField"
		// are changing Names
		boolean inputBlzFilled = !this.inputBlz.getText().isEmpty();
		boolean inputAccountNumberFilled = !this.inputAccountNumber.getText().isEmpty();
		boolean passwordFieldFilled = !(this.passwordField.getPassword().length == 0);

		// if an input field is empty, set a red border, make the text of the JTextField
		// red
		// and show the corresponding requiredLabel
		// reset if not empty
		if (!inputBlzFilled) {
			this.inputBlz.setBorder(BorderFactory.createLineBorder(Color.RED));
			this.bankCodeRequiredLabel.setVisible(true);
		} else {
			this.inputBlz.setBorder(null);
			this.bankCodeRequiredLabel.setVisible(false);
			this.inputBlz.setForeground(new Color(51, 51, 51));
		}

		if (!inputAccountNumberFilled) {
			this.inputAccountNumber.setBorder(BorderFactory.createLineBorder(Color.RED));
			this.accountNumberRequiredLabel.setVisible(true);
		} else {
			this.inputAccountNumber.setBorder(null);
			this.accountNumberRequiredLabel.setVisible(false);
			this.inputAccountNumber.setForeground(new Color(51, 51, 51));
		}

		if (!passwordFieldFilled) {
			this.passwordField.setBorder(BorderFactory.createLineBorder(Color.RED));
			this.passwordRequiredLabel.setVisible(true);
		} else {
			this.passwordField.setBorder(null);
			this.passwordRequiredLabel.setVisible(false);
			this.passwordField.setForeground(new Color(51, 51, 51));
		}

		// if at least one is empty, return false
		return inputBlzFilled && inputAccountNumberFilled && passwordFieldFilled;
	}
}