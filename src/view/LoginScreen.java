package view;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JPanel {
    private JButton loginButton;

    public LoginScreen() {
        setLayout(new FlowLayout());

        JLabel pageLabel = new JLabel("Bankautomat");
        add(pageLabel);

        loginButton = new JButton("Login");
        add(loginButton);

        JLabel bankCodeLabel = new JLabel("BLZ");
        add(bankCodeLabel);

        JLabel accountNumberLabel = new JLabel("Kontonummer");
        add(accountNumberLabel);

        JLabel passwordLabel = new JLabel("Passwort");
        add(passwordLabel);
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}