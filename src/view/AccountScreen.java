package view;

import java.awt.Dimension; //TODO darf ich awt Dimension verwenden?
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

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
	private JLabel overdraftAmountLabel;
	private JLabel balanceLabel;
	private JComboBox accountsAtThisBankComboBox;
	private List<String> accountList;
	private JButton btnLogout;
	private SpringLayout springLayout;

	public AccountScreen() {
		setPreferredSize(new Dimension(670, 375));
		springLayout = new SpringLayout();
		setLayout(springLayout);

		initLogoutButton();
		initDepositWindowButton();
		initWithdrawWindowButton();
		initAccountNumber();
		initAccountsAtThisBankComboBox();
		initTransferButton();
		initFirstNameLastName();
		initAddress();
		initBank();
		initBankCode();
		initAccountType();
		initOverdraftAmount();
		initBalance();
	}

	private void initBalance() {
		this.balanceLabel = new JLabel("Kontostand:");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.balanceLabel, 174, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.balanceLabel, 12, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.balanceLabel, 164, SpringLayout.WEST, this);
		add(this.balanceLabel);
	}

	private void initOverdraftAmount() {
		this.overdraftAmountLabel = new JLabel("Überziehungsbetrag:");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.overdraftAmountLabel, 147, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.overdraftAmountLabel, 12, SpringLayout.WEST, this);
		add(this.overdraftAmountLabel);
	}

	private void initAccountType() {
		this.accountTypeLabel = new JLabel("Konto:");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.accountTypeLabel, 120, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.accountTypeLabel, 12, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.accountTypeLabel, 164, SpringLayout.WEST, this);
		this.accountTypeLabel.setToolTipText("Zeigt, welcher Kontotyp vorliegt.");
		add(this.accountTypeLabel);
	}

	private void initBankCode() {
		this.bankCodeLabel = new JLabel("BLZ:");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.bankCodeLabel, 201, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.bankCodeLabel, 12, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.bankCodeLabel, 164, SpringLayout.WEST, this);
		add(this.bankCodeLabel);
	}

	private void initBank() {
		this.bankLabel = new JLabel("Bank:");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.bankLabel, 66, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.bankLabel, 12, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.bankLabel, 164, SpringLayout.WEST, this);
		add(this.bankLabel);
	}

	private void initAddress() {
		this.addressLabel = new JLabel("Adresse:");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.addressLabel, 93, SpringLayout.NORTH, this);
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
		this.accountList = Arrays.asList("8008135 (Girokonto)", "weiter", "Konten");
		// TODO this needs data from outside
		this.accountsAtThisBankComboBox = new JComboBox<>(new DefaultComboBoxModel<>(this.accountList.toArray()));
		this.springLayout.putConstraint(SpringLayout.NORTH, this.accountsAtThisBankComboBox, 12, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.accountsAtThisBankComboBox, 458, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.SOUTH, this.accountsAtThisBankComboBox, 42, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.accountsAtThisBankComboBox, 656, SpringLayout.WEST, this);
		this.accountsAtThisBankComboBox.setSelectedIndex(1);
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
		this.springLayout.putConstraint(SpringLayout.NORTH, this.openDepositWindowButton, 249, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.openDepositWindowButton, 404, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.openDepositWindowButton, 524, SpringLayout.WEST, this);
		add(openDepositWindowButton);
	}

	private void initWithdrawWindowButton() {
		openWithdrawWindowButton = new JButton("Auszahlen");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.openWithdrawWindowButton, 249, SpringLayout.NORTH, this);
		this.springLayout.putConstraint(SpringLayout.WEST, this.openWithdrawWindowButton, 536, SpringLayout.WEST, this);
		this.springLayout.putConstraint(SpringLayout.EAST, this.openWithdrawWindowButton, 656, SpringLayout.WEST, this);
		add(openWithdrawWindowButton);

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