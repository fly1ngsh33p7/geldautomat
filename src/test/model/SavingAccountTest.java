package test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import model.Bank;
import model.BankManagementSystem;
import model.NegativeAmountException;
import model.NotEnoughMoneyException;
import model.Owner;
import model.SavingAccount;

public class SavingAccountTest {
	private Bank bank;
    private Owner owner;
    

    @Before
    public void setUp() {
        bank = new Bank("666", "Abzockbank", BankManagementSystem.getInstance());
        owner = new Owner(123, "Zufall", "Rainer", "12345", "Astraße 1", "Bstadt");
    }
    
    @Test
    public void testWithdrawMoneyFromSavingAccount() {
    	SavingAccount account = new SavingAccount(123456, 1234, 1000, bank, owner, 3);
    	
    	try {
		    account.withdrawMoney(500.0);
		    assertEquals(500.0, account.getBalance(), 0.001);
		    
		    // withdraw negative amount
		    account.withdrawMoney(-500.0);
		    // re-check to make sure no money was withdrawn
		    assertEquals(500.0, account.getBalance(), 0.001);
        
			account.withdrawMoney(2000.0);
		} catch (Exception e) {
			fail("Exception was thrown");
		}
        // re-check to make sure no money was withdrawn
        assertEquals(500.0, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCanUserAfford() {
        // Create an instance of SavingAccount
        SavingAccount savingAccount = new SavingAccount(1234, 5678, 3000.0, bank, owner, 2.5);

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
    public void testWithdrawMoney() {
        // Create an instance of SavingAccount
        SavingAccount savingAccount = new SavingAccount(1234, 5678, 3000.0, bank, owner, 2.5);
        try {
	        // Test cases for withdrawMoney
	        savingAccount.withdrawMoney(2000.0);
	        assertEquals(1000.0, savingAccount.getBalance(), 0.001);
	
	        savingAccount.withdrawMoney(1000.1);
	        assertEquals(1000.0, savingAccount.getBalance(), 0.001);
	    } catch (Exception e) {
			fail("Exception was thrown");
		}
    }

    @Test
    public void testDepositMoney() {
        // Create an instance of SavingAccount
        SavingAccount savingAccount = new SavingAccount(1234, 5678, 3000.0, bank, owner, 2.5);

        // Test cases for depositMoney
        try {
	        savingAccount.depositMoney(500.0);
	        assertEquals(3500.0, savingAccount.getBalance(), 0.001);
	
	        savingAccount.depositMoney(-100.0);
	        assertEquals(3500.0, savingAccount.getBalance(), 0.001);
	    } catch (Exception e) {
			fail("Exception was thrown");
		}
    }
}
