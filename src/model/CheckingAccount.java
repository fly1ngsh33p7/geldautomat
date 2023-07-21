package model;

/*
 * Girokonto in German
 */
public class CheckingAccount extends Account {
	private double overdraftAmount;
	
	public CheckingAccount(int accountNumber, int pin, double balance, Bank bank, Owner owner, double overdraftAmount) {
		super(accountNumber, pin, balance, bank, owner);
		this.overdraftAmount = overdraftAmount;
	}
	
	public String getAccountType() {
		return "Girokonto";
	}

	public double getOverdraftAmount() {
		return overdraftAmount;
	}

	public void setOverdraftAmount(double overdraftAmount) {
		this.overdraftAmount = overdraftAmount;
	}
}
