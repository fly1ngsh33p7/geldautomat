package model;

/*
 * Girokonto in German
 */
public class CheckingAccount extends Account {
	private double overdraftAmount;
	
	public CheckingAccount(double overdraftAmount, int accountNumber, int pin, Bank bank, Owner owner) {
		super(accountNumber, pin, overdraftAmount, bank, owner);
		this.overdraftAmount = overdraftAmount;
	}

	public double getOverdraftAmount() {
		return overdraftAmount;
	}

	public void setOverdraftAmount(double overdraftAmount) {
		this.overdraftAmount = overdraftAmount;
	}
}
