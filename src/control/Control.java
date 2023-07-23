package control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;

import model.Account;
import model.Bank;
import model.BankManagementSystem;
import model.Owner;
import view.View;
import view.screens.AccountScreen;
import view.screens.LoginScreen;
import view.windows.Popup;
import view.windows.TransferWindow;
import view.windows.DepositOrWithdrawWindow;
import view.BooleanConsumer;
import view.TransferBooleanConsumer;
import model.exception.NegativeAmountException;
import model.exception.NotEnoughMoneyException;
import model.exception.AmountHigherThanMoneyWithOverdraftAmountException;
import model.exception.UserCanOnlyAffordWithOverdraftException;

/**
 * The Control class is responsible for controlling the application's flow and user interface. It communicates with the BankManagementSystem
 * and View classes to manage the interactions between the user, the data, and the graphical interface.
 * 
 * It allows users to log in and access their accounts, perform various banking operations, and manage their account details.
 * 
 * The Control class initializes the GUI components, sets up event listeners, and controls the transition between different screens
 * (e.g., login screen and account screen).
 */
public class Control {
    private BankManagementSystem bms;
    private View view;
    
    // this prevents opening more than one of these windows
    private boolean isWithdrawOrDepositOrTransferWindowOpen;
    
    private Account currentAccount;
    private List<Account> accountsOfThatBank;

    /**
     * Constructs a new Control object with the specified BankManagementSystem and View instances.
     *
     * @param bms  The BankManagementSystem to interact with the data and perform banking operations.
     * @param view The View to manage the graphical user interface and display information to the user.
     */
    public Control(BankManagementSystem bms, View view) {
        this.bms = bms;
        this.view = view;
        
        this.isWithdrawOrDepositOrTransferWindowOpen = false;
        
        // Add an ActionListener to the LoginButton
        this.view.getFrame().setResizable(true);
        setupGUI();
        this.view.getFrame().setResizable(false);
    }
    
    /**
     * Sets up the graphical user interface by initializing and configuring the login screen and account screen.
     * It also displays the login screen initially.
     */
    private void setupGUI() {
        setupLoginScreen();
        setupAccountScreen();
        
        // Display the login screen initially
        view.changeScreen(View.LOGIN_SCREEN_KEY);
        
        this.view.getFrame().setVisible(true);
    }
    
    /**
     * Sets up the login screen by adding necessary event listeners to the components. When the login button is clicked or the enter key is pressed
     * while focus is on any of the input fields (bank code, account number, or password), the method performs the login process by calling the
     * {@link #performLogin(LoginScreen, CardLayout, JPanel)} method.
     */
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
        
