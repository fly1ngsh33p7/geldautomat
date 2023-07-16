package view;

import javax.swing.*;
import java.awt.*;

public class View {
	public static final String LOGIN_SCREEN_KEY = "login";
	public static final String ACCOUNT_SCREEN_KEY = "account";
	
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
        this.cardPanel = new JPanel(this.cardLayout); //FIXME nochmal überlegen, ob man hier noch ein extra JPanel macht, statt direkt die Screens zur contentPane zu adden 

        // Create and add screens to the card panel
        this.loginScreen = new LoginScreen();
        this.accountScreen = new AccountScreen();
        
        this.loginScreen.setPreferredSize(new Dimension(300, 180));
        this.accountScreen.setPreferredSize(new Dimension(670, 280));
        
        this.cardPanel.add(this.loginScreen, "Login");
        this.cardPanel.add(this.accountScreen, "Account");

        // Add the card panel to the main frame
        this.frame.getContentPane().add(this.cardPanel);
        
        // Pack the frame to adjust its size based on the preferred sizes of the screens
        this.frame.pack();
    }
    
    private void setupFrame() {
    	
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