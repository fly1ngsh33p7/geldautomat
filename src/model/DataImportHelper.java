package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JFileChooser;

import view.windows.ErrorPopup;
import view.windows.Popup;

/**
 * The DataImportHelper class facilitates the import of data from a database file
 * to populate the set of accounts in the application.
 * 
 * It provides methods for:
 * 1. Prompting the user to select a database file through a file chooser dialog
 *    using {@link #readDatabaseFileByUser()} method.
 * 2. Reading and extracting data from the selected database file and organizing it
 *    into columns using {@link #getDataByColumn(java.io.File)} method.
 * 3. Extracting accounts, linking banks and owners, from the data and adding them to the
 *    provided sets using {@link #extractFromData(Set, BankManagementSystem)} method.
 * 4. Sanitizing and parsing double values in the data using {@link #sanitizeAndParseDouble(String)} method.
 * 
 * The class assumes that the database file has a specific structure, as described in the
 * documentation of the {@link #getDataByColumn(java.io.File)} method. It also handles German
 * characters correctly by using the "windows-1252" charset during file reading.
 * 
 * This class serves as a helper to fill the application's data model with data from the
 * selected database file, facilitating data import and initialization of the application.
 * 
 * @see #readDatabaseFileByUser()
 * @see #getDataByColumn(java.io.File)
 * @see #extractFromData(Set, BankManagementSystem)
 * @see #sanitizeAndParseDouble(String)
 */
public class DataImportHelper {
	/**
	 * stores raw column data to use in some functions
	 */
	private List<List<String>> data;
	
	/**
	 * Prompts the user to select a database file through a file chooser dialog.
	 * 
	 * The selected file is returned as a {@link File} object. If the user cancels the file selection,
	 * the method will display an information dialog with a message and two options - "OK" and "Programm beenden".
	 * If the user selects "OK", the file chooser dialog will be displayed again to prompt for a file selection.
	 * If the user selects "Programm beenden", the program will exit.
	 * The method will continue to prompt the user until a valid file is selected or the program is exited.
	 * 
	 * @return A {@link File} object representing the selected database file (null will not be returned as 
	 * 			the program will exit then.
	 */
	private static File readDatabaseFileByUser() {
		File selectedFile;
		do {
			JFileChooser fileChooser = new JFileChooser();
			int resultStatus = fileChooser.showOpenDialog(null);
			selectedFile = fileChooser.getSelectedFile();

			if (resultStatus == JFileChooser.CANCEL_OPTION) {
				// a file is still considered selected when it is highlighted (after clicking it) and then closing the JFileChooser using "Cancel"
				// Therefore we reset selectedFile to null
				selectedFile = null;
			}
			
			if (selectedFile == null) {
				Popup.display("Info", "Bitte wählen Sie eine Datei aus.", "OK", "Programm beenden", () -> System.exit(0));
			}
		} while (selectedFile == null);
		return selectedFile;
    }
	
