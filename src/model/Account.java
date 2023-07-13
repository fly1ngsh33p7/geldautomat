package model;

public class Account {
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
	
	public int getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public int getPin() {
		return pin;
	}
	
	public void setPin(int pin) {
		this.pin = pin;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
}
