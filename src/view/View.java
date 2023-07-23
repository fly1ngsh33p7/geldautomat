package view;

import javax.swing.*;

import view.screens.AccountScreen;
import view.screens.LoginScreen;

import java.awt.*;
import java.util.concurrent.Delayed;

/**
 * The View class represents the graphical user interface (GUI) for the Geldomat application.
 * <p>
 * The View class is responsible for creating and managing the main JFrame, login screen (LoginScreen),
 * and account screen (AccountScreen). It utilizes a CardLayout to switch between these screens based
 * on user actions such as logging in or viewing the account information.
 * <p>
 * The class provides methods to change the active screen and adjust the frame size according to the preferred
 * size of the active screen. It also contains constant keys (LOGIN_SCREEN_KEY and ACCOUNT_SCREEN_KEY) used to
 * identify the different screens within the CardLayout.
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * // Creating a View object
 * View view = new View();
 *
 * // Display the login screen initially
 * view.changeScreen(View.LOGIN_SCREEN_KEY);
 *
 * // After successful login, switch to the account screen
 * view.changeScreen(View.ACCOUNT_SCREEN_KEY);
 * }
 * </pre>
 */
public class View {
	public static final String LOGIN_SCREEN_KEY = "login";
	public static final String ACCOUNT_SCREEN_KEY = "account";
	
    private JFrame frame;
    private JPanel panelAroundCardContainer;
    private LoginScreen loginScreen;
    private AccountScreen accountScreen;
    private JPanel cardContainer;

    /**
     * Constructs the View and initializes the main JFrame and CardLayout for managing the login and account screens.
     */
    public View() {
        this.frame = new JFrame("Geldomat");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setupCardPanel();
        
        this.frame.setResizable(false);
    }
    
    /**
     * Sets up the card panel for managing the login and account screens using a CardLayout.
     * The card panel contains the login screen (LoginScreen) and the account screen (AccountScreen)
     * and switches between them based on user actions.
     * <p>
     * The method initializes the card panel and adds the login and account screens to it using their respective
     * screen keys (LOGIN_SCREEN_KEY and ACCOUNT_SCREEN_KEY). It then adds the card panel to the main frame's
     * content pane and adjusts the frame size based on the preferred sizes of the screens.
     * <p>
     * Note: This method should be called during the initialization of the View class to set up the GUI layout.
     */
    private void setupCardPanel() {
        this.panelAroundCardContainer = new JPanel(new BorderLayout());

        // Create the login and account screens
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
    
    /**
     * Changes the active screen within the CardLayout to the specified screen identified by the given screenKey.
     * Adjusts the frame size to fit the preferred size of the active screen. 
     * (this doesn't work that well when logging out...)
     *
     * @param screenKey The key representing the screen to switch to.
     */
    public void changeScreen(String screenKey) {
    	this.getCardLayout().show(cardContainer, screenKey);
    	this.frame.setResizable(true);
    	
    	// Adjust the size of the frame to fit the preferred size of the active screen;
    	Dimension newDim;
    	if (screenKey.equals(ACCOUNT_SCREEN_KEY)) {
    		newDim = this.accountScreen.getPreferredSize();
    	} else { // screenKey: LOGIN_SCREEN_KEY
    		newDim = this.loginScreen.getPreferredSize();
    	}
    	//FIXME: LoginScreen sieht beim Logout kaputt aus (funktioniert aber), ist zu groß, obwohl in dieser function die korrekte Größe gesetzt wird
        this.frame.setSize(newDim);
        
        this.getCardPanel().repaint();  
        this.getCardPanel().revalidate();
        this.frame.setResizable(false);
    }
    
    // getters
    
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
		return this.cardContainer;
	}

	public AccountScreen getAccountScreen() {
		return this.accountScreen;
	}
}