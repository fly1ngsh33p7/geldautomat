package view.screens;

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

	private void initFields() {
		this.passwordField = new JPasswordField();
		this.springLayout.putConstraint(SpringLayout.NORTH, this.passwordField, 120, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.passwordField, 150, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.passwordField, 17, SpringLayout.EAST, this.loginButton);
		this.passwordField.setToolTipText("erlaubt (positive) Ganzzahlen.");
		this.passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// only allow (positive!) whole Numbers characters
	            removeCharactersFromField(passwordField, "[^0-9]");
			}
		});
		add(this.passwordField);

		this.inputBlz = new JTextField();
		this.springLayout.putConstraint(SpringLayout.NORTH, this.inputBlz, 57, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.inputBlz, 150, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.inputBlz, 17, SpringLayout.EAST, this.loginButton);
		this.inputBlz.setForeground(new Color(51, 51, 51));
		this.inputBlz.setToolTipText("erlaubt alphanumerische Zeichen");
		this.inputBlz.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\u0001') { // Check for Ctrl+A key combination
					inputBlz.selectAll(); // Select all text when Ctrl+A is pressed
		        } else {
					int cursorPosition = inputBlz.getCaretPosition();
					
					// only allow alphanumeric characters
		            removeCharactersFromField(inputBlz, "[^0-9A-Za-z]");
		            
		            inputBlz.setCaretPosition(cursorPosition);
		        }
			}
		});
		add(this.inputBlz);

		this.inputAccountNumber = new JTextField();
		this.springLayout.putConstraint(SpringLayout.NORTH, this.inputAccountNumber, 89, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.inputAccountNumber, 150, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.SOUTH, this.inputAccountNumber, -72, SpringLayout.SOUTH, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.inputAccountNumber, 17, SpringLayout.EAST, this.loginButton);
		this.inputAccountNumber.setToolTipText("erlaubt alphanumerische Zeichen");
		this.inputAccountNumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// only allow alphanumeric characters
	            removeCharactersFromField(inputAccountNumber, "[^0-9A-Za-z]");
			}
		});
		this.inputAccountNumber.setColumns(10);
		add(this.inputAccountNumber);
	}

	/**
	 * Initializes the labels that need no interaction, therefore local variables
	 * are enough.
	 */
	private void initLabels() {
		pageLabel = new JLabel("Bankautomat");
		this.springLayout.putConstraint(SpringLayout.NORTH, pageLabel, 12, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, pageLabel, 12, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, pageLabel, -266, SpringLayout.EAST, this);
		add(pageLabel);

		// JTextField labels
		bankCodeLabel = new JLabel("BLZ");
		this.springLayout.putConstraint(SpringLayout.NORTH, bankCodeLabel, 59, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, bankCodeLabel, 105, SpringLayout.WEST, this);
		add(bankCodeLabel);

		accountNumberLabel = new JLabel("Kontonummer");
		this.springLayout.putConstraint(SpringLayout.NORTH, accountNumberLabel, 91, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, accountNumberLabel, 33, SpringLayout.WEST, this);
		add(accountNumberLabel);

		passwordLabel = new JLabel("Passwort");
		this.springLayout.putConstraint(SpringLayout.NORTH, passwordLabel, 122, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, passwordLabel, 65, SpringLayout.WEST, this);
		add(passwordLabel);
	}

	private void initInputRequiredLabels() {
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
		this.springLayout.putConstraint(SpringLayout.NORTH, this.passwordRequiredLabel, 122, SpringLayout.NORTH, this);
		this.passwordRequiredLabel.setSize(new Dimension(140, passwordRequiredLabel.getPreferredSize().height));
		this.passwordRequiredLabel.setVisible(false);
		this.passwordRequiredLabel.setForeground(Color.RED);
		add(this.passwordRequiredLabel);
	}

	private void initLoginButton() {
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
		boolean isInputBlzEmpty = inputBlz.getText().isEmpty();
		boolean isInputAccountNumberEmpty = inputAccountNumber.getText().isEmpty();
		boolean isPasswordFieldEmpty = passwordField.getPassword().length == 0;
		
		// set the visibility to the opposite 
		bankCodeRequiredLabel.setVisible(isInputBlzEmpty);
		accountNumberRequiredLabel.setVisible(isInputAccountNumberEmpty);
		passwordRequiredLabel.setVisible(isPasswordFieldEmpty);

		return !isInputBlzEmpty && !isInputAccountNumberEmpty && !isPasswordFieldEmpty;
	}
	
	/**
	 * TODO: do I want this?
	 *	 Removes characters from a JTextField based on a specified regular expression pattern.
	 *
	 *	 @param field The JTextField from which characters will be removed.
	 *   @param characterRegexToMatch The regular expression pattern specifying the characters to be removed.
	 */
	private void removeCharactersFromField(JTextField field, String characterRegexToMatch) {
		String text = field.getText();
        text = text.replaceAll(characterRegexToMatch, ""); // Remove characters that match characterRegexToMatch
        field.setText(text);
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
