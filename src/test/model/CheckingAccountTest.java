package test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
    	
    	assertTrue(account.withdrawMoney(500.0));
        assertEquals(500.0, account.getBalance(), 0.001);
        
        // withdraw negative amount
        assertFalse(account.withdrawMoney(-500.0));
        assertEquals(500.0, account.getBalance(), 0.001);// re-check to make sure no money was withdrawn
        
        // withdraw amount within the overdraft amount
        assertTrue(account.withdrawMoney(1000.0));
        assertEquals(-500.0, account.getBalance(), 0.001);
        
        // withdraw higher amount than overdraft amount allows
        assertFalse(account.withdrawMoney(1000.0));
        assertEquals(-500.0, account.getBalance(), 0.001);// re-check to make sure no money was withdrawn
    }
}
