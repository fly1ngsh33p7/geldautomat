package model;

public class SavingAccount extends Account {
	// interest in percent
	private double interest;

	public SavingAccount(int accountNumber, int pin, double balance, Bank bank, Owner owner, double interest) {
		super(accountNumber, pin, balance, bank, owner);
		this.interest = interest;
	}
	
	@Override
	public boolean withdrawMoney(double amount) {
		// a negative amount cannot be withdrawn
		if (amount < 0) {
			return false;
		}
		
		double newAmount = this.getBalance() - amount;

		// this a SavingsAccount: don't allow a negative balance
		if ( newAmount < 0 ) {
			return false; 
		}
		
		this.setBalance(newAmount);
		
		//return true to show that this operation succeeded
		return true;
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
