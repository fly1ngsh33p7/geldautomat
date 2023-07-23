package model;

public class SavingAccount extends Account {
	// interest in percent
	private double interest;

	public SavingAccount(int accountNumber, int pin, double balance, Bank bank, Owner owner, double interest) {
		super(accountNumber, pin, balance, bank, owner);
		this.interest = interest;
	}
	
	public boolean canUserAfford(double amount) throws NegativeAmountException {
		if (amount < 0) {
			throw new NegativeAmountException();
		}
		return (this.getBalance() - amount) > 0;
	}
	
	public String getAccountType() {
		return "Sparkonto";
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}
	
}
