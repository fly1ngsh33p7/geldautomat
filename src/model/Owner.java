package model;

import java.util.Objects;

public class Owner {
	private int customerNumber;
	private String lastName;
	private String firstName;
	private String postalCode;
	private String location;
	private String street;
	
	public int getCustomerNumber() {
		return customerNumber;
	}
	
	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	public Owner(int customerNumber, String lastName, String firstName, String postalCode, String location,
			String street) {
		this.customerNumber = customerNumber;
		this.lastName = lastName;
		this.firstName = firstName;
		this.postalCode = postalCode;
		this.location = location;
		this.street = street;
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
	
	@Override
	public int hashCode() {
		return Objects.hash(customerNumber, firstName, lastName, location, postalCode, street);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Owner)) {
			return false;
		}
		Owner other = (Owner) obj;
		return customerNumber == other.customerNumber;
	}
}
