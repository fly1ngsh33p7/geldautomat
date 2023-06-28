package view;

import javax.swing.*;
import java.awt.*;

public class AccountScreen extends JPanel {
    private JButton logoutButton;
    private JButton openDepositWindowButton;
    private JButton openWithdrawWindowButton;

    public AccountScreen() {
        setLayout(new FlowLayout());
        JLabel label = new JLabel("Account Screen");
        logoutButton = new JButton("Logout");
        add(label);
        add(logoutButton);

        initDepositWindowButton();

        initWithdrawWindowButton();
    }

    private void initDepositWindowButton() {
        openDepositWindowButton = new JButton("Geld einzahlen");
        JLabel openDepositButtonLabel = new JLabel("Account Screen");
        add(openDepositButtonLabel);
        add(openDepositWindowButton);
    }

    private void initWithdrawWindowButton() {
        openWithdrawWindowButton = new JButton("Geld auszahlen");
        JLabel openWithdrawButtonLabel = new JLabel("Account Screen");
        add(openWithdrawButtonLabel);
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