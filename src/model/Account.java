package model;

public abstract class Account {
	private int accountNumber;
	private int pin;
	private double balance; //TODO ist das hier wirklich als PROZENT interpretiert?? 
	private Bank bank;
	private Owner owner;
	
	public Account(int accountNumber, int pin, double balance, Bank bank, Owner owner) {
		super();
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.balance = balance;
		this.bank = bank;
		this.owner = owner;
	}
	
	public abstract String getAccountType();
	
	public boolean depositMoney(double amount) {
		// a negative amount cannot be deposited
		if (amount < 0) {
			return false;
		}
		
		this.setBalance(this.getBalance() + amount);
		
		//return true to show that this operation succeeded
		return true;
	}
	
	public boolean withdrawMoney(double amount) {
		// a negative amount cannot be withdrawn
		if (amount < 0) {
			return false;
		}
		
		double newAmount = this.getBalance() - amount;

		// if this a SavingsAccount, don't allow a negative balance
		if (this instanceof SavingAccount) {
			if ( newAmount < 0 ) {
				return false; 
			}
		}
		
		this.setBalance(newAmount);
		
		//return true to show that this operation succeeded
		return true;
	}
	
	public int getAccountNumber() {
		return accountNumber;
	}
	
	public int getPin() {
		return pin;
	}
	
	public double getBalance() {
		return balance;
	}
	
	private void setBalance(double balance) {
		this.balance = balance;
	}
	
	public Bank getBank() {
		return bank;
	}

	public Owner getOwner() {
		return owner;
	}
}
