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
	
	public boolean withdrawMoney(double amount) {
		// a negative amount cannot be withdrawn
		if (amount < 0) {
			return false;
		}
		
		double newAmount = this.getBalance() - amount;
		
		// this is a CheckingAccount: only allow a negative balance within the overdraft amount
		if (newAmount < -overdraftAmount) {
			return false;
		}
		
		this.setBalance(newAmount);
		
		//return true to show that this operation succeeded
		return true;
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
