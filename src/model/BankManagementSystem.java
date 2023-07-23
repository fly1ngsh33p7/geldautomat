package model;


import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * BankManagementSystem creates all the Bank, Account and Owner objects from the provided databaseFile.
 * It is a Singleton, so there can be only one BankManagementSystem instance.
 * 
 * This is the Control part of MVC.
 */
public class BankManagementSystem {
	private static BankManagementSystem instance;
	
	// TODO idk if BMS needs to know these all: (Login uses just accounts)
    private Set<Bank> banks;
    private Set<Account> accounts;
    private Set<Owner> owners;
    
    private Account currentAccount; // FIXME: I'm not so sure if bms should have this: it's supposed to KNOW all accounts not the current login!
    private List<Account> accountsOfThatBank;// FIXME: I'm not so sure if bms should have this: it's supposed to KNOW all accounts not the current login!
    
    private BankManagementSystem() {
    	// FIXME the databaseFile is essentially a list of accounts and those reference banks and owners - do we need those Sets then?
    	this.banks = new HashSet<Bank>();
    	this.accounts = new HashSet<Account>();
    	this.owners = new HashSet<Owner>();
    	
    	// create Banks, Owners and Accounts from data  
    	(new DataImportHelper()).fillBanksAccountsAndOwners(this.accounts, this);
    }
    
    public static BankManagementSystem getInstance() {
    	if (instance == null) {
    		instance = new BankManagementSystem();
    		return instance;
    	}
    	return instance;
    }
    
    /**
     * Looks through the accounts-Set and returns the first matching the 
     * provided bankCode and accountNumber.
     * 
     * @return matching account or null
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
		for (Account currentAccount : this.accounts) {
			if (currentAccount.getOwner().equals(owner) && currentAccount.getBank().equals(bank)) {
				accounts.add(currentAccount);
			}
		}
		
		// Sort the resulting list of accounts by accountNumber (asc)
		Collections.sort(accounts);
		
		return accounts; //TODO ist null schlimm als return wert?
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