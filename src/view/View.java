package view;

import javax.swing.*;
import java.awt.*;

public class View {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private LoginScreen loginScreen;
    private AccountScreen accountScreen;

    public View() {
        this.frame = new JFrame("GUI Application");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);

        // Create the card layout and panel to hold screens
        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(this.cardLayout);

        // Create and add screens to the card panel
        this.loginScreen = new LoginScreen();
        this.accountScreen = new AccountScreen();
        this.cardPanel.add(this.loginScreen, "Login");
        this.cardPanel.add(this.accountScreen, "Account");

        // Add the card panel to the main frame
        this.frame.getContentPane().add(this.cardPanel);
    }
    
    public LoginScreen getLoginScreen() {
    	return this.loginScreen;
    }

	public JFrame getFrame() {
		return this.frame;
	}

	public CardLayout getCardLayout() {
		return this.cardLayout;
	}

	public JPanel getCardPanel() {
		return this.cardPanel;
	}

	public AccountScreen getAccountScreen() {
		return this.accountScreen;
	}
}