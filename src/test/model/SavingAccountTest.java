package test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.Bank;
import model.BankManagementSystem;
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
    	
        assertTrue(account.withdrawMoney(500.0));
        assertEquals(500.0, account.getBalance(), 0.001);
        
        // withdraw negative amount
        assertFalse(account.withdrawMoney(-500.0));
        // re-check to make sure no money was withdrawn
        assertEquals(500.0, account.getBalance(), 0.001);
        
        assertFalse(account.withdrawMoney(2000.0));
        // re-check to make sure no money was withdrawn
        assertEquals(500.0, account.getBalance(), 0.001);
    }
}
