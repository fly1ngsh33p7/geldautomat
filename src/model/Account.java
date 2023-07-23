package model;

public abstract class Account implements Comparable<Account> {
	private int accountNumber;
	private int pin;
	private double balance;
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
	public abstract boolean withdrawMoney(double amount);
	
	public boolean depositMoney(double amount) {
		// a negative amount cannot be deposited
		if (amount < 0) {
			return false;
		}
		
		this.setBalance(this.getBalance() + amount);
		
		//return true to show that this operation succeeded
		return true;
	}
	
	@Override
    public int compareTo(Account otherAccount) {
        // Compare based on accountNumber
        return Integer.compare(this.accountNumber, otherAccount.accountNumber);
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
	
	protected void setBalance(double balance) {
		this.balance = balance;
	}
	
	public Bank getBank() {
		return bank;
	}

	public Owner getOwner() {
		return owner;
	}
}
