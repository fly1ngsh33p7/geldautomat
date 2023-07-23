package model;

import java.util.Objects;

/**
 * The Owner class represents an owner of a bank account.
 * <p>
 * An Owner object encapsulates information about a customer, including the customer number,
 * first name, last name, postal code, location, and street address.
 * <p>
 * Owner objects are used to associate bank accounts with their respective owners, allowing
 * the identification and management of account ownership.
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * // Creating an Owner object
 * Owner owner = new Owner(12345, "Rainer", "Zufall", "12345", "Essen", "Bahnhofstraße 5");
 *
 * // Accessing owner information
 * int customerNumber = owner.getCustomerNumber();
 * String fullName = owner.getFirstName() + " " + owner.getLastName();
 * System.out.println("Customer Number: " + customerNumber);
 * System.out.println("Full Name: " + fullName);
 * }
 * </pre>
 */
public class Owner {
	private int customerNumber;
	private String lastName;
	private String firstName;
	private String postalCode;
	private String location;
	private String street;
	
	/**
     * Constructs an Owner object with the specified details.
     *
     * @param customerNumber The customer number of the owner.
     * @param lastName       The last name of the owner.
     * @param firstName      The first name of the owner.
     * @param postalCode     The postal code of the owner's address.
     * @param location       The location of the owner's address.
     * @param street         The street address of the owner.
     */
	public Owner(int customerNumber, String lastName, String firstName, String postalCode, String location,
			String street) {
		this.customerNumber = customerNumber;
		this.lastName = lastName;
		this.firstName = firstName;
		this.postalCode = postalCode;
		this.location = location;
		this.street = street;
	}
	
	// Getters and setters
	
	public int getCustomerNumber() {
		return customerNumber;
	}
	
	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getPostalCode() {
		return postalCode;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	// hashCode and equals
	
	/**
     * Generates a hash code for this Owner object. customerNumbers are unique
     *
     * @return The hash code.
     */
	@Override
	public int hashCode() {
		return Objects.hash(customerNumber);
	}

	/**
     * Compares this Owner object with another object for equality. customerNumbers are unique
     *
     * @param obj The object to compare with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		Owner other = (Owner) obj;
		return customerNumber == other.customerNumber;
	}
}
