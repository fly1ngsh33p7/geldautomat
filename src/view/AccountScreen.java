package view;

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

	public AccountScreen() {
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
		this.balanceLabel.setBounds(12, 174, 152, 15);
		add(this.balanceLabel);
	}

	private void initOverdraftAmount() {
		this.overdraftAmountLabel = new JLabel("Überziehungsbetrag:");
		this.overdraftAmountLabel.setBounds(12, 147, 152, 15);
		add(this.overdraftAmountLabel);
	}

	private void initAccountType() {
		this.accountTypeLabel = new JLabel("Konto:");
		this.accountTypeLabel.setToolTipText("Zeigt, welcher Kontotyp vorliegt.");
		this.accountTypeLabel.setBounds(12, 120, 152, 15);
		add(this.accountTypeLabel);
	}

	private void initBankCode() {
		this.bankCodeLabel = new JLabel("BLZ:");
		this.bankCodeLabel.setBounds(12, 201, 152, 15);
		add(this.bankCodeLabel);
	}

	private void initBank() {
		this.bankLabel = new JLabel("Bank:");
		this.bankLabel.setBounds(12, 66, 152, 15);
		add(this.bankLabel);
	}

	private void initAddress() {
		this.addressLabel = new JLabel("Adresse:");
		this.addressLabel.setBounds(12, 93, 152, 15);
		add(this.addressLabel);
	}

	private void initFirstNameLastName() {
		this.firstNameLastNameLabel = new JLabel("Vorname / Name:");
		this.firstNameLastNameLabel.setBounds(12, 39, 152, 15);
		add(this.firstNameLastNameLabel);
	}

	private void initAccountsAtThisBankComboBox() {
		this.accountList = Arrays.asList("8008135 (Girokonto)", "weiter", "Konten");
		// TODO this needs data from outside
		this.accountsAtThisBankComboBox = new JComboBox<>(new DefaultComboBoxModel<>(this.accountList.toArray()));
		this.accountsAtThisBankComboBox.setSelectedIndex(1);
		this.accountsAtThisBankComboBox.setToolTipText("Hier finden Sie Ihre andere Konten bei dieser Bank");
		this.accountsAtThisBankComboBox.setBounds(458, 12, 198, 30);
		add(this.accountsAtThisBankComboBox);
	}

	private void initAccountNumber() {
		this.accountNumberLabel = new JLabel("Kontonummer:");
		this.accountNumberLabel.setBounds(12, 12, 152, 15);
		add(this.accountNumberLabel);
	}

	private void initTransferButton() {
		this.transferButton = new JButton("Überweisen");
		this.transferButton.setBounds(272, 249, 120, 25);
		add(this.transferButton);
	}

	private void initLogoutButton() {
		this.logoutButton = new JButton("Logout");
		this.logoutButton.setBounds(12, 249, 84, 25);
		setLayout(null);
	}

	private void initDepositWindowButton() {
		openDepositWindowButton = new JButton("Einzahlen");
		this.openDepositWindowButton.setBounds(404, 249, 120, 25);
		add(openDepositWindowButton);
	}

	private void initWithdrawWindowButton() {
		openWithdrawWindowButton = new JButton("Auszahlen");
		this.openWithdrawWindowButton.setBounds(536, 249, 120, 25);
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