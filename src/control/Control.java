package control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;

import model.Account;
import model.AmountHigherThanMoneyWithOverdraftAmountException;
import model.BankManagementSystem;
import model.NegativeAmountException;
import model.NotEnoughMoneyException;
import model.UserCanOnlyAffordWithOverdraftException;
import view.View;
import view.screens.AccountScreen;
import view.screens.LoginScreen;
import view.windows.Popup;
import view.windows.TransferWindow;
import view.windows.DepositOrWithdrawWindow;
import view.BooleanConsumer;
import view.TransferBooleanConsumer;

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
        	// keyTyped() is already used to format the text
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
    }
    
    private void setupAccountScreen() {
    	AccountScreen accountScreen = this.view.getAccountScreen();
    	JFrame frame = this.view.getFrame();
    	
        // Create a button on the account screen to switch back to the login screen 
        accountScreen.getLogoutButton().addActionListener(e -> this.view.changeScreen(View.LOGIN_SCREEN_KEY)); //TODO cleanup fields in AccountScreen??

        accountScreen.getOpenWithdrawWindowButton().addActionListener(e -> {
        	if (!isWithdrawOrDepositWindowOpen) {
        		isWithdrawOrDepositWindowOpen = true;
        		
        		BooleanConsumer<Double> withdrawMoney = amount -> {
        			//if currentAccount.withdrawMoney(amount) FAILED, do not close DepositOrWithdrawWindow -> return false;
        			try {
						this.bms.getCurrentAccount().withdrawMoney(amount);
					} catch (NegativeAmountException nae) {
						Popup.display("Info", "Der Betrag darf nicht negativ sein.", "ok", null, null); 
        				return false;
					} catch (NotEnoughMoneyException neme) {
						Popup.display("Info", "Nicht genug Geld auf dem Konto, der Betrag ist zu hoch.", "ok", null, null); //TODO fenster nicht schließen!
						return false;
					}
    				
        			return true;
        		};
        		
        		Runnable onCloseOperation = () -> {
        			setIsWithdrawOrDepositWindowOpen(false);
        			// update AcountScreen
        			accountScreen.setValuesOfActualAccountLabels(this.bms.getCurrentAccount());
        		};
        		
		        // Open the small window
		        DepositOrWithdrawWindow depositOrWithdrawWindow = new DepositOrWithdrawWindow(frame, "Auszahlen", withdrawMoney, onCloseOperation);
		        depositOrWithdrawWindow.setVisible(true);
        	}
        });

        accountScreen.getOpenDepositWindowButton().addActionListener(e -> {
        	if (!isWithdrawOrDepositWindowOpen) {
        		isWithdrawOrDepositWindowOpen = true;
        		
        		BooleanConsumer<Double> depositMoney = amount -> {
        			//if currentAccount.depositMoney(amount) FAILED, do not close DepositOrWithdrawWindow -> return false;
        			try {
	        			this.bms.getCurrentAccount().depositMoney(amount);
        				return true;
        			} catch(NegativeAmountException ex) {
        				Popup.display("Info", "Der Betrag darf nicht negativ sein.", "ok", null, null); //TODO transfer fenster NICHT schließen
        				return false;
        			}
        		};
        		
        		Runnable onCloseOperation = () -> {
        			setIsWithdrawOrDepositWindowOpen(false);
        			// update AcountScreen
        			accountScreen.setValuesOfActualAccountLabels(this.bms.getCurrentAccount());
        		};
        		
		        // Open the small window
		        DepositOrWithdrawWindow depositOrWithdrawWindow = new DepositOrWithdrawWindow(frame, "Einzahlen", depositMoney, onCloseOperation);
		        depositOrWithdrawWindow.setVisible(true);
        	}
        });
        
        
        Consumer<Integer> switchAccountOnUserSelect = (selectedIndex) -> {
        	// update the current Account
        	bms.setCurrentAccount(bms.getAccountsOfThatBank().get(selectedIndex));
        	// also in view
        	view.getAccountScreen().setActualAccountLabelsAndCombobox(bms.getAccountsOfThatBank().get(selectedIndex), bms.getAccountsOfThatBank(), selectedIndex);        	
        };
        accountScreen.getCombobox().setOnSelect(switchAccountOnUserSelect);
        
        
        accountScreen.getOpenTransferWindowButton().addActionListener(e -> {
        	if (!isWithdrawOrDepositWindowOpen) {
        		isWithdrawOrDepositWindowOpen = true;
        		
        		Runnable onCloseOperation = () -> {
        			setIsWithdrawOrDepositWindowOpen(false);
        			// update AcountScreen
        			accountScreen.setValuesOfActualAccountLabels(this.bms.getCurrentAccount());
        		};
        		
        		// declared here to make it available in the Consumers and Runnables
        		TransferWindow transferWindow = new TransferWindow();
        		
        		//TODO comment, WHY such Consumers are needed (MVC)
        		TransferBooleanConsumer<String, Integer, Double> transferMoney = (bankCode, accountNumber, amount) -> {
        			Account to = bms.getAccountByBankCodeAndAccountNumber(bankCode, accountNumber);
        			
        			Runnable onOkOperationAfterPopupAskingConsent = () -> {
    					try {
    						bms.transferMoney(this.bms.getCurrentAccount(), to, amount, true);
    						
    						// TODO: close the popup (should happen automatically) & close the transferWindow
    						transferWindow.closeWindow();
    					} catch (NegativeAmountException | AmountHigherThanMoneyWithOverdraftAmountException
    							| UserCanOnlyAffordWithOverdraftException | NotEnoughMoneyException e1) {
    						// there should not be any Exception thrown here.
    					}
    				};
        			
        			if (to != null) {
        				try {
							return bms.transferMoney(this.bms.getCurrentAccount(), to, amount, false);
						} catch (NegativeAmountException nae) {
							Popup.displayOnlyWithOkButton("Info", "Ein negativer Betrag kann nicht überwiesen werden.", "OK");
						} catch (AmountHigherThanMoneyWithOverdraftAmountException ahtmwoae) {
							Popup.displayOnlyWithOkButton("Info", "Der Betrag geht über die zugelassene Überziehung hinaus.", "OK");
						} catch (NotEnoughMoneyException neme) {
							Popup.displayOnlyWithOkButton("Info", "Nicht genug Geld auf dem Konto.", "OK");
						} catch (UserCanOnlyAffordWithOverdraftException ucoawoe) {
							Popup.display("Warnung", "Nach Überweisung befindet sich der Kontostand im Soll, trotzdem überweisen?", "Ja", "Nein", onOkOperationAfterPopupAskingConsent, onCloseOperation);
							// TODO ich muss hier iwi ein return rausbringen, falls das gut ging. but how??
						}
        			}
        			return false; // TODO nicht zumachen! nochmal probieren lassen!
        		};
        		
        		transferWindow.setParentFrameTransferMoneyConsumerAndOnCloseOperation(frame, transferMoney, onCloseOperation);
        		transferWindow.setVisible(true);
        	}
        });
    }
   
    private void performLogin(LoginScreen loginScreen, CardLayout cardLayout, JPanel cardPanel) {
    	String bankCode = loginScreen.getInputBlz().getText();
    	char[] pinCharArray = loginScreen.getPasswordField().getPassword();
    	String accountNumberString = loginScreen.getInputAccountNumber().getText();
    	
    	// if correct credentials are provided, show the account screen
    	if (bms.checkCredentials(bankCode, accountNumberString, pinCharArray)) {
    		// show AccountScreen
    		this.view.changeScreen(View.ACCOUNT_SCREEN_KEY);
    		
    		// update account in bms
    		Account currentAccount = bms.getAccountByBankCodeAndAccountNumber(bankCode, Integer.parseInt(accountNumberString));
    		bms.setCurrentAccount(currentAccount);
    		
    		// display the account in AccountScreen
    		List<Account> accountsOfThatBank = bms.getAccountsOfThatBankByOwnerAndBank(currentAccount.getOwner(), currentAccount.getBank());
    		bms.setAccountsOfThatBank(accountsOfThatBank);
    		view.getAccountScreen().setActualAccountLabelsAndCombobox(currentAccount, accountsOfThatBank, accountsOfThatBank.indexOf(currentAccount));
    		
    		// clear TextFields
    		loginScreen.getInputAccountNumber().setText("");
    		loginScreen.getPasswordField().setText("");
    		loginScreen.getInputBlz().setText("");
    	} else {
    		Popup.display("Info", "Login fehlgeschlagen", "OK", "Programm beenden", () -> System.exit(0));
    	}
    }
    
    public void setIsWithdrawOrDepositWindowOpen(boolean newValue) {
    	this.isWithdrawOrDepositWindowOpen = newValue;
    }
    
}