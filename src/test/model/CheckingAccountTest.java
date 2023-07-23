package test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import model.Bank;
import model.BankManagementSystem;
import model.CheckingAccount;
import model.Owner;

public class CheckingAccountTest {
	private Bank bank;
    private Owner owner;
    

    @Before
    public void setUp() {
        bank = new Bank("666", "Abzockbank", BankManagementSystem.getInstance());
        owner = new Owner(123, "Zufall", "Rainer", "12345", "Astraße 1", "Bstadt");
    }
    
    @Test
    public void testWithdrawMoney() {
    	CheckingAccount account = new CheckingAccount(123456, 1234, 1000.0, bank, owner, 1000);
    	
    	try {
			account.withdrawMoney(500.0);
		
        assertEquals(500.0, account.getBalance(), 0.001);
        
        // withdraw negative amount
        account.withdrawMoney(-500.0);
        assertEquals(500.0, account.getBalance(), 0.001);// re-check to make sure no money was withdrawn
        
        // withdraw amount within the overdraft amount
        account.withdrawMoney(1000.0);
        assertEquals(-500.0, account.getBalance(), 0.001);
        
        // withdraw higher amount than overdraft amount allows
        account.withdrawMoney(1000.0);
        assertEquals(-500.0, account.getBalance(), 0.001);// re-check to make sure no money was withdrawn
        
    	} catch (Exception e) {
    		fail("Exception was thrown");
		}
    }
    
    @Test
    public void testCanUserAfford() {
        // Create an instance of CheckingAccount
        CheckingAccount checkingAccount = new CheckingAccount(1234, 5678, 2000.0, bank, owner, 500.0);

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
    public void testCanUserAffordOnlyWithOverdraft() {
        // Create an instance of CheckingAccount
        CheckingAccount checkingAccount = new CheckingAccount(1234, 5678, 2000.0, bank, owner, 500.0);

        // Test cases for canUserAffordOnlyWithOverdraft
        try {
	        assertFalse(checkingAccount.canUserAffordOnlyWithOverdraft(100.0));
	        assertTrue(checkingAccount.canUserAffordOnlyWithOverdraft(3000.0));
	        assertTrue(checkingAccount.canUserAffordOnlyWithOverdraft(2000.0));
	        assertFalse(checkingAccount.canUserAffordOnlyWithOverdraft(2000.1));
        } catch (Exception e) {
        	fail("Exception was thrown");
        }
    }

    @Test
    public void testWithdrawMoney2() {
        // Create an instance of CheckingAccount
        CheckingAccount checkingAccount = new CheckingAccount(1234, 5678, 2000.0, bank, owner, 500.0);

        // Test cases for withdrawMoney
        try {
        	checkingAccount.withdrawMoney(1500.0);
        	assertEquals(500.0, checkingAccount.getBalance(), 0.001);
        	
        	checkingAccount.withdrawMoney(1000.1);
            assertEquals(500.0, checkingAccount.getBalance(), 0.001);
        } catch (Exception e) {
        	fail("Exception was thrown");
        }
    }

    @Test
    public void testDepositMoney() {
        // Create an instance of CheckingAccount
        CheckingAccount checkingAccount = new CheckingAccount(1234, 5678, 2000.0, bank, owner, 500.0);

        // Test cases for depositMoney
        try {
        	checkingAccount.depositMoney(500.0);
        	assertEquals(2500.0, checkingAccount.getBalance(), 0.001);

            checkingAccount.depositMoney(-100.0);
            assertEquals(2500.0, checkingAccount.getBalance(), 0.001);
        } catch (Exception e) {
        	fail("Exception was thrown");
        }
        
    }
}
