package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginScreen extends JPanel {
	private JButton loginButton;
	private JPasswordField passwordField;
	private JTextField inputBlz;
	private JTextField inputAccountNumber;
	private JLabel bankCodeRequiredLabel;
	private JLabel accountNumberRequiredLabel;
	private JLabel passwordRequiredLabel;
	private SpringLayout springLayout;
	private JLabel pageLabel;
	private JLabel bankCodeLabel;
	private JLabel accountNumberLabel;
	private JLabel passwordLabel;

	public LoginScreen() {
		setPreferredSize(new Dimension(380, 270));
		this.springLayout = new SpringLayout();
		setLayout(this.springLayout);

		initLoginButton();
		initLabels();
		initFields();
		initInputRequiredLabels();
	}

	public void initFields() {
		this.passwordField = new JPasswordField();
		this.springLayout.putConstraint(SpringLayout.NORTH, this.passwordField, 120, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.passwordField, 150, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.passwordField, 17, SpringLayout.EAST, this.loginButton);
		add(this.passwordField);

		this.inputBlz = new JTextField();
		this.springLayout.putConstraint(SpringLayout.NORTH, this.inputBlz, 57, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.inputBlz, 150, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.inputBlz, 17, SpringLayout.EAST, this.loginButton);
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
		add(this.inputBlz);

		this.inputAccountNumber = new JTextField();
		this.springLayout.putConstraint(SpringLayout.NORTH, this.inputAccountNumber, 89, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.inputAccountNumber, 150, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.SOUTH, this.inputAccountNumber, -72, SpringLayout.SOUTH, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.inputAccountNumber, 17, SpringLayout.EAST, this.loginButton);
		this.inputAccountNumber.setColumns(10);
		add(this.inputAccountNumber);
	}

	/**
	 * Initializes the labels that need no interaction, therefore local variables
	 * are enough.
	 */
	public void initLabels() {
		JLabel pageLabel = new JLabel("Bankautomat");
		this.springLayout.putConstraint(SpringLayout.NORTH, pageLabel, 12, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, pageLabel, 12, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, pageLabel, -266, SpringLayout.EAST, this);
		add(pageLabel);

		// JTextField labels
		JLabel bankCodeLabel = new JLabel("BLZ");
		this.springLayout.putConstraint(SpringLayout.NORTH, bankCodeLabel, 59, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, bankCodeLabel, 105, SpringLayout.WEST, this);
		add(bankCodeLabel);

		JLabel accountNumberLabel = new JLabel("Kontonummer");
		this.springLayout.putConstraint(SpringLayout.NORTH, accountNumberLabel, 91, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, accountNumberLabel, 33, SpringLayout.WEST, this);
		add(accountNumberLabel);

		JLabel passwordLabel = new JLabel("Passwort");
		this.springLayout.putConstraint(SpringLayout.NORTH, passwordLabel, 122, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, passwordLabel, 65, SpringLayout.WEST, this);
		add(passwordLabel);
	}

	public void initInputRequiredLabels() {
		this.bankCodeRequiredLabel = new JLabel("* bitte ausfüllen");
		this.springLayout.putConstraint(SpringLayout.WEST, this.bankCodeRequiredLabel, 9, SpringLayout.EAST, this.inputBlz);
		this.springLayout.putConstraint(SpringLayout.EAST, this.bankCodeRequiredLabel, -5, SpringLayout.EAST, this);
		this.bankCodeRequiredLabel.setSize(new Dimension(140, bankCodeRequiredLabel.getPreferredSize().height));
		this.springLayout.putConstraint(SpringLayout.NORTH, this.bankCodeRequiredLabel, 59, SpringLayout.NORTH, this);
		this.bankCodeRequiredLabel.setVisible(false);
		this.bankCodeRequiredLabel.setForeground(Color.RED);
		add(this.bankCodeRequiredLabel);

		this.accountNumberRequiredLabel = new JLabel("* bitte ausfüllen");
		this.springLayout.putConstraint(SpringLayout.WEST, this.accountNumberRequiredLabel, 9, SpringLayout.EAST, this.inputAccountNumber);
		this.springLayout.putConstraint(SpringLayout.EAST, this.accountNumberRequiredLabel, -5, SpringLayout.EAST, this);
		this.accountNumberRequiredLabel.setSize(new Dimension(140, accountNumberRequiredLabel.getPreferredSize().height));
		this.springLayout.putConstraint(SpringLayout.NORTH, this.accountNumberRequiredLabel, 91, SpringLayout.NORTH, this);
		this.accountNumberRequiredLabel.setVisible(false);
		this.accountNumberRequiredLabel.setForeground(Color.RED);
		add(this.accountNumberRequiredLabel);

		this.passwordRequiredLabel = new JLabel("* bitte ausfüllen");
		this.springLayout.putConstraint(SpringLayout.WEST, this.passwordRequiredLabel, 9, SpringLayout.EAST, this.passwordField);
		this.springLayout.putConstraint(SpringLayout.EAST, this.passwordRequiredLabel, -5, SpringLayout.EAST, this);
		this.passwordRequiredLabel.setSize(new Dimension(140, passwordRequiredLabel.getPreferredSize().height));
		this.springLayout.putConstraint(SpringLayout.NORTH, this.passwordRequiredLabel, 122, SpringLayout.NORTH, this);
		this.passwordRequiredLabel.setVisible(false);
		add(this.passwordRequiredLabel);
		this.passwordRequiredLabel.setForeground(Color.RED);
	}

	public void initLoginButton() {
		this.loginButton = new JButton("Login");
		springLayout.putConstraint(SpringLayout.NORTH, this.loginButton, 155, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, this.loginButton, 165, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.SOUTH, this.loginButton, -10, SpringLayout.SOUTH, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.loginButton, -145, SpringLayout.EAST, this);
		this.loginButton.setFont(new Font("DejaVu Sans", Font.BOLD, 11));
		this.loginButton.setBackground(new Color(192, 192, 192));
		add(loginButton);
	}

	public boolean areFieldsFilledAndShowHints() {
		boolean inputBlzFilled = !inputBlz.getText().isEmpty();
		boolean inputAccountNumberFilled = !inputAccountNumber.getText().isEmpty();
		boolean passwordFieldFilled = !(passwordField.getPassword().length == 0);

		if (!inputBlzFilled) {
			bankCodeRequiredLabel.setVisible(true);
		} else {
			bankCodeRequiredLabel.setVisible(false);
		}

		if (!inputAccountNumberFilled) {
			accountNumberRequiredLabel.setVisible(true);
		} else {
			accountNumberRequiredLabel.setVisible(false);
		}

		if (!passwordFieldFilled) {
			passwordRequiredLabel.setVisible(true);
		} else {
			passwordRequiredLabel.setVisible(false);
		}

		return inputBlzFilled && inputAccountNumberFilled && passwordFieldFilled;
	}

	public JButton getLoginButton() {
		return loginButton;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public JTextField getInputBlz() {
		return inputBlz;
	}

	public JTextField getInputAccountNumber() {
		return inputAccountNumber;
	}
}
