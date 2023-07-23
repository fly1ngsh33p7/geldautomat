package model;


/**
 * The CheckingAccount class represents a specific type of bank account known as a "Girokonto" (checking account).
 * <p>
 * A CheckingAccount is a type of account that extends the abstract {@link Account} class.
 * It allows users to make transactions, including withdrawing money up to a certain overdraft limit.
 * <p>
 * The class provides methods to check if a user can afford a certain amount with and without using the overdraft facility.
 * It also includes methods to get and set the overdraft amount and to calculate the available money in the account.
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * // Creating a CheckingAccount object
 * CheckingAccount checkingAccount = new CheckingAccount(1234, 5678, 3000.0, new Bank(), new Owner(), 1000.0);
 *
 * // Checking if the user can afford a specific amount using the overdraft
 * double withdrawalAmount = 4000.0;
 * boolean canAffordWithOverdraft = checkingAccount.canUserAffordOnlyWithOverdraft(withdrawalAmount);
 * System.out.println("Can afford with overdraft: " + canAffordWithOverdraft);
 *
 * // Getting the available money in the account (including overdraft)
 * double availableMoney = checkingAccount.getAvailableMoney();
 * System.out.println("Available money: " + availableMoney);
 * }
 * </pre>
 */
public class CheckingAccount extends Account {
	private double overdraftAmount;
	
	/**
     * Constructs a CheckingAccount with the specified details.
     *
     * @param accountNumber   The account number of the checking account.
     * @param pin             The PIN associated with the account.
     * @param balance         The initial balance of the account.
     * @param bank            The bank to which the account belongs.
     * @param owner           The owner of the account.
     * @param overdraftAmount The overdraft amount for the checking account.
     */
	public CheckingAccount(int accountNumber, int pin, double balance, Bank bank, Owner owner, double overdraftAmount) {
		super(accountNumber, pin, balance, bank, owner);
		this.overdraftAmount = overdraftAmount;
	}
	
	/**
     * Checks if the user can afford to withdraw the given amount from the checking account using the overdraft.
     *
     * @param amount The amount to be withdrawn.
     * @return {@code true} if the user can afford to withdraw the amount with the overdraft, {@code false} otherwise.
     * @throws NegativeAmountException If the amount is negative.
     */
	public boolean canUserAffordOnlyWithOverdraft(double amount) throws NegativeAmountException {
		double newAmount = this.getBalance() - amount;
		
		return (canUserAfford(amount) && newAmount < 0);
	}
	
	/**
     * Checks if the user can afford to withdraw the given amount from the checking account.
     *
     * @param amount The amount to be withdrawn.
     * @return {@code true} if the user can afford to withdraw the amount, {@code false} otherwise.
     * @throws NegativeAmountException If the amount is negative.
     */
	public boolean canUserAfford(double amount) throws NegativeAmountException {
		if (amount < 0) {
			throw new NegativeAmountException();
		}
		
		double newAmount = this.getBalance() - amount;
		
		// return true if the resulting amount is more (= less debt) than 
		// the overdraft amount (negative because overdraftAmount = "allowed debt")
		return newAmount > -overdraftAmount;
	}
	
	// Getters and setters 
	
	/**
     * Calculates the available money in the checking account, considering the overdraft.
     *
     * @return The available money (balance + overdraft) in the checking account.
     */
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
