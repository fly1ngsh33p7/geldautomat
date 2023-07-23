package view.windows;

import javax.swing.*;
import java.awt.*;

public class Popup {
	public static void display(String title, String message, String okButtonText, String cancelButtonText, Runnable okButtonAction, Runnable cancelButtonAction) {
    	JDialog dialog = createDialog(title, message, okButtonText, cancelButtonText, okButtonAction, cancelButtonAction);
        dialog.setVisible(true);
    }
	
	public static void display(String title, String message, String okButtonText, String cancelButtonText, Runnable cancelButtonAction) {
    	JDialog dialog = createDialog(title, message, okButtonText, cancelButtonText, null, cancelButtonAction);
        dialog.setVisible(true);
    }
    
    public static void displayOnlyWithOkButton(String title, String message, String okButtonText) {
    	display(title, message, okButtonText, null, null);
    }
    
    private static JDialog createDialog(String title, String message, String okButtonText, String cancelButtonText, Runnable okButtonAction, Runnable cancelButtonAction) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        // If the message is very long: make the popup wider
        if (message.length() > 35) {
        	dialog.setSize(600, 150);
        } else {
        	dialog.setSize(300, 150);
        }
        
        dialog.setResizable(false);
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
        okButton.addActionListener(e -> {
        	if (okButtonAction != null) {
        		okButtonAction.run();
        	}
        	dialog.dispose();
        });
        buttonPanel.add(okButton);

        // if cancelButtonText and cancelButtonAction are null, display no cancelButton
        if (cancelButtonText != null) {
            JButton cancelButton = new JButton(cancelButtonText);
            cancelButton.addActionListener(e -> {
                dialog.dispose();
                if (cancelButtonAction != null) {
                	cancelButtonAction.run();
                }
            });
            buttonPanel.add(cancelButton);
        }

        dialog.getContentPane().add(panel);
        
        return dialog;
    }
}
