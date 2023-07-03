package view;

import javax.swing.*;
import java.awt.*;

public class ViewBackup {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private LoginScreen loginScreen;
    private AccountScreen accountScreen;

    public ViewBackup() {
        frame = new JFrame("GUI Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the card layout and panel to hold screens
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create and add screens to the card panel
        loginScreen = new LoginScreen();
        accountScreen = new AccountScreen();
        cardPanel.add(loginScreen, "Login");
        cardPanel.add(accountScreen, "Account");

        // Add the card panel to the main frame
        frame.getContentPane().add(cardPanel);

        // Create a button on the login screen to switch to the account screen
        loginScreen.getLoginButton().addActionListener(e -> cardLayout.show(cardPanel, "Account"));

        // Create a button on the account screen to switch back to the login screen
        accountScreen.getLogoutButton().addActionListener(e -> cardLayout.show(cardPanel, "Login"));

        accountScreen.getOpenWithdrawWindowButton().addActionListener(e -> {
            // Open the small window
            SmallWindow smallWindow = new SmallWindow(frame, "Auszahlen");
            smallWindow.setVisible(true);
        });

        accountScreen.getOpenDepositWindowButton().addActionListener(e -> {
            // Open the small window
            SmallWindow smallWindow = new SmallWindow(frame, "Einzahlen");
            smallWindow.setVisible(true);
        });

        // Display the login screen initially
        cardLayout.show(cardPanel, "Login");

        // Set the frame size, pack, and make it visible
        frame.setPreferredSize(new Dimension(400, 300));
        frame.pack();
        frame.setVisible(true);
    }

}