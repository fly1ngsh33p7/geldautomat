package view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This PatternTextField makes sure that only Strings matching the provided
 * pattern may be put in it. Other than that it behaves like a normal 
 * J(Formatted)TextField.
 */
public class PatternTextField extends JFormattedTextField {

    private String oldText;

    public PatternTextField(String pattern) {
        super();
        this.oldText = "";

        PatternTextField thisKeywordReplacement = this;
        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                Runnable format = () -> {
                    String input = thisKeywordReplacement.getText();
                    if (!input.matches(pattern)) {
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
                    if (!input.matches(pattern)) {
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
