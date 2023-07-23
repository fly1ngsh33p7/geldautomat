package test.model;

import org.junit.Before;
import org.junit.Test;

import model.*;

import static org.junit.Assert.*;

public class AccountTest {
    private Bank bank;
    private Owner owner;
    

    @Before
    public void setUp() {
        bank = new Bank("666", "Abzockbank", BankManagementSystem.getInstance());
        owner = new Owner(123, "Zufall", "Rainer", "12345", "Astraße 1", "Bstadt");
    }

    @Test
    public void testDepositMoney() {
    	CheckingAccount account = new CheckingAccount(123456, 1234, 1000.0, bank, owner, 0);
    	
        assertTrue(account.depositMoney(500.0));
        assertEquals(1500.0, account.getBalance(), 0.001); // Use delta for double comparison
        
        // deposit negative amount
        assertFalse(account.depositMoney(-500));
        // re-check to make sure no money was deposited
        assertEquals(1500.0, account.getBalance(), 0.001);
    }

    
    
   
}
