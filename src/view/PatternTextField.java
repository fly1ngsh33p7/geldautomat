package view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PatternTextField extends JFormattedTextField {

    private String oldText;

    public PatternTextField(String regex) {
        super();
        this.oldText = "";

        PatternTextField thisKeywordReplacement = this;
        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                Runnable format = () -> {
                    String input = thisKeywordReplacement.getText();
                    if (!input.matches(regex)) {
                    	thisKeywordReplacement.setText(oldText);
                    } else {
                        oldText = input;
                    }
                };
                SwingUtilities.invokeLater(format);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            	Runnable format = () -> {
                    String input = thisKeywordReplacement.getText();
                    if (!input.matches(regex)) {
                    	thisKeywordReplacement.setText(oldText);
                    } else {
                        oldText = input;
                    }
                };
                SwingUtilities.invokeLater(format);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }
}
