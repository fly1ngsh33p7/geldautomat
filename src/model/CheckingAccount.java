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
	
	public boolean canUserAffordOnlyWithOverdraft(double amount) throws NegativeAmountException {
		double newAmount = this.getBalance() - amount;
		
		return (canUserAfford(amount) && newAmount < 0);
	}
	
	public boolean canUserAfford(double amount) throws NegativeAmountException {
		if (amount < 0) {
			throw new NegativeAmountException();
		}
		
		double newAmount = this.getBalance() - amount;
		
		// return true if the resulting amount is more (= less debt) than 
		// the overdraft amount (negative because overdraftAmount = "allowed debt")
		return newAmount > -overdraftAmount;
	}
	
	public double getAvailableMoney() {
		return this.getBalance() + this.getOverdraftAmount();
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
