package control;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;

import model.Account;
import model.BankManagementSystem;
import view.View;
import view.screens.AccountScreen;
import view.screens.LoginScreen;
import view.windows.Popup;
import view.windows.SmallWindow;
import view.BooleanConsumer;

public class Control {
    private BankManagementSystem bms;
    private View view;
    
    private boolean isWithdrawOrDepositWindowOpen;

    public Control(BankManagementSystem bms, View view) {
        this.bms = bms;
        this.view = view;
        
        this.isWithdrawOrDepositWindowOpen = false;
        
        // Add an ActionListener to the LoginButton
        this.view.getFrame().setResizable(true);
        setupGUI();
        this.view.getFrame().setResizable(false);
    }
    
    public void setIsWithdrawOrDepositWindowOpen(boolean newValue) {
    	this.isWithdrawOrDepositWindowOpen = newValue;
    }
    
    // TODO how to order functions?
    
    private void setupGUI() {
        setupLoginScreen();
        setupAccountScreen();
        
        // Display the login screen initially
        view.changeScreen(View.LOGIN_SCREEN_KEY);
        
        this.view.getFrame().setVisible(true);
    }
    
    private void setupLoginScreen() {
    	LoginScreen loginScreen = this.view.getLoginScreen();
    	JButton loginButton = loginScreen.getLoginButton();
    	CardLayout cardLayout = this.view.getCardLayout();
        JPanel cardPanel = this.view.getCardPanel();
    	
    	loginButton.addActionListener(e -> {
        	// check if all fields are filled
        	if (loginScreen.areFieldsFilledAndShowHints()) {
        		// all fields are filled, try authentication:
        		performLogin(loginScreen, cardLayout, cardPanel);
        	}
        });
        
        KeyAdapter performLoginOnEnter = new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	        		performLogin(loginScreen, cardLayout, cardPanel);
	            }
			}
		};
        
        loginScreen.getInputBlz().addKeyListener(performLoginOnEnter);
        loginScreen.getPasswordField().addKeyListener(performLoginOnEnter);
        loginScreen.getInputAccountNumber().addKeyListener(performLoginOnEnter);
    }
    
    private void setupAccountScreen() {
    	AccountScreen accountScreen = this.view.getAccountScreen();
    	JFrame frame = this.view.getFrame();
    	
        // Create a button on the account screen to switch back to the login screen 
        accountScreen.getLogoutButton().addActionListener(e -> this.view.changeScreen(View.LOGIN_SCREEN_KEY)); //TODO cleanup fields in AccountScreen??

        accountScreen.getOpenWithdrawWindowButton().addActionListener(e -> {
        	if (!isWithdrawOrDepositWindowOpen) {
        		isWithdrawOrDepositWindowOpen = true;
        		
        		Account currentAccount = accountScreen.getCurrentAccount();
        		
        		BooleanConsumer<Double> withdrawMoney = amount -> {
        			//if currentAccount.withdrawMoney(amount) FAILED, do not close SmallWindow -> return false;
        			if(!currentAccount.withdrawMoney(amount)) {
        				Popup.display("Info", "Der Betrag darf nicht negativ sein.", "ok", null, null);
        				return false;
        			}
        			// TODO operation was successful -> update balance on Screen TODO debug: did it really change?
        			return true;
        		};
        		
        		Runnable onCloseOperation = () -> {
        			setIsWithdrawOrDepositWindowOpen(false);
        		};
        		
		        // Open the small window
		        SmallWindow smallWindow = new SmallWindow(frame, "Auszahlen", withdrawMoney, onCloseOperation);
		        smallWindow.setVisible(true);
        	}
        });

        accountScreen.getOpenDepositWindowButton().addActionListener(e -> {
        	if (!isWithdrawOrDepositWindowOpen) {
        		isWithdrawOrDepositWindowOpen = true;
        		
        		Account currentAccount = accountScreen.getCurrentAccount();
        		
        		BooleanConsumer<Double> depositMoney = amount -> {
        			//if currentAccount.depositMoney(amount) FAILED, do not close SmallWindow -> return false;
        			if(!currentAccount.depositMoney(amount)) {
        				Popup.display("Info", "Der Betrag darf nicht negativ sein.", "ok", null, null);
        				return false; //TODO only run, when Popup is closed again -> Callback stuff?  force Popup "always on top"?
        			}
        			// TODO operation was successful -> update balance on Screen TODO debug: did it really change?
        			return true;
        		};
        		
        		Runnable onCloseOperation = () -> {
        			setIsWithdrawOrDepositWindowOpen(false);
        		};
        		
		        // Open the small window
		        SmallWindow smallWindow = new SmallWindow(frame, "Einzahlen", depositMoney, onCloseOperation);
		        smallWindow.setVisible(true);
        	}
        });
    }
   
    private void performLogin(LoginScreen loginScreen, CardLayout cardLayout, JPanel cardPanel) {
    	String bankCode = loginScreen.getInputBlz().getText();
    	char[] pinCharArray = loginScreen.getPasswordField().getPassword();
    	String accountNumberString = loginScreen.getInputAccountNumber().getText();
    	
    	// if correct credentials are provided, show the account screen
    	if (bms.checkCredentials(bankCode, accountNumberString, pinCharArray)) {
    		// FIXME: is that really the final solution? 
    		this.view.changeScreen(View.ACCOUNT_SCREEN_KEY);
    		
    		//TODO was darf ich denn im AccountScreen anzeigen? -> ich brauch die AccountListe und den currentAccount (mit dieser AccountNumber|BankCode-Kombo)
    		//this.view.getAccountScreen().setCurrentAccount() -> die Liste Gibts nicht????, die hat BMS???? (also getAccountsByBankCodeAndAccountNumber() hat die, dann die accountNumber und Typ in der Combobox -> da könnte man über bms wieder den Account zum Anzeigen kriegen. 
    		
    		Account currentAccount = bms.getAccountByBankCodeAndAccountNumber(bankCode, Integer.parseInt(accountNumberString));
    		view.getAccountScreen().setCurrentAccount(currentAccount);
    		
    		// clear TextFields
    		loginScreen.getInputAccountNumber().setText("");
    		loginScreen.getPasswordField().setText("");
    		loginScreen.getInputBlz().setText("");
    	} else {
    		Popup.display("Info", "Login fehlgeschlagen", "OK", "Programm beenden", () -> System.exit(0));
    	}
    }
    
}