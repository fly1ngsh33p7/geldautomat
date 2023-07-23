package model;

import java.util.Objects;

/**
 * The abstract class representing a generic bank account.
 * <p>
 * An Account represents a bank account with an account number, PIN, balance, bank details, and owner information.
 * It provides common methods for depositing and withdrawing money and defining specific account types and affordability checks.
 * <p>
 * Subclasses must implement the {@link #getAccountType()} method to specify the type of the account.
 * Additionally, subclasses should override the {@link #canUserAfford(double)} method to define the account-specific affordability rules.
 * <p>
 * This class also implements the {@link Comparable} interface to allow for comparison based on account numbers.
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * Account account = new CheckingAccount(1234, 5678, 1000.0, new Bank(), new Owner(), 500.0);
 * double withdrawalAmount = 200.0;
 * try {
 *     account.withdrawMoney(withdrawalAmount);
 *     System.out.println("Withdrawal successful. New balance: " + account.getBalance());
 * } catch (NotEnoughMoneyException e) {
 *     System.out.println("Withdrawal failed. Not enough money in the account.");
 * }
 * }
 * </pre>
 */
public abstract class Account implements Comparable<Account> {
	private int accountNumber;
	private int pin;
	private double balance;
	private Bank bank;
	private Owner owner;
	
	/**
     * Constructs a new Account object with the given details.
     *
     * @param accountNumber The account number of the account.
     * @param pin           The PIN associated with the account.
     * @param balance       The initial balance of the account.
     * @param bank          The bank to which the account belongs.
     * @param owner         The owner of the account.
     */
	public Account(int accountNumber, int pin, double balance, Bank bank, Owner owner) {
		super();
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.balance = balance;
		this.bank = bank;
		this.owner = owner;
	}
	
	/**
     * Gets the type of the account.
     *
     * @return A string representing the type of the account.
     */
	public abstract String getAccountType();
	
	/**
     * Checks if the user can afford to withdraw the given amount from the account.
     *
     * @param amount The amount to be withdrawn.
     * @return {@code true} if the user can afford to withdraw the amount, {@code false} otherwise.
     * @throws NegativeAmountException If the amount is negative.
     */
	public abstract boolean canUserAfford(double amount) throws NegativeAmountException;
	
	/**
     * Withdraws money from the account.
     *
     * @param amount The amount to be withdrawn.
     * @throws NegativeAmountException  If the amount is negative.
     * @throws NotEnoughMoneyException If the account balance is insufficient to cover the withdrawal amount.
     */
	public void withdrawMoney(double amount) throws NegativeAmountException, NotEnoughMoneyException {
		// check if this account can afford it
		if (!canUserAfford(amount)) {
			throw new NotEnoughMoneyException();
		}
		
		this.setBalance(this.getBalance() - amount);
	}
	
	/**
     * Deposits money into the account.
     *
     * @param amount The amount to be deposited.
     * @throws NegativeAmountException If the amount is negative.
     */
	public void depositMoney(double amount) throws NegativeAmountException {
		// a negative amount cannot be deposited
		if (amount < 0) {
			throw new NegativeAmountException();
		}
		
		this.setBalance(this.getBalance() + amount);
	}
	
	
	/**
     * Compares this account to another account based on their account numbers.
     *
     * @param otherAccount The account to compare to.
     * @return A negative integer, zero, or a positive integer if this account's number is less than, equal to,
     *         or greater than the other account's number, respectively.
     */
	@Override
    public int compareTo(Account otherAccount) {
        // Compare based on accountNumber
        return Integer.compare(this.accountNumber, otherAccount.accountNumber);
    }
	
	// Getters and setters
	
	public int getAccountNumber() {
		return accountNumber;
	}
	
	public int getPin() {
		return pin;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public Bank getBank() {
		return bank;
	}

	public Owner getOwner() {
		return owner;
	}

	// hashCode and equals
	
	/**
     * Generates a hash code for this Account object based on its account number and associated bank.
     *
     * @return The hash code.
     */
	@Override
	public int hashCode() {
		return Objects.hash(accountNumber, bank);
	}

	/**
     * Compares this Account object with another object for equality.
     * <p>
     * Two accounts are considered equal if they have the same account number and associated bank.
     *
     * @param obj The object to compare with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return accountNumber == other.accountNumber && Objects.equals(bank, other.bank);
	}
}
