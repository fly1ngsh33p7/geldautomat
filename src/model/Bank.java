package model;

import java.util.Objects;

public class Bank {
	public Bank(String bankCode, String name, BankManagementSystem bms) {
		this.bankCode = bankCode;
		this.name = name;
		this.bms = bms; // TODO idk, if that's needed - but the Angabe says so.
	}

	private String bankCode;
	private String name;
	private BankManagementSystem bms;
	
	public String getBankCode() {
		return bankCode;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bankCode, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Bank)) {
			return false;
		}
		Bank other = (Bank) obj;
		return Objects.equals(bankCode, other.bankCode) && Objects.equals(name, other.name);
	}
}
