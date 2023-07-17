package view;

import javax.swing.*;
import java.awt.*;

public class View {
	public static final String LOGIN_SCREEN_KEY = "login";
	public static final String ACCOUNT_SCREEN_KEY = "account";
	
    private JFrame frame;
    //private CardLayout cardLayout; FIXME
    private JPanel panelAroundCardContainer;
    private LoginScreen loginScreen;
    private AccountScreen accountScreen;
    
    //ChatGPT suggestion
    private JPanel cardContainer;

    public View() {
        this.frame = new JFrame("Geldomat");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.frame.setResizable(false);

        setupCardPanel();
    }
    
    private void setupCardPanel() {
    	// Create the card layout and panel to hold screens
        //this.cardPanel = new JPanel(new CardLayout()); //FIXME nochmal überlegen, ob man hier noch ein extra JPanel macht, statt direkt die Screens zur contentPane zu adden 
    	/*this.cardPanel = new JPanel(new BorderLayout());
    	
    	
    	// Create and add screens to the card panel
        this.loginScreen = new LoginScreen();
        this.accountScreen = new AccountScreen();
        
        this.loginScreen.setPreferredSize(new Dimension(300, 200));
        this.accountScreen.setPreferredSize(new Dimension(670, 280));
        
        //this.cardPanel.add(this.loginScreen, LOGIN_SCREEN_KEY); // cardLayout
        //this.cardPanel.add(this.accountScreen, ACCOUNT_SCREEN_KEY); //cardlayout
        this.cardPanel.add(this.loginScreen, BorderLayout.CENTER);
        this.cardPanel.add(this.accountScreen, BorderLayout.CENTER);*/
        
        
        // CHAT GPT suggestion===============================================
        this.panelAroundCardContainer = new JPanel(new BorderLayout());

        // Create screens
        this.loginScreen = new LoginScreen();
        this.accountScreen = new AccountScreen();

        // Create a panel to hold the screens and use CardLayout for switching
        this.cardContainer = new JPanel(new CardLayout());
        this.cardContainer.add(this.loginScreen, LOGIN_SCREEN_KEY);
        this.cardContainer.add(this.accountScreen, ACCOUNT_SCREEN_KEY);
        
        // Add the card container to the center region of the cardPanel
        this.panelAroundCardContainer.add(this.cardContainer, BorderLayout.CENTER);
        
        // Add the card panel to the main frame
        this.frame.getContentPane().add(this.panelAroundCardContainer);
        
        // Pack the frame to adjust its size based on the preferred sizes of the screens
        this.frame.pack();
        
    }
    
    public void changeScreen(String screenKey) {
    	this.getCardLayout().show(cardContainer, screenKey);
    	
    	// Adjust the size of the frame to fit the preferred size of the active screen;
    	Dimension newDim = this.getCardLayout().preferredLayoutSize(panelAroundCardContainer);
        this.frame.setSize(newDim);
    }
    
    public LoginScreen getLoginScreen() {
    	return this.loginScreen;
    }

	public JFrame getFrame() {
		return this.frame;
	}

	public CardLayout getCardLayout() {
		return (CardLayout) this.cardContainer.getLayout();
	}

	public JPanel getCardPanel() {
		//return this.cardPanel;
		return this.cardContainer;
	}

	public AccountScreen getAccountScreen() {
		return this.accountScreen;
	}
}