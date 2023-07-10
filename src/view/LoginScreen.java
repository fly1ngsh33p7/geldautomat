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

    public LoginScreen() {
    	super.setSize(new Dimension(300, 180));
        setLayout(null);

        JLabel pageLabel = new JLabel("Bankautomat");
        pageLabel.setBounds(12, 12, 95, 15);
        add(pageLabel);

        loginButton = new JButton("Login");
        loginButton.setBounds(164, 156, 73, 25);
        add(loginButton);

        JLabel bankCodeLabel = new JLabel("BLZ");
        bankCodeLabel.setBounds(105, 59, 27, 15);
        add(bankCodeLabel);

        JLabel accountNumberLabel = new JLabel("Kontonummer");
        accountNumberLabel.setBounds(33, 91, 99, 15);
        add(accountNumberLabel);

        JLabel passwordLabel = new JLabel("Passwort");
        passwordLabel.setBounds(65, 122, 67, 15);
        add(passwordLabel);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(150, 120, 114, 19);
        add(passwordField);
        
        inputBlz = new JTextField();
        inputBlz.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent e) {
        		// Check if pressed key is alphanumeric
        		String numbers = "0123456789";
        		String abc = "abcdefghijklmnopqrstuvwxyz";
        		if((numbers + abc + abc.toUpperCase()).indexOf(e.getKeyChar()) == -1) {
        			// TODO
        			return;
        		}
        	}
        });
        inputBlz.setBounds(150, 57, 114, 19);
        add(inputBlz);
        inputBlz.setColumns(10);
        
        inputAccountNumber = new JTextField();
        inputAccountNumber.setColumns(10);
        inputAccountNumber.setBounds(150, 89, 114, 19);
        add(inputAccountNumber);
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}