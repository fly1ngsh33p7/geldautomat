package view;

import javax.swing.*;
import java.awt.*;

public class Popup {
    public static void display(String title, String message, String okButtonText, String cancelButtonText, Runnable cancelButtonAction) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(300, 150);
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel(message);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(messageLabel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        JButton okButton = new JButton(okButtonText);
        okButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(okButton);

        JButton cancelButton = new JButton(cancelButtonText);
        cancelButton.addActionListener(e -> {
            dialog.dispose();
            cancelButtonAction.run();
        });        
        buttonPanel.add(cancelButton);

        

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
    }
}