	 /**
     * The structure of the data should be as described below,
     * otherwise the data cannot be interpreted correctly:
     * 
	 * Represents a table with the following headers: (index above, translation below)
	 * 
	 * <table border="1">
	 *   <tr>
	 *     <td>0</td><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td><td>6</td>
	 *     <td>7</td><td>8</td><td>9</td><td>10</td><td>11</td><td>12</td><td>13</td>
	 *   </tr>
     *   <tr>
	 *     <td>Bank</td><td>BLZ</td><td>Kontonummer</td><td>PIN</td><td>Kontostand</td>
	 *     <td>Kontoart</td><td>Zins %</td><td>Ueberziehungsbetrag</td><td>Kundennummer</td>
	 *     <td>Name</td><td>Vorname</td><td>Kunde Straße</td><td>Kunde PLZ</td><td>Kunde Ort</td>
	 *   </tr>
	 *   <tr>
	 *     <td>Bank</td><td>Bank Code</td><td>Account Number</td><td>PIN</td><td>Account Balance</td>
	 *     <td>Account Type</td><td>Interest Rate (%)</td><td>Overdraft Amount</td><td>Customer Number</td>
	 *     <td>Last Name</td><td>First Name</td><td>Customer Street</td><td>Customer Postal Code</td><td>Customer City</td>
	 *   </tr>
	 * </table>
	 * 
	 * It is actually a database for the accounts, i. e. there are no duplicate 
	 * accounts, but there can be duplicate banks and owners.
	 * 
	 */
    public static List<List<String>> getDataByColumn(File file) {
        List<List<String>> columns = new ArrayList<>();

        // open the file with the correct charset to display German characters
        try (BufferedReader reader = Files.newBufferedReader(file.toPath(), Charset.forName("windows-1252"))) {
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
    
    public void extractFromData(Set<Account> accounts, BankManagementSystem bms) {
    	try {
	    	for (int currentAccountIndex = 0; currentAccountIndex < data.get(0).size(); currentAccountIndex++) {
	    		//---extract-the-current-Bank---
				String bankName = data.get(0).get(currentAccountIndex);
				String bankCode = data.get(1).get(currentAccountIndex);
			
				Bank currentBank = new Bank(bankCode, bankName, bms);
				//------------------------------
	    		
	    		//---extract-the-current-Owner---
	    		int customerNumber = Integer.parseInt(data.get(8).get(currentAccountIndex));
	    		String lastName = data.get(9).get(currentAccountIndex);
	    		String firstName = data.get(10).get(currentAccountIndex);
	    		String street = data.get(11).get(currentAccountIndex);
	    		String postalCode = removePossibleLeadingSingleQuote(data.get(12).get(currentAccountIndex));
	    		String location = data.get(13).get(currentAccountIndex);
	    		
	    		
	    		
	    		Owner currentOwner = new Owner(customerNumber, lastName, firstName, postalCode, location, street);
	    		//------------------------------
	    		
	    		//---extract-the-current-account---
	    		int accountNumber = Integer.parseInt(removePossibleLeadingSingleQuote(data.get(2).get(currentAccountIndex)));
	    		int pin = Integer.parseInt(removePossibleLeadingSingleQuote(data.get(3).get(currentAccountIndex)));
	    		double balance = sanitizeAndParseDouble(data.get(4).get(currentAccountIndex));
	    		String accountType= data.get(5).get(currentAccountIndex);
	    		String rawInterestString = data.get(6).get(currentAccountIndex);
	    		String rawOverdraftAmountString = data.get(7).get(currentAccountIndex);
	    		
	    		switch (accountType) {
	    			case "Sparkonto":
	    				accounts.add(new SavingAccount(accountNumber, pin, balance, currentBank, currentOwner, sanitizeAndParseDouble(rawInterestString)));
	    				break;
	    			case "Girokonto":
	    				accounts.add(new CheckingAccount(accountNumber, pin, balance, currentBank, currentOwner, sanitizeAndParseDouble(rawOverdraftAmountString)));
	    				break;
					default:
						// if the extracted accountType is not in this switch-case, something went wrong
						throw new Exception("Fehler beim Einlesen der Datenbankdatei.");
	    		}
	    	}
    	} catch (Exception e) {
    		ErrorPopup.displayError("Fehler beim Einlesen der Datenbankdatei.", () -> System.exit(1));
    	}
    }
    
    /**
     * Fills the given accounts set from the data read from a database file.
     *
     * This method is designed to take class fields representing sets of banks, accounts, and owners as parameters,
     * and populate them with data read from a database file. It performs the following steps:
     *
     * 1. Reads data from a database file using {@link #readDatabaseFileByUser()} method.
     * 2. Extracts the required data for accounts and owners using {@link #getDataByColumn(java.io.File)} method
     *    and sets it using {@link #setData(Map)} method.
     * 3. Extracts accounts and owners from the data and adds them to the provided sets of accounts and owners respectively
     *    using {@link #extractFromData(Set, BankManagementSystem)} method.
     *
     * @param accounts The set of accounts to be filled with data.
     * @param bms      The BankManagementSystem instance used to manage the accounts and owners.
     * @see #readDatabaseFileByUser()
     * @see #getDataByColumn(java.io.File)
     * @see #setData(Map)
     * @see #extractFromData(Set, BankManagementSystem)
     */
    public void fillBanksAccountsAndOwners(Set<Account> accounts, BankManagementSystem bms) {
    	setData(getDataByColumn(readDatabaseFileByUser()));
    	extractFromData(accounts, bms);
    }
    
    /**
     * Takes a String input that is to be parsed into a Double and removes everything 
     * that is different from a preceding dash, digits, commas and dots. Then replaces all commas with dots.
     * 
     * @param raw String input
     * @return sanitized double
     */
    public static double sanitizeAndParseDouble(String input) {
    	// matches and removes everything except dashes, digits, dots and commas
    	String removedAllExceptDigitsDotsAndCommas = input.replaceAll("[^-0-9.,]", "");
    	
    	// match only the periods that are not the decimal separator, that means it
    	// matches a dot and then looks ahead if there is another dot or a comma.
    	// It assumes that there are no dashes inside of the number.
    	String removedThousandSeparators = removedAllExceptDigitsDotsAndCommas.replaceAll("\\.(?=.*[.,])", "");
    	
    	// replace the German decimal separation comma with a dot
    	String replacedGermanDecimalSeparator = removedThousandSeparators.replace(",", ".");
        return Double.parseDouble(replacedGermanDecimalSeparator);
    }
    
    /**
     * Takes a String input and tries to remove a leading ' if possible.
     * 
     * @param String input
     * @return String without a leading '
     */
    public static String removePossibleLeadingSingleQuote(String text) {
    	if (text.charAt(0) == '\'') {
    		text.substring(1);
    	}
    	return text;
    }

	public void setData(List<List<String>> data) {
		this.data = data;
	}
}