    	// pressing Enter when in LoginScreen is equivalent to clicking the login button
        KeyAdapter performLoginOnEnter = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					// Perform login when the enter key is pressed
	        		performLogin(loginScreen, cardLayout, cardPanel);
	            }
			}
		};
		// Add the KeyAdapter to the input fields (bank code, account number, and password)
        loginScreen.getInputBlz().addKeyListener(performLoginOnEnter);
        loginScreen.getPasswordField().addKeyListener(performLoginOnEnter);
        loginScreen.getInputAccountNumber().addKeyListener(performLoginOnEnter);
    }
    
    /**
     * The setupAccountScreen method configures the graphical user interface for the account screen and sets up event listeners
     * for various buttons and components on the account screen. It allows users to perform account-related operations such as
     * withdrawing money, depositing money, transferring money, and switching between accounts.
     */
    private void setupAccountScreen() {
    	AccountScreen accountScreen = this.view.getAccountScreen();
    	JFrame frame = this.view.getFrame();
    	
        // Create a button on the account screen to switch back to the login screen 
        accountScreen.getLogoutButton().addActionListener(e -> {
        	this.view.changeScreen(View.LOGIN_SCREEN_KEY);
        	accountScreen.resetActualAccountLabels();
        });

        accountScreen.getOpenWithdrawWindowButton().addActionListener(e -> {
        	if (!isWithdrawOrDepositOrTransferWindowOpen) {
        		isWithdrawOrDepositOrTransferWindowOpen = true;
        		
        		BooleanConsumer<Double> withdrawMoney = amount -> {
        			//if this.bms.getCurrentAccount().withdrawMoney(amount) FAILED, do not close DepositOrWithdrawWindow -> return false;
        			try {
						this.getCurrentAccount().withdrawMoney(amount);
					} catch (NegativeAmountException nae) {
						Popup.display("Info", "Der Betrag darf nicht negativ sein.", "ok", null, null); 
        				return false;
					} catch (NotEnoughMoneyException neme) {
						Popup.display("Info", "Nicht genug Geld auf dem Konto, der Betrag ist zu hoch.", "ok", null, null);
						return false;
					}
    				
        			return true;
        		};
        		
        		Runnable onCloseOperation = () -> {
        			setIsWithdrawOrDepositWindowOpen(false);
        			// update AcountScreen
        			accountScreen.setValuesOfActualAccountLabels(this.getCurrentAccount());
        		};
        		
		        // Open the small window for withdrawing money
		        DepositOrWithdrawWindow depositOrWithdrawWindow = new DepositOrWithdrawWindow(frame, "Auszahlen", withdrawMoney, onCloseOperation);
		        depositOrWithdrawWindow.setVisible(true);
        	}
        });

        accountScreen.getOpenDepositWindowButton().addActionListener(e -> {
        	if (!isWithdrawOrDepositOrTransferWindowOpen) {
        		isWithdrawOrDepositOrTransferWindowOpen = true;
        		
        		BooleanConsumer<Double> depositMoney = amount -> {
        			//if currentAccount.depositMoney(amount) FAILED, do not close DepositOrWithdrawWindow -> return false;
        			try {
	        			this.getCurrentAccount().depositMoney(amount);
        				return true;
        			} catch(NegativeAmountException ex) {
        				Popup.display("Info", "Der Betrag darf nicht negativ sein.", "ok", null, null);
        				return false;
        			}
        		};
        		
        		Runnable onCloseOperation = () -> {
        			setIsWithdrawOrDepositWindowOpen(false);
        			// update AcountScreen
        			accountScreen.setValuesOfActualAccountLabels(this.getCurrentAccount());
        		};
        		
		        // Open the small window for depositing money
		        DepositOrWithdrawWindow depositOrWithdrawWindow = new DepositOrWithdrawWindow(frame, "Einzahlen", depositMoney, onCloseOperation);
		        depositOrWithdrawWindow.setVisible(true);
        	}
        });
        
        
        Consumer<Integer> switchAccountOnUserSelect = (selectedIndex) -> {
        	// update the current Account
        	setCurrentAccount(getAccountsOfThatBank().get(selectedIndex));
        	// also in view
        	view.getAccountScreen().setActualAccountLabelsAndCombobox(getAccountsOfThatBank().get(selectedIndex), getAccountsOfThatBank(), selectedIndex);        	
        };
        accountScreen.getCombobox().setOnSelect(switchAccountOnUserSelect);
        
        
        accountScreen.getOpenTransferWindowButton().addActionListener(e -> {
        	if (!isWithdrawOrDepositOrTransferWindowOpen) {
        		isWithdrawOrDepositOrTransferWindowOpen = true;
        		
        		Runnable onCloseOperation = () -> {
        			setIsWithdrawOrDepositWindowOpen(false);
        			// update AcountScreen
        			accountScreen.setValuesOfActualAccountLabels(this.getCurrentAccount());
        		};
        		
        		// // Initialize the TransferWindow to perform money transfer in the Consumers and Runnables
        		TransferWindow transferWindow = new TransferWindow();
        		
        		// Define a TransferBooleanConsumer to handle transfer operations
        		TransferBooleanConsumer<String, Integer, Double> transferMoney = (bankCode, accountNumber, amount) -> {
        			Account to = bms.getAccountByBankCodeAndAccountNumber(bankCode, accountNumber);
        			
        			Runnable onOkOperationAfterPopupAskingConsent = () -> {
    					try {
    						// Transfer money (with overdraft) from the current account to the specified account
    						bms.transferMoney(this.getCurrentAccount(), to, amount, true);
    						
    						transferWindow.closeWindow();
    					} catch (NegativeAmountException | AmountHigherThanMoneyWithOverdraftAmountException
    							| UserCanOnlyAffordWithOverdraftException | NotEnoughMoneyException e1) {
    						// there should not be any Exception thrown here.
    					}
    				};
        			
        			if (to != null) {
        				try {
							return bms.transferMoney(this.getCurrentAccount(), to, amount, false);
						} catch (NegativeAmountException nae) {
							Popup.displayOnlyWithOkButton("Info", "Ein negativer Betrag kann nicht überwiesen werden.", "OK");
						} catch (AmountHigherThanMoneyWithOverdraftAmountException ahtmwoae) {
							Popup.displayOnlyWithOkButton("Info", "Der Betrag geht über die zugelassene Überziehung hinaus.", "OK");
						} catch (NotEnoughMoneyException neme) {
							Popup.displayOnlyWithOkButton("Info", "Nicht genug Geld auf dem Konto.", "OK");
						} catch (UserCanOnlyAffordWithOverdraftException ucoawoe) {
							// Ask for user consent to proceed with the transfer even if the account will be in debt
							Popup.display("Warnung", "Nach Überweisung befindet sich der Kontostand im Soll, trotzdem überweisen?", "Ja", "Nein", onOkOperationAfterPopupAskingConsent, onCloseOperation);
						}
        			}
        			return false;
        		};
        		
        		 // Set the TransferWindow with appropriate consumers and runnables
        		transferWindow.setParentFrameTransferMoneyConsumerAndOnCloseOperation(frame, transferMoney, onCloseOperation);
        		transferWindow.setVisible(true);
        	}
        });
    }
    
    /**
	 * This method searches through the internal list of accounts and returns a list of accounts
	 * that belong to the specified {@code owner} and are associated with the specified {@code bank}.
	 * 
	 * The resulting list is sorted based on the sorting logic defined in the Account class (ascending
	 * by accountNumber).
	 *
	 * @param owner The owner for whom the accounts are to be retrieved.
	 * @param bank  The bank from which to retrieve the accounts.
	 * @return A list of accounts belonging to the specified owner and bank.
	 *         The list may be empty if no accounts match the criteria.
	 */
	public List<Account> getAccountsOfThatBankByOwnerAndBank(Owner owner, Bank bank) {
		List<Account> accounts = new ArrayList<Account>();
		
		// Iterate through all accounts to find the matching ones
		for (Account currentAccount : this.bms.getAccounts()) {
			if (currentAccount.getOwner().equals(owner) && currentAccount.getBank().equals(bank)) {
				accounts.add(currentAccount);
			}
		}
		
		// Sort the resulting list of accounts by accountNumber (asc)
		Collections.sort(accounts);
		
		return accounts;
	}
   
    /**
     * Performs the login process using the provided login credentials from the LoginScreen.
     * If the correct credentials are provided, it switches to the AccountScreen, updates the current account in the BankManagementSystem (BMS),
     * and displays the account details on the AccountScreen. If the login fails, it displays an error message using a popup.
     *
     * @param loginScreen The LoginScreen object containing the user's input for the login process.
     * @param cardLayout  The CardLayout used to manage the card panels in the main frame.
     * @param cardPanel   The main panel that holds the card container for switching between different screens.
     */
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
    		setCurrentAccount(currentAccount);
    		
    		// display the account in AccountScreen
    		List<Account> accountsOfThatBank = getAccountsOfThatBankByOwnerAndBank(currentAccount.getOwner(), currentAccount.getBank());
    		setAccountsOfThatBank(accountsOfThatBank);
    		view.getAccountScreen().setActualAccountLabelsAndCombobox(currentAccount, accountsOfThatBank, accountsOfThatBank.indexOf(currentAccount));
    		
    		// clear TextFields
    		loginScreen.getInputAccountNumber().setText("");
    		loginScreen.getPasswordField().setText("");
    		loginScreen.getInputBlz().setText("");
    	} else {
    		// Display an error message using a Popup
    		Popup.display("Info", "Login fehlgeschlagen", "OK", "Programm beenden", () -> System.exit(0));
    	}
    }
    
    public void setIsWithdrawOrDepositWindowOpen(boolean newValue) {
    	this.isWithdrawOrDepositOrTransferWindowOpen = newValue;
    }
    
    public List<Account> getAccountsOfThatBank() {
		return accountsOfThatBank;
	}
	
	public void setAccountsOfThatBank(List<Account> accountsOfThatBank) {
		this.accountsOfThatBank = accountsOfThatBank;
	}
	
	public Account getCurrentAccount() {
		return currentAccount;
	}

	public void setCurrentAccount(Account currentAccount) {
		this.currentAccount = currentAccount;
	}
    
}