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
	private SpringLayout layout_1;
	private JLabel pageLabel;
	private JLabel bankCodeLabel;
	private JLabel accountNumberLabel;
	private JLabel passwordLabel;

	public LoginScreen() {
		setPreferredSize(new Dimension(367, 180));
		layout_1 = new SpringLayout();
		setLayout(layout_1);

		initLoginButton();
		initLabels();
		initInputRequiredLabels();
		initFields();
	}

	public void initFields() {
		passwordField = new JPasswordField();
		this.layout_1.putConstraint(SpringLayout.NORTH, this.passwordLabel, 2, SpringLayout.NORTH, this.passwordField);
		this.layout_1.putConstraint(SpringLayout.EAST, this.passwordLabel, -6, SpringLayout.WEST, this.passwordField);
		this.layout_1.putConstraint(SpringLayout.WEST, this.passwordField, 129, SpringLayout.WEST, this);
		this.layout_1.putConstraint(SpringLayout.NORTH, this.loginButton, 12, SpringLayout.SOUTH, this.passwordField);
		this.layout_1.putConstraint(SpringLayout.SOUTH, this.passwordField, -57, SpringLayout.SOUTH, this);
		this.layout_1.putConstraint(SpringLayout.EAST, this.passwordField, -6, SpringLayout.WEST,
				this.passwordRequiredLabel);
		add(passwordField);

		inputBlz = new JTextField();
		this.layout_1.putConstraint(SpringLayout.EAST, this.inputBlz, -6, SpringLayout.WEST,
				this.accountNumberRequiredLabel);
		this.layout_1.putConstraint(SpringLayout.NORTH, this.inputBlz, -140, SpringLayout.SOUTH, this);
		this.layout_1.putConstraint(SpringLayout.WEST, this.inputBlz, 129, SpringLayout.WEST, this);
		inputBlz.setForeground(new Color(51, 51, 51));
		inputBlz.addKeyListener(new KeyAdapter() {
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
		add(inputBlz);

		inputAccountNumber = new JTextField();
		this.layout_1.putConstraint(SpringLayout.WEST, this.inputAccountNumber, 6, SpringLayout.EAST,
				this.accountNumberLabel);
		this.layout_1.putConstraint(SpringLayout.SOUTH, this.inputAccountNumber, -90, SpringLayout.SOUTH, this);
		this.layout_1.putConstraint(SpringLayout.EAST, this.inputAccountNumber, -6, SpringLayout.WEST,
				this.passwordRequiredLabel);
		add(inputAccountNumber);
	}

	/**
	 * Initializes the labels that need no interaction, therefore local variables
	 * are enough.
	 */
	public void initLabels() {
		pageLabel = new JLabel("Bankautomat");
		this.layout_1.putConstraint(SpringLayout.NORTH, this.pageLabel, 4, SpringLayout.NORTH, this);
		this.layout_1.putConstraint(SpringLayout.WEST, this.pageLabel, 11, SpringLayout.WEST, this);
		add(pageLabel);

		// JTextField labels
		passwordLabel = new JLabel("Passwort");
		this.layout_1.putConstraint(SpringLayout.WEST, this.passwordLabel, 56, SpringLayout.WEST, this);
		add(passwordLabel);

		accountNumberLabel = new JLabel("Kontonummer");
		this.layout_1.putConstraint(SpringLayout.EAST, this.accountNumberLabel, 0, SpringLayout.EAST,
				this.passwordLabel);
		add(accountNumberLabel);

		bankCodeLabel = new JLabel("BLZ");
		this.layout_1.putConstraint(SpringLayout.NORTH, this.accountNumberLabel, 16, SpringLayout.SOUTH,
				this.bankCodeLabel);
		this.layout_1.putConstraint(SpringLayout.EAST, this.bankCodeLabel, 0, SpringLayout.EAST, this.passwordLabel);
		add(bankCodeLabel);
	}

	public void initInputRequiredLabels() {
		bankCodeRequiredLabel = new JLabel("* bitte ausfüllen");
		this.layout_1.putConstraint(SpringLayout.NORTH, this.bankCodeRequiredLabel, 0, SpringLayout.NORTH,
				this.passwordLabel);
		bankCodeRequiredLabel.setVisible(false);
		bankCodeRequiredLabel.setForeground(Color.RED);
		add(bankCodeRequiredLabel);

		accountNumberRequiredLabel = new JLabel("* bitte ausfüllen");
		this.layout_1.putConstraint(SpringLayout.EAST, this.bankCodeRequiredLabel, 0, SpringLayout.EAST,
				this.accountNumberRequiredLabel);
		this.layout_1.putConstraint(SpringLayout.NORTH, this.bankCodeLabel, 0, SpringLayout.NORTH,
				this.accountNumberRequiredLabel);
		this.layout_1.putConstraint(SpringLayout.NORTH, this.accountNumberRequiredLabel, 42, SpringLayout.NORTH, this);
		this.layout_1.putConstraint(SpringLayout.EAST, this.accountNumberRequiredLabel, -10, SpringLayout.EAST, this);
		accountNumberRequiredLabel.setVisible(false);
		accountNumberRequiredLabel.setForeground(Color.RED);
		add(accountNumberRequiredLabel);

		passwordRequiredLabel = new JLabel("* bitte ausfüllen");
		this.layout_1.putConstraint(SpringLayout.NORTH, this.passwordRequiredLabel, 18, SpringLayout.SOUTH,
				this.accountNumberRequiredLabel);
		this.layout_1.putConstraint(SpringLayout.EAST, this.passwordRequiredLabel, -10, SpringLayout.EAST, this);
		passwordRequiredLabel.setVisible(false);
		passwordRequiredLabel.setForeground(Color.RED);
		add(passwordRequiredLabel);
	}

	public void initLoginButton() {
		loginButton = new JButton("Login");
		this.layout_1.putConstraint(SpringLayout.WEST, this.loginButton, 139, SpringLayout.WEST, this);
		this.layout_1.putConstraint(SpringLayout.EAST, this.loginButton, -140, SpringLayout.EAST, this);
		loginButton.setFont(new Font("DejaVu Sans", Font.BOLD, 11));
		loginButton.setBackground(new Color(192, 192, 192));
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
