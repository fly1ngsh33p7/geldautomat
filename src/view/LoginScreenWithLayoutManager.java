package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginScreenWithLayoutManager extends JPanel {
	private JButton loginButton;
	private JPasswordField passwordField;
	private JTextField inputBlz;
	private JTextField inputAccountNumber;
	private JLabel bankCodeRequiredLabel;
	private JLabel accountNumberRequiredLabel;
	private JLabel passwordRequiredLabel;

	public LoginScreenWithLayoutManager() {
		setPreferredSize(new Dimension(300, 180));
		setLayout(new GridBagLayout());

		initLoginButton();
		initLabels();
		initInputRequiredLabels();
		initFields();
	}

	public void initFields() {
		passwordField = new JPasswordField();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 0, 5, 0);
		add(passwordField, gbc);

		inputBlz = new JTextField();
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
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 0, 5, 0);
		add(inputBlz, gbc);
		inputBlz.setColumns(10);

		inputAccountNumber = new JTextField();
		inputAccountNumber.setColumns(10);
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 5, 0);
		add(inputAccountNumber, gbc);
	}

	/**
	 * initializes the labels that need no interaction, therefore local variables
	 * are enough.
	 */
	public void initLabels() {
		JLabel pageLabel = new JLabel("Bankautomat");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(5, 5, 5, 5);
		add(pageLabel, gbc);

		// JTextField labels
		JLabel bankCodeLabel = new JLabel("BLZ");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(5, 5, 5, 5);
		add(bankCodeLabel, gbc);

		JLabel accountNumberLabel = new JLabel("Kontonummer");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(5, 5, 5, 5);
		add(accountNumberLabel, gbc);

		JLabel passwordLabel = new JLabel("Passwort");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(5, 5, 5, 5);
		add(passwordLabel, gbc);
	}

	public void initInputRequiredLabels() {
		bankCodeRequiredLabel = new JLabel("* bitte ausfüllen");
		bankCodeRequiredLabel.setVisible(false);
		bankCodeRequiredLabel.setForeground(Color.RED);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(5, 0, 5, 0);
		add(bankCodeRequiredLabel, gbc);

		accountNumberRequiredLabel = new JLabel("* bitte ausfüllen");
		accountNumberRequiredLabel.setVisible(false);
		accountNumberRequiredLabel.setForeground(Color.RED);
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(5, 0, 5, 0);
		add(accountNumberRequiredLabel, gbc);

		passwordRequiredLabel = new JLabel("* bitte ausfüllen");
		passwordRequiredLabel.setVisible(false);
		passwordRequiredLabel.setForeground(Color.RED);
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(5, 0, 5, 0);
		add(passwordRequiredLabel, gbc);
	}

	public void initLoginButton() {
		loginButton = new JButton("Login");
		loginButton.setFont(new Font("DejaVu Sans", Font.BOLD, 11));
		loginButton.setBackground(new Color(192, 192, 192));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 0, 5, 0);
		add(loginButton, gbc);
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

	public boolean areFieldsFilledAndShowHints() {
		boolean inputBlzFilled = !inputBlz.getText().isEmpty();
		boolean inputAccountNumberFilled = !inputAccountNumber.getText().isEmpty();
		boolean passwordFieldFilled = !(passwordField.getPassword().length == 0);

		if (!inputBlzFilled) {
			inputBlz.setBorder(BorderFactory.createLineBorder(Color.RED));
			bankCodeRequiredLabel.setVisible(true);
		} else {
			inputBlz.setBorder(null);
			bankCodeRequiredLabel.setVisible(false);
			inputBlz.setForeground(new Color(51, 51, 51));
		}

		if (!inputAccountNumberFilled) {
			inputAccountNumber.setBorder(BorderFactory.createLineBorder(Color.RED));
			accountNumberRequiredLabel.setVisible(true);
		} else {
			inputAccountNumber.setBorder(null);
			accountNumberRequiredLabel.setVisible(false);
			inputAccountNumber.setForeground(new Color(51, 51, 51));
		}

		if (!passwordFieldFilled) {
			passwordField.setBorder(BorderFactory.createLineBorder(Color.RED));
			passwordRequiredLabel.setVisible(true);
		} else {
			passwordField.setBorder(null);
			passwordRequiredLabel.setVisible(false);
			passwordField.setForeground(new Color(51, 51, 51));
		}

		return inputBlzFilled && inputAccountNumberFilled && passwordFieldFilled;
	}
}
