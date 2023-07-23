package model;

import java.util.Objects;

/**
 * The Bank class represents a bank entity.
 * <p>
 * A Bank object encapsulates information about a bank, including its bank code and name.
 * It also includes a reference to the {@link BankManagementSystem} that manages this bank,
 * although it is optional and might not be needed depending on the use case.
 * <p>
 * The Bank class provides access to the bank's code and name through getter methods.
 * It also overrides the {@code hashCode()} and {@code equals()} methods for proper comparison
 * and hashing of Bank objects based on their bank codes.
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * // Creating a Bank object
 * Bank bank = new Bank("123", "MyBank", bankManagementSystem);
 *
 * // Accessing bank information
 * String bankCode = bank.getBankCode();
 * String bankName = bank.getName();
 * System.out.println("Bank Code: " + bankCode);
 * System.out.println("Bank Name: " + bankName);
 * }
 * </pre>
 */
public class Bank {
	private String bankCode;
	private String name;
	private BankManagementSystem bms;  // with how I structured my program, this is technically not needed.
	
	/**
     * Constructs a Bank object with the specified bank code and name.
     *
     * @param bankCode The bank code of the bank.
     * @param name     The name of the bank.
     * @param bms      The BankManagementSystem managing this bank (optional).
     */
	public Bank(String bankCode, String name, BankManagementSystem bms) {
		this.bankCode = bankCode;
		this.name = name;
		this.bms = bms;
	}

	// Getters and setters
	
	public String getBankCode() {
		return bankCode;
	}
	
	public String getName() {
		return name;
	}

	// hashCode and equals
	
	/**
     * Generates a hash code for this Bank object. bankCodes are unique
     *
     * @return The hash code.
     */
	@Override
	public int hashCode() {
		return Objects.hash(bankCode, name);
	}
	
	/**
     * Compares this Bank object with another object for equality. bankCodes are unique
     *
     * @param obj The object to compare with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Bank)) {
			return false;
		}
		Bank other = (Bank) obj;
		return Objects.equals(bankCode, other.bankCode);
	}
}
