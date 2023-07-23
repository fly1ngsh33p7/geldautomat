package view.screens;

import java.awt.Dimension;
import java.util.List;

import javax.swing.*;

import model.Account;
import model.CheckingAccount;
import model.Owner;
import model.SavingAccount;
import view.ItemClickAwareComboBox;

public class AccountScreen extends JPanel {
	private JButton logoutButton;
	private JButton openDepositWindowButton;
	private JButton openWithdrawWindowButton;
	private JButton transferButton;
	private JLabel accountNumberLabel;
	private JLabel firstNameLastNameLabel;
	private JLabel addressLabel;
	private JLabel bankLabel;
	private JLabel bankCodeLabel;
	private JLabel accountTypeLabel;
	private JLabel overdraftAmountOrInterestLabel;
	private JLabel balanceLabel;
	private ItemClickAwareComboBox<String> accountsAtThisBankComboBox;
	private SpringLayout springLayout;

	private JLabel actualAccountNumberLabel;
	private JLabel actualFirstNameLastNameLabel;
	private JLabel actualBankLabel;
	private JLabel actualAddressLabel;
	private JLabel actualAccountTypeLabel;
	private JLabel actualOverdraftAmountOrInterestLabel;
	private JLabel actualBalanceLabel;
	private JLabel actualBankCodeLabel;
	
	public AccountScreen() {
		setPreferredSize(new Dimension(670, 357));
		springLayout = new SpringLayout();
		setLayout(springLayout);

		initLogoutButton();
		initDepositWindowButton();
		initWithdrawWindowButton();
		initTransferButton();
		
		initLabels();
		initActualLabels();

		initComboBox();
	}

	public void setActualAccountLabelsAndCombobox(Account account, List<Account> accountListToDisplay, int selectedIndex) {
		//TODO this method is a mess: when do we call which method and why? -> more methods?
		if (account != null) {
			setValuesOfActualAccountLabels(account);
		} else {
			resetActualAccountLabels();
		}
		
		if (accountListToDisplay != null) {
			// deactivate the ItemListener
			this.accountsAtThisBankComboBox.setInInitMode(true);
			
			setAccountsAtThisBankInCombobox(account, accountListToDisplay);
			this.accountsAtThisBankComboBox.setSelectedIndex(selectedIndex);
			
			//activate the ItemListener
			this.accountsAtThisBankComboBox.setInInitMode(false);
		}
	}
	
	public void setValuesOfActualAccountLabels(Account account) {
		Owner owner = account.getOwner();
		
		// set the values in the actualLabels
		this.actualAccountNumberLabel.setText("" + account.getAccountNumber());
		this.actualFirstNameLastNameLabel.setText(owner.getFirstName() + " " + owner.getLastName());
		this.actualAddressLabel.setText(owner.getStreet() + ", " + owner.getPostalCode() + " " + owner.getLocation());
		this.actualBankLabel.setText(account.getBank().getName());
		this.actualBankCodeLabel.setText(account.getBank().getBankCode());
		this.actualAccountTypeLabel.setText(account.getAccountType());
		this.actualBalanceLabel.setText(String.format("%.2f €", account.getBalance()));

		if (account instanceof SavingAccount) {
			this.overdraftAmountOrInterestLabel.setText("Zins:");
			
			double interestRate = ((SavingAccount) account).getInterest();
			this.actualOverdraftAmountOrInterestLabel.setText(String.format("%.3f", interestRate) + " %");
		} else if (account instanceof CheckingAccount) {
			this.overdraftAmountOrInterestLabel.setText("Überziehungsbetrag:");
			
			double overdraftAmount = ((CheckingAccount) account).getOverdraftAmount();
			this.actualOverdraftAmountOrInterestLabel.setText(String.format("%.2f €", overdraftAmount));
		}
	}

	private void resetActualAccountLabels() {
		this.actualAccountNumberLabel.setText("");
		this.actualFirstNameLastNameLabel.setText("");
		this.actualAddressLabel.setText("");
		this.actualBankLabel.setText("");
		this.actualBankCodeLabel.setText("");
		this.actualAccountTypeLabel.setText("");
		this.actualBalanceLabel.setText("");
		this.actualOverdraftAmountOrInterestLabel.setText("");
	}
	
	private void setAccountsAtThisBankInCombobox(Account currentAccount, List<Account> accountList) {
		accountsAtThisBankComboBox.removeAllItems(); // Clear the combo box before adding items

	    // Add each account to the combo box
	    for (Account account : accountList) {
	    	String label = account.getAccountNumber() + " (" + account.getAccountType() + ")";
	        accountsAtThisBankComboBox.addItem(label);
	        //TODO technically it should be enough to send the index, since another call to getByXYZ() (or the List in BMS/Control) will result in the same result
	    }

	    // set currentAccount as SelectedItem
	    if (currentAccount != null) {
	    	accountsAtThisBankComboBox.setSelectedItem(currentAccount);
	    } else {
	    	accountsAtThisBankComboBox.setSelectedIndex(-1);
	    }
        // TODO test/try this
	}
	
	private void initLabels() {
		initAccountNumber();
		initFirstNameLastName();
		initAddress();
		initBank();
		initBankCode();
		initAccountType();
		initOverdraftAmount();
		initBalance();
	}
	
	private void initActualLabels() {
		initActualAccountNumberLabel();
		initActualFirstNameLastNameLabel();
		initActualBankLabel();
		initActualAddressLabel();
		initActualAccountTypeLabel();
		initActualOverdraftAmountLabel();
		initActualBalanceLabel();
		initActualBankCodeLabel();
	}
	
	private void initBalance() {
		this.balanceLabel = new JLabel("Kontostand:");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.balanceLabel, 201, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.balanceLabel, 12, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.balanceLabel, 164, SpringLayout.WEST, this);
		add(this.balanceLabel);
		
	}

	private void initOverdraftAmount() {
		this.overdraftAmountOrInterestLabel = new JLabel();
		this.springLayout.putConstraint(SpringLayout.NORTH, this.overdraftAmountOrInterestLabel, 174, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.overdraftAmountOrInterestLabel, 12, SpringLayout.WEST, this);
		add(this.overdraftAmountOrInterestLabel);
	}

	private void initAccountType() {
		this.accountTypeLabel = new JLabel("Konto:");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.accountTypeLabel, 147, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.accountTypeLabel, 12, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.accountTypeLabel, 164, SpringLayout.WEST, this);
		this.accountTypeLabel.setToolTipText("Zeigt, welcher Kontotyp vorliegt.");
		add(this.accountTypeLabel);
	}

	private void initBankCode() {
		this.bankCodeLabel = new JLabel("BLZ:");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.bankCodeLabel, 120, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.bankCodeLabel, 12, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.bankCodeLabel, 164, SpringLayout.WEST, this);
		add(this.bankCodeLabel);
	}

	private void initBank() {
		this.bankLabel = new JLabel("Bank:");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.bankLabel, 93, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.bankLabel, 12, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.bankLabel, 164, SpringLayout.WEST, this);
		add(this.bankLabel);
	}

	private void initAddress() {
		this.addressLabel = new JLabel("Adresse:");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.addressLabel, 66, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.addressLabel, 12, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.addressLabel, 164, SpringLayout.WEST, this);
		add(this.addressLabel);
	}

	private void initFirstNameLastName() {
		this.firstNameLastNameLabel = new JLabel("Vorname / Name:");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.firstNameLastNameLabel, 39, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.firstNameLastNameLabel, 12, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.firstNameLastNameLabel, 164, SpringLayout.WEST, this);
		add(this.firstNameLastNameLabel);
	}

	private void initComboBox() {
		this.accountsAtThisBankComboBox = new ItemClickAwareComboBox<>();
		this.springLayout.putConstraint(SpringLayout.NORTH, this.accountsAtThisBankComboBox, 12, SpringLayout.NORTH,
				this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.accountsAtThisBankComboBox, 458, SpringLayout.WEST,
				this);
		this.springLayout.putConstraint(SpringLayout.SOUTH, this.accountsAtThisBankComboBox, 42, SpringLayout.NORTH,
				this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.accountsAtThisBankComboBox, 656, SpringLayout.WEST,
				this);
		this.accountsAtThisBankComboBox.setToolTipText("Hier finden Sie Ihre anderen Konten bei dieser Bank");
		add(this.accountsAtThisBankComboBox);
	}

	private void initAccountNumber() {
		this.accountNumberLabel = new JLabel("Kontonummer:");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.accountNumberLabel, 12, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.accountNumberLabel, 12, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.accountNumberLabel, 164, SpringLayout.WEST, this);
		add(this.accountNumberLabel);
	}

	private void initTransferButton() {
		this.transferButton = new JButton("Überweisen");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.transferButton, 249, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.transferButton, 272, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.transferButton, 392, SpringLayout.WEST, this);
		add(this.transferButton);
	}

	private void initLogoutButton() {
		this.logoutButton = new JButton("Logout");
		springLayout.putConstraint(SpringLayout.NORTH, this.logoutButton, 249, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, this.logoutButton, 12, SpringLayout.WEST, this);
		add(this.logoutButton);
	}

	private void initDepositWindowButton() {
		openDepositWindowButton = new JButton("Einzahlen");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.openDepositWindowButton, 249, SpringLayout.NORTH,
				this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.openDepositWindowButton, 404, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.openDepositWindowButton, 524, SpringLayout.WEST, this);
		add(openDepositWindowButton);
	}

	private void initWithdrawWindowButton() {
		openWithdrawWindowButton = new JButton("Auszahlen");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.openWithdrawWindowButton, 249, SpringLayout.NORTH,
				this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.openWithdrawWindowButton, 536, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.openWithdrawWindowButton, 656, SpringLayout.WEST, this);
		add(openWithdrawWindowButton);

	}
	
	private void initActualAccountNumberLabel() {
		this.actualAccountNumberLabel = new JLabel("");
		this.springLayout.putConstraint(SpringLayout.WEST, this.actualAccountNumberLabel, 22, SpringLayout.EAST,
				this.accountNumberLabel);
		this.springLayout.putConstraint(SpringLayout.SOUTH, this.actualAccountNumberLabel, 0, SpringLayout.SOUTH,
				this.accountNumberLabel);
		add(this.actualAccountNumberLabel);
	}
	
	private void initActualFirstNameLastNameLabel() {
		this.actualFirstNameLastNameLabel = new JLabel("");
		this.springLayout.putConstraint(SpringLayout.WEST, this.actualFirstNameLastNameLabel, 0, SpringLayout.WEST,
				this.actualAccountNumberLabel);
		this.springLayout.putConstraint(SpringLayout.SOUTH, this.actualFirstNameLastNameLabel, 0,
				SpringLayout.SOUTH, this.firstNameLastNameLabel);
		add(this.actualFirstNameLastNameLabel);
	}
	
	private void initActualBankLabel() {
		this.actualBankLabel = new JLabel("");
		this.springLayout.putConstraint(SpringLayout.WEST, this.actualBankLabel, 0, SpringLayout.WEST,
				this.actualAccountNumberLabel);
		this.springLayout.putConstraint(SpringLayout.SOUTH, this.actualBankLabel, 0, SpringLayout.SOUTH,
				this.bankLabel);
		add(this.actualBankLabel);
	}
	
	private void initActualAddressLabel() {
		this.actualAddressLabel = new JLabel("");
		this.springLayout.putConstraint(SpringLayout.WEST, this.actualAddressLabel, 0, SpringLayout.WEST,
				this.actualAccountNumberLabel);
		this.springLayout.putConstraint(SpringLayout.SOUTH, this.actualAddressLabel, 0, SpringLayout.SOUTH,
				this.addressLabel);
		add(this.actualAddressLabel);
	}
	
	private void initActualAccountTypeLabel() {
		this.actualAccountTypeLabel = new JLabel("");
		this.springLayout.putConstraint(SpringLayout.WEST, this.actualAccountTypeLabel, 0, SpringLayout.WEST,
				this.actualAccountNumberLabel);
		this.springLayout.putConstraint(SpringLayout.SOUTH, this.actualAccountTypeLabel, 0, SpringLayout.SOUTH,
				this.accountTypeLabel);
		this.actualAccountTypeLabel.setToolTipText("Zeigt, welcher Kontotyp vorliegt.");
		add(this.actualAccountTypeLabel);
	}
	
	private void initActualOverdraftAmountLabel() {
		this.actualOverdraftAmountOrInterestLabel = new JLabel("");
		this.springLayout.putConstraint(SpringLayout.WEST, this.actualOverdraftAmountOrInterestLabel, 0, SpringLayout.WEST,
				this.actualAccountNumberLabel);
		this.springLayout.putConstraint(SpringLayout.SOUTH, this.actualOverdraftAmountOrInterestLabel, 0, SpringLayout.SOUTH,
				this.overdraftAmountOrInterestLabel);
		add(this.actualOverdraftAmountOrInterestLabel);
	}
	
	private void initActualBalanceLabel() {
		this.actualBalanceLabel = new JLabel("");
		this.springLayout.putConstraint(SpringLayout.WEST, this.actualBalanceLabel, 0, SpringLayout.WEST, this.actualAccountNumberLabel);
		this.springLayout.putConstraint(SpringLayout.SOUTH, this.actualBalanceLabel, 0, SpringLayout.SOUTH, this.balanceLabel);
		add(this.actualBalanceLabel);
	}
	
	private void initActualBankCodeLabel() {
		this.actualBankCodeLabel = new JLabel("");
		this.springLayout.putConstraint(SpringLayout.WEST, this.actualBankCodeLabel, 0, SpringLayout.WEST, this.actualAccountNumberLabel);
		this.springLayout.putConstraint(SpringLayout.SOUTH, this.actualBankCodeLabel, 0, SpringLayout.SOUTH, this.bankCodeLabel);
		add(this.actualBankCodeLabel);
	}
	
	public JButton getOpenWithdrawWindowButton() {
		return openWithdrawWindowButton;
	}

	public JButton getOpenDepositWindowButton() {
		return openDepositWindowButton;
	}

	public JButton getLogoutButton() {
		return logoutButton;
	}
	
	public JButton getOpenTransferWindowButton() {
		return transferButton;
	}
	
	public ItemClickAwareComboBox<String> getCombobox() {
		return accountsAtThisBankComboBox;
	}
}