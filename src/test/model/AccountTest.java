package test.model;

import org.junit.Test;
import static org.junit.Assert.*;

import model.*;

public class AccountTest {

    @Test
    public void testCanUserAffordWithCheckingAccount() {
        // Create an instance of CheckingAccount
        CheckingAccount checkingAccount = new CheckingAccount(5678, 1234, 2000.0, new Bank("666", "Abzockbank", BankManagementSystem.getInstance()), new Owner(123, "Zufall", "Rainer", "12345", "Astraße 1", "Bstadt"), 500.0);

        // Test cases for canUserAfford
        try {
	        assertTrue(checkingAccount.canUserAfford(500.0));
	        assertFalse(checkingAccount.canUserAfford(2500.0));
	        assertTrue(checkingAccount.canUserAfford(2000.0));
	        assertFalse(checkingAccount.canUserAfford(2000.1));
        } catch (Exception e) {
        	fail("Exception was thrown");
        }
    }

    @Test
    public void testWithdrawMoneyWithCheckingAccount() {
        // Create an instance of CheckingAccount
        CheckingAccount checkingAccount = new CheckingAccount(5678, 1234, 2000.0, new Bank("666", "Abzockbank", BankManagementSystem.getInstance()), new Owner(123, "Zufall", "Rainer", "12345", "Astraße 1", "Bstadt"), 500.0);

        // Test cases for withdrawMoney
        try {
        	checkingAccount.withdrawMoney(1500.0);
        } catch (Exception e) {
        	fail("Exception was thrown");
        }
        assertEquals(500.0, checkingAccount.getBalance(), 0.001);

        assertThrows(NotEnoughMoneyException.class, () -> checkingAccount.withdrawMoney(1000.1));
        assertEquals(500.0, checkingAccount.getBalance(), 0.001);
    }

    @Test
    public void testDepositMoneyWithCheckingAccount() {
        // Create an instance of CheckingAccount
        CheckingAccount checkingAccount = new CheckingAccount(5678, 1234, 2000.0, new Bank("666", "Abzockbank", BankManagementSystem.getInstance()), new Owner(123, "Zufall", "Rainer", "12345", "Astraße 1", "Bstadt"), 500.0);

        // Test cases for depositMoney
        try {
        	checkingAccount.depositMoney(500.0);
        } catch (Exception e) {
        	fail("Exception was thrown");
        }
        assertEquals(2500.0, checkingAccount.getBalance(), 0.001);

        assertThrows(NegativeAmountException.class, () -> checkingAccount.depositMoney(-100.0));
        assertEquals(2500.0, checkingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCanUserAffordWithSavingAccount() {
        // Create an instance of SavingAccount
        SavingAccount savingAccount = new SavingAccount(1234, 5678, 3000.0, new Bank("666", "Abzockbank", BankManagementSystem.getInstance()), new Owner(123, "Zufall", "Rainer", "12345", "Astraße 1", "Bstadt"), 2.5);

        // Test cases for canUserAfford
        try {
	        assertTrue(savingAccount.canUserAfford(500.0));
	        assertFalse(savingAccount.canUserAfford(3500.0));
	        assertTrue(savingAccount.canUserAfford(3000.0));
	        assertFalse(savingAccount.canUserAfford(3000.1));
        } catch (Exception e) {
        	fail("Exception was thrown");
        }
    }

    @Test
    public void testWithdrawMoneyWithSavingAccount() {
        // Create an instance of SavingAccount
        SavingAccount savingAccount = new SavingAccount(1234, 5678, 3000.0, new Bank("666", "Abzockbank", BankManagementSystem.getInstance()), new Owner(123, "Zufall", "Rainer", "12345", "Astraße 1", "Bstadt"), 2.5);

        // Test cases for withdrawMoney
        try {        
        	savingAccount.withdrawMoney(2000.0);
        } catch (Exception e) {
        	fail("Exception was thrown");
        }
        assertEquals(1000.0, savingAccount.getBalance(), 0.001);

        assertThrows(NotEnoughMoneyException.class, () -> savingAccount.withdrawMoney(1000.1));
        assertEquals(1000.0, savingAccount.getBalance(), 0.001);
    }

    @Test
    public void testDepositMoneyWithSavingAccount() {
        // Create an instance of SavingAccount
        SavingAccount savingAccount = new SavingAccount(1234, 5678, 3000.0, new Bank("666", "Abzockbank", BankManagementSystem.getInstance()), new Owner(123, "Zufall", "Rainer", "12345", "Astraße 1", "Bstadt"), 2.5);

        // Test cases for depositMoney
        try {
        	savingAccount.depositMoney(500.0);
        } catch (Exception e) {
        	fail("Exception was thrown");
        }
        assertEquals(3500.0, savingAccount.getBalance(), 0.001);

        assertThrows(NegativeAmountException.class, () -> savingAccount.depositMoney(-100.0));
        assertEquals(3500.0, savingAccount.getBalance(), 0.001);
    }
}
