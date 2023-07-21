package view.screens;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

import model.Account;
import model.CheckingAccount;
import model.Owner;
import model.SavingAccount;

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
	private JComboBox accountsAtThisBankComboBox;
	private JButton btnLogout;
	private SpringLayout springLayout;

	private List<Account> accountList;
	private Account currentAccount; // FIXME das soll in BankManagementSystem, hier nur Account entgegennehmen und
									// Labels setzen
	private JLabel actualAccountNumberLabel;
	private JLabel actualFirstNameLastNameLabel;
	private JLabel actualBankLabel;
	private JLabel actualAddressLabel;
	private JLabel actualAccountTypeLabel;
	private JLabel actualOverdraftAmountOrInterestLabel;
	private JLabel actualBalanceLabel;
	private JLabel actualBankCodeLabel;

	public AccountScreen(List<Account> accountList, int indexOfAccountToDisplay) {
		this(accountList);

		setCurrentAccount(this.accountList.get(indexOfAccountToDisplay));

		this.accountsAtThisBankComboBox.setSelectedIndex(0);

	}

	public AccountScreen(List<Account> accountList) {
		this();
		this.accountList = accountList;
	}

	public AccountScreen() {
		setPreferredSize(new Dimension(670, 357));
		springLayout = new SpringLayout();
		setLayout(springLayout);

		this.accountList = new ArrayList<>();

		initLogoutButton();
		initDepositWindowButton();
		initWithdrawWindowButton();
		initAccountsAtThisBankComboBox();
		initTransferButton();
		
		
		initLabels();
		initActualLabels();
	}

	public void setCurrentAccount(Account account) {
		this.currentAccount = account; // FIXME, eig in BankManagementSystem, also das Speichern des currentAccounts

		// set the values in the actualLabels
		Owner owner = this.currentAccount.getOwner();
		
		this.actualAccountNumberLabel.setText("" + this.currentAccount.getAccountNumber());
		this.actualFirstNameLastNameLabel.setText(owner.getFirstName() + " " + owner.getLastName());
		this.actualAddressLabel.setText(owner.getStreet() + ", " + owner.getPostalCode() + " " + owner.getLocation());
		this.actualBankLabel.setText(this.currentAccount.getBank().getName());
		this.actualBankCodeLabel.setText(this.currentAccount.getBank().getBankCode());
		this.actualAccountTypeLabel.setText(this.currentAccount.getAccountType());
		this.actualBalanceLabel.setText("" + this.currentAccount.getBalance() + " €");

		if (this.currentAccount instanceof SavingAccount) {
			this.overdraftAmountOrInterestLabel.setText("Zins:");
			
			double interestRate = ((SavingAccount) this.currentAccount).getInterest();
			this.actualOverdraftAmountOrInterestLabel.setText("" + interestRate + " %"); //FIXME ist es wirklich in prozent eingelesen?
		} else if (this.currentAccount instanceof CheckingAccount) {
			this.overdraftAmountOrInterestLabel.setText("Überziehungsbetrag:");
			
			double overdraftAmount = ((CheckingAccount) this.currentAccount).getOverdraftAmount();
			this.actualOverdraftAmountOrInterestLabel.setText("" + overdraftAmount + " €");
		}

	}

	public Account getCurrentAccount() {
		return this.currentAccount;
	}

	private void clearActualLabels() {
		// TODO setText("") bei allen actualLabels machen
	}

	private void switchAccount(int accountIndex) {
		this.setCurrentAccount(this.accountList.get(accountIndex));
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

	private void initAccountsAtThisBankComboBox() {
		// this.accountList = Arrays.asList("8008135 (Girokonto)", "weiter", "Konten");
		// TODO this needs data from outside
		this.accountsAtThisBankComboBox = new JComboBox<>(new DefaultComboBoxModel<>(this.accountList.toArray()));
		this.springLayout.putConstraint(SpringLayout.NORTH, this.accountsAtThisBankComboBox, 12, SpringLayout.NORTH,
				this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.accountsAtThisBankComboBox, 458, SpringLayout.WEST,
				this);
		this.springLayout.putConstraint(SpringLayout.SOUTH, this.accountsAtThisBankComboBox, 42, SpringLayout.NORTH,
				this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.accountsAtThisBankComboBox, 656, SpringLayout.WEST,
				this);
		this.accountsAtThisBankComboBox.setToolTipText("Hier finden Sie Ihre andere Konten bei dieser Bank");
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
}