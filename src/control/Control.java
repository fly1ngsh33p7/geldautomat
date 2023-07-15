package control;

import java.awt.Dimension;
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

    public Control(BankManagementSystem bms, View view) {
        this.bms = bms;
        this.view = view;
        
        
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
    	
        // Create a button on the login screen to switch to the account screen after checking the credentials.
        
        // TODO same on Enter?
        loginButton.addActionListener(e -> {
        	String bankCode = loginScreen.getInputBlz().getText();
        	char[] pinCharArray = loginScreen.getPasswordField().getPassword(); // TODO String? Can I force only ints? 
        	String accountNumberString = loginScreen.getInputAccountNumber().getText(); // TODO String? Can I force only ints?
        	
        	if (bms.checkCredentials(bankCode, accountNumberString, pinCharArray)) {
        		cardLayout.show(cardPanel, "Account");
        	} else {
        		Popup.display("Info", "PIN inkorrekt", "OK", "Programm beenden", () -> System.exit(0));
        		// TODO clear PIN Textfield - or do I want that?
        	}
        });

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
        frame.setPreferredSize(new Dimension(390, 210));
        frame.pack();
        frame.setVisible(true);
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