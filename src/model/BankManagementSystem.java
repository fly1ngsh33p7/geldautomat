package model;

import java.util.HashSet;
import java.util.Set;

/**
 * The BankManagementSystem class represents a system that manages all the Account objects from the provided databaseFile.
 * It holds references to all Banks and Owners associated with these accounts.
 * 
 * This class follows the Singleton design pattern, meaning that there can be only one instance of the BankManagementSystem.
 * The single instance can be obtained using the {@link #getInstance()} method.
 */
public class BankManagementSystem {
	private static BankManagementSystem instance;
	
	// it is unclear to me if BMS needs to know these all: (Login uses just accounts, the rest is not needed for anything)
    private Set<Bank> banks;
    private Set<Account> accounts;
    private Set<Owner> owners;
    
    private BankManagementSystem() {
    	this.banks = new HashSet<Bank>();
    	this.accounts = new HashSet<Account>();
    	this.owners = new HashSet<Owner>();
    	
    	// create Banks, Owners and Accounts from data  
    	(new DataImportHelper()).fillBanksAccountsAndOwners(this.accounts, this);
    }
    
    /**
     * Returns the single instance of the BankManagementSystem.
     *
     * @return The BankManagementSystem instance.
     */
    public static BankManagementSystem getInstance() {
    	if (instance == null) {
    		instance = new BankManagementSystem();
    		return instance;
    	}
    	return instance;
    }
    
    /**
     * Transfers money from one account to another.
     *
     * @param from                       The account from which the money will be transferred.
     * @param to                         The account to which the money will be transferred.
     * @param amount                     The amount of money to be transferred.
     * @param skipIsWithinOverdraftAmountCheck Set to true to skip the overdraft amount check for CheckingAccount. 
     *                                      If false, CheckingAccount will throw an exception if the account goes into overdraft.
     * @return True if the transfer is successful; otherwise, false.
     * @throws NegativeAmountException                    If the amount is negative.
     * @throws AmountHigherThanMoneyWithOverdraftException If the amount exceeds the balance + overdraft amount for CheckingAccount.
     * @throws UserCanOnlyAffordWithOverdraftException     If the account can afford the amount only with the overdraft for CheckingAccount.
     * @throws NotEnoughMoneyException                    If there is not enough money in the account to perform the transfer.
     */
    public boolean transferMoney(Account from, Account to, double amount, boolean skipIsWithinOverdraftAmountCheck) throws NegativeAmountException, AmountHigherThanMoneyWithOverdraftAmountException, UserCanOnlyAffordWithOverdraftException, NotEnoughMoneyException {
    	// check if the account can afford the amount (only if skipIsWithinOverdraftAmountCheck is not set)
    	if (from instanceof CheckingAccount && !skipIsWithinOverdraftAmountCheck) {
    		// throw an Exception when the account would get into overdraft 
    		if ( ((CheckingAccount) from).canUserAffordOnlyWithOverdraft(amount) ) {
    			throw new UserCanOnlyAffordWithOverdraftException();
    		}
    	} 

    	// check if there is enough money (normal SavingAccount Check - CheckingAccount: see if balance + overdraft is enough)
		if (!from.canUserAfford(amount)) {
			throw new NotEnoughMoneyException();
		}
    	
    	// transfer the amount "from" -> "to" 
    	try {
			from.withdrawMoney(amount);
		} catch (NegativeAmountException | NotEnoughMoneyException e) {
			// there shouldn't be an error anymore
			return false;
		}
    	// if withdrawing was successful:
    	to.depositMoney(amount);
    	return true;
    }
    
    /**
     * Looks through the accounts and returns the first account matching the provided bankCode and accountNumber.
     *
     * @param bankCode       The bank code for which the account is to be found.
     * @param accountNumber  The account number for which the account is to be found.
     * @return The matching account or null if no account is found.
     */
    public Account getAccountByBankCodeAndAccountNumber(String bankCode, int accountNumber) {
    	for (Account currentAccount : this.accounts) {
    		if (currentAccount.getBank().getBankCode().equals(bankCode) && currentAccount.getAccountNumber() == accountNumber) {
                return currentAccount;
            }
    	}
    	
    	// Account not found
    	return null; 
    }
    
    /**
     * Checks the provided credentials for authentication.
     *
     * @param bankCode             The bank code associated with the account.
     * @param accountNumberString  The account number as a string associated with the account.
     * @param pinCharArray         The PIN as a character array for authentication.
     * @return True if the credentials are valid and authenticated; otherwise, false.
     */
    public boolean checkCredentials(String bankCode, String accountNumberString, char[] pinCharArray) {
    	int pin;
    	int accountNumber;
    	// try to interpret the pinString and the accountNumberString, a format error means
    	// wrong characters and failed authentication
    	try {
    		pin = Integer.parseInt(String.valueOf(pinCharArray));
    		accountNumber = Integer.parseInt(accountNumberString);
    	} catch (NumberFormatException e) {
    		return false;
    	}
    	
    	// try to get the account that matches the bankCode and accountNumber
    	Account account = getAccountByBankCodeAndAccountNumber(bankCode, accountNumber);
    	
    	// if the provided bankCode and accountNumber don't match any account, authentication failed
    	if (account == null) {
    		return false;
    	}
    	
    	// returns true if the pin matches
    	return account.getPin() == pin;
    }
    
    public Set<Account> getAccounts() {
    	return accounts;
    }

}