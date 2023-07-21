package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

public class KeyAdapterWithSelectSupport extends KeyAdapter {
    private final Runnable runnable;

    public KeyAdapterWithSelectSupport(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int selectionStart = ((JTextField) e.getSource()).getSelectionStart();
        int selectionEnd = ((JTextField) e.getSource()).getSelectionEnd();

        if (selectionStart != selectionEnd) {
            removeSelectedText((JTextField) e.getSource());
        }

        moveCursorToPreviousPosition((JTextField) e.getSource(), selectionStart);

        // Execute the provided formatRunnable if it's not null
        if (runnable != null) {
            runnable.run();
        }
    }

    private void removeSelectedText(JTextField textField) {
    	int selectionStart = textField.getSelectionStart();
        int selectionEnd = textField.getSelectionEnd();

        String text = textField.getText();
        String newText = text.substring(0, selectionStart) + text.substring(selectionEnd);
        textField.setText(newText);

        textField.setCaretPosition(selectionStart);
    }

    private void moveCursorToPreviousPosition(JTextField textField, int previousPosition) {
    	textField.setCaretPosition(previousPosition);
    }
}
