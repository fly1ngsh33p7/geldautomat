package model;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import view.ErrorPopup;

/**
 * BankManagementSystem creates all the Bank, Account and Owner objects from the provided databaseFile.
 */
public class BankManagementSystem {
    private Set<Bank> banks;
    private Set<Account> accounts;
    private Set<Owner> owners;
    
    public BankManagementSystem(File databaseFile) {
    	this.banks = new HashSet<Bank>();
    	this.accounts = new HashSet<Account>();
    	this.owners = new HashSet<Owner>();
    	
    	List<List<String>> data = getDataByColumn(databaseFile);
        
    	// create Banks, Owners and Accounts from data
    	extractFromData(data);
    }
    
    
    /**
     * The structure of the data should be as described below,
     * otherwise the data cannot be interpreted correctly:
     * 
     * 
	 * Represents a table with the following headers: (index above, translation below)
	 * 
	 * <table border="1">
	 *   <tr>
	 *     <td>0</td>
	 *     <td>1</td>
	 *     <td>2</td>
	 *     <td>3</td>
	 *     <td>4</td>
	 *     <td>5</td>
	 *     <td>6</td>
	 *     <td>7</td>
	 *     <td>8</td>
	 *     <td>9</td>
	 *     <td>10</td>
	 *     <td>11</td>
	 *     <td>12</td>
	 *     <td>13</td>
	 *   </tr>
     *   <tr>
	 *     <td>Bank</td>
	 *     <td>BLZ</td>
	 *     <td>Kontonummer</td>
	 *     <td>PIN</td>
	 *     <td>Kontostand</td>
	 *     <td>Kontoart</td>
	 *     <td>Zins %</td>
	 *     <td>Ueberziehungsbetrag</td>
	 *     <td>Kundennummer</td>
	 *     <td>Name</td>
	 *     <td>Vorname</td>
	 *     <td>Kunde Straße</td>
	 *     <td>Kunde PLZ</td>
	 *     <td>Kunde Ort</td>
	 *   </tr>
	 *   <tr>
	 *     <td>Bank</td>
	 *     <td>Bank Code</td>
	 *     <td>Account Number</td>
	 *     <td>PIN</td>
	 *     <td>Account Balance</td>
	 *     <td>Account Type</td>
	 *     <td>Interest Rate (%)</td>
	 *     <td>Overdraft Amount</td>
	 *     <td>Customer Number</td>
	 *     <td>Last Name</td>
	 *     <td>First Name</td>
	 *     <td>Customer Street</td>
	 *     <td>Customer Postal Code</td>
	 *     <td>Customer City</td>
	 *   </tr>
	 * </table>
	 * 
	 * It is actually a database for the accounts, i. e. there are no duplicate 
	 * accounts, but there can be duplicate banks and owners.
	 * 
	 */
    private static List<List<String>> getDataByColumn(File file) {
        List<List<String>> columns = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        	// technically we don't need to count the header columns, it's 14 for the database file
        	// we use, but let's do that anyways:
            int columnCount = reader.readLine().split(";").length;

            // Initialize columns list with empty lists for each column
            for (int i = 0; i < columnCount; i++) {
                columns.add(new ArrayList<>());
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");

                // Add each value to the corresponding column list
                for (int i = 0; i < values.length; i++) {
                    columns.get(i).add(values[i].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return columns;
    }
    
    private void extractFromData(List<List<String>> data) {
    	for (int currentAccountIndex = 0; currentAccountIndex < data.get(0).size(); currentAccountIndex++) {
    		//---extract-the-current-Bank---
			String bankName = data.get(0).get(currentAccountIndex);
			String bankCode = data.get(1).get(currentAccountIndex);
		
			Bank currentBank = new Bank(bankCode, bankName, this);
			boolean insertedNewBank = this.banks.add(currentBank);
			//------------------------------
    		
    		//---extract-the-current-Owner---
    		int customerNumber = Integer.parseInt(data.get(8).get(currentAccountIndex));
    		String lastName = data.get(9).get(currentAccountIndex);
    		String firstName = data.get(10).get(currentAccountIndex);
    		String street = data.get(11).get(currentAccountIndex);
    		String postalCode = data.get(12).get(currentAccountIndex);
    		String location = data.get(13).get(currentAccountIndex);
    		
    		Owner currentOwner = new Owner(customerNumber, lastName, firstName, postalCode, location, street);
    		boolean insertedNewOwner = this.owners.add(currentOwner);
    		//------------------------------
    		
    		//---extract-the-current-account---
    		int accountNumber = Integer.parseInt(data.get(2).get(currentAccountIndex));
    		int pin = Integer.parseInt(data.get(3).get(currentAccountIndex));
    		double balance = sanitizeAndParseDouble(data.get(4).get(currentAccountIndex));
    		String accountType= data.get(5).get(currentAccountIndex);
    		String rawInterestString = data.get(6).get(currentAccountIndex);
    		String rawOverdraftAmountString = data.get(7).get(currentAccountIndex);
    		
    		switch (accountType) {
    			case "Sparkonto":
    				// FIXME: this adds not *the original* Bank if insertedNewBank is false, but one that's equal to it. This shouldn't cause problems but is not perfect.
    				this.accounts.add(new SavingAccount(accountNumber, pin, balance, currentBank, currentOwner, sanitizeAndParseDouble(rawInterestString))); //TODO sanitize necessary or suitable?
    				break;
    			case "Girokonto":
    				// FIXME: this adds not *the original* Bank/Owner if insertedNewBank/insertedNewOwner is false, but one that's equal to it. This shouldn't cause problems but is not perfect.
    				this.accounts.add(new CheckingAccount(accountNumber, pin, balance, currentBank, currentOwner, sanitizeAndParseDouble(rawOverdraftAmountString))); //TODO sanitize necessary or suitable?
    				break;
				default:
					// if the extracted accountType is not in this switch-case, something went wrong
					ErrorPopup.displayError("Fehler beim Einlesen der Datenbankdatei.", () -> System.exit(1));
    		}
    	}
    }
    
    /**
     * Takes a String input that is to be parsed into a Double and removes everything 
     * that is different from digits, commas and dots. Then replaces all commas with dots.
     * 
     * @param input
     * @return sanitized double
     */
    public static double sanitizeAndParseDouble(String input) {
    	// matches everything except dashes, digits, dots and commas
    	String removedAllExceptDigitsDotsAndCommas = input.replaceAll("[^-0-9.,]", "");
    	
    	// match only the periods that are not the decimal separator, that means it
    	// matches a dot and then looks ahead if there is another dot or a comma.
    	// It assumes that there are no dashes inside of the number.
    	String removedThousandSeparators = removedAllExceptDigitsDotsAndCommas.replaceAll("\\.(?=.*[.,])", "");
    	
    	// replace the German decimal separation comma with a dot
    	String replacedGermanDecimalSeparator = removedThousandSeparators.replace(",", ".");
        return Double.parseDouble(replacedGermanDecimalSeparator);
    }
}