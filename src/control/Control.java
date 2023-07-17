package control;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import model.BankManagementSystem;
import view.AccountScreen;
import view.LoginScreen;
import view.Popup;
import view.SmallWindow;
import view.View;

public class Control {
    private BankManagementSystem bms;
    private View view;
    
    private boolean isWithdrawOrDepositWindowOpen;

    public Control(BankManagementSystem bms, View view) {
        this.bms = bms;
        this.view = view;
        
        this.isWithdrawOrDepositWindowOpen = false;
        
        // Add an ActionListener to the LoginButton
        setupGUI();
    }
    
    // TODO how to order functions?
    
    private void setupGUI() {
    	// create local variables to reduce code size
    	LoginScreen loginScreen = this.view.getLoginScreen();
    	AccountScreen accountScreen = this.view.getAccountScreen();
        JButton loginButton = loginScreen.getLoginButton();
        CardLayout cardLayout = this.view.getCardLayout();
        JPanel cardPanel = this.view.getCardPanel();
        JFrame frame = this.view.getFrame();
    	
        
        //---Login---------------------------
        loginButton.addActionListener(e -> {
        	// check if all fields are filled
        	if (!loginScreen.areFieldsFilledAndShowHints()) {
        		// TODO show which fields are missing by highlighting them
        	} else {
        		// all fields are filled, try authentication:
        		performLogin(loginScreen, cardLayout, cardPanel);
        	}
        });
        
        KeyAdapter performLoginOnEnter = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	        		performLogin(loginScreen, cardLayout, cardPanel);
	            }
			}
		};
        
        loginScreen.getInputBlz().addKeyListener(performLoginOnEnter);
        loginScreen.getPasswordField().addKeyListener(performLoginOnEnter);
        loginScreen.getInputAccountNumber().addKeyListener(performLoginOnEnter);
        //-----------------------------------
        
        

        // Create a button on the account screen to switch back to the login screen 
        accountScreen.getLogoutButton().addActionListener(e -> this.view.changeScreen(View.LOGIN_SCREEN_KEY)); //TODO cleanup fields in AccountScreen??

        accountScreen.getOpenWithdrawWindowButton().addActionListener(e -> {
        	if (!isWithdrawOrDepositWindowOpen) {
		    	// TODO reset Variable on close -> runnable that does that? -> constructor of SmallWindow
		        // Open the small window
		        SmallWindow smallWindow = new SmallWindow(frame, "Auszahlen");
		        smallWindow.setVisible(true);
        	}
        });

        accountScreen.getOpenDepositWindowButton().addActionListener(e -> {
        	if (!isWithdrawOrDepositWindowOpen) {
		    	// TODO reset Variable on close -> runnable that does that? -> constructor of SmallWindow
		        // Open the small window
		        SmallWindow smallWindow = new SmallWindow(frame, "Einzahlen");
		        smallWindow.setVisible(true);
        	}
        });
        
        
        // Display the login screen initially
        cardLayout.show(cardPanel, "Login");

        // Set the frame size, pack, and make it visible
        frame.setPreferredSize(new Dimension(390, 210));
        frame.pack();
        frame.setVisible(true);
    }
    
    private void performLogin(LoginScreen loginScreen, CardLayout cardLayout, JPanel cardPanel) {
    	String bankCode = loginScreen.getInputBlz().getText();
    	char[] pinCharArray = loginScreen.getPasswordField().getPassword();
    	String accountNumberString = loginScreen.getInputAccountNumber().getText();
    	
    	// if correct credentials are provided, show the account screen
    	if (bms.checkCredentials(bankCode, accountNumberString, pinCharArray)) {
    		// FIXME: is that really the final solution? 
    		this.view.changeScreen(View.ACCOUNT_SCREEN_KEY);
    		
    		// clear TextFields
    		loginScreen.getInputAccountNumber().setText("");
    		loginScreen.getPasswordField().setText("");
    		loginScreen.getInputBlz().setText("");
    	} else {
    		Popup.display("Info", "PIN inkorrekt", "OK", "Programm beenden", () -> System.exit(0));
    	}
    }
    

    /*
    public static void processUserInput(String input) {
        // Process the user input
        // Example implementation:
        //bms.processInput(input);

        // Retrieve the updated data from the bms
        //String data = bms.getData();

        // Update the View with the updated data
        //view.displayData(data);
    }

    public static void cancelAction() {
        // Handle cancel action if needed
        // Example implementation:
        //view.clearInputField();
    }
    */
}