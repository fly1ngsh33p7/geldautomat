package model;

import model.exception.NegativeAmountException;

/**
 * The SavingAccount class represents a type of bank account known as a "Sparkonto" (savings account).
 * <p>
 * A SavingAccount is a specific type of account that extends the abstract {@link Account} class.
 * It allows users to store their money and earn interest over time (in theory, that is. It's not implemented)
 * <p>
 * A SavingAccount has properties such as an interest rate and supports common banking operations,
 * such as checking if a user can afford a certain withdrawal amount.
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * // Creating a SavingAccount object
 * SavingAccount savingAccount = new SavingAccount(1234, 5678, 3000.0, new Bank(), new Owner(), 2.5);
 *
 * // Accessing account information
 * String accountType = savingAccount.getAccountType();
 * double interestRate = savingAccount.getInterest();
 * System.out.println("Account Type: " + accountType);
 * System.out.println("Interest Rate: " + interestRate + "%");
 * }
 * </pre>
 */
public class SavingAccount extends Account {
	// interest in percent
	private double interest;

	 /**
     * Constructs a SavingAccount with the specified details.
     *
     * @param accountNumber The account number of the saving account.
     * @param pin           The PIN associated with the account.
     * @param balance       The initial balance of the account.
     * @param bank          The bank to which the account belongs.
     * @param owner         The owner of the account.
     * @param interest      The interest rate in percentage for the saving account.
     */
	public SavingAccount(int accountNumber, int pin, double balance, Bank bank, Owner owner, double interest) {
		super(accountNumber, pin, balance, bank, owner);
		this.interest = interest;
	}
	
	/**
     * Checks if the user can afford to withdraw the given amount from the saving account.
     *
     * @param amount The amount to be withdrawn.
     * @return {@code true} if the user can afford to withdraw the amount, {@code false} otherwise.
     * @throws NegativeAmountException If the amount is negative.
     */
	public boolean canUserAfford(double amount) throws NegativeAmountException {
		if (amount < 0) {
			throw new NegativeAmountException();
		}
		return (this.getBalance() - amount) > 0;
	}
	
	// Getters and setters
	
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
