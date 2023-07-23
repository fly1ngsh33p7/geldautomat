package view;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.function.Consumer;

public class ItemClickAwareComboBox<T> extends JComboBox<T> {
	private boolean isInInitMode;
	private Consumer<Integer> onSelect;
	
    public ItemClickAwareComboBox() {
    	super();
    	this.setInInitMode(true);
    	
    	JComboBox<T> substituteOfKeywordThis = this;
    	
        addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
            	// only trigger on selects if initMode is off
                if ((e.getStateChange() == ItemEvent.SELECTED) && !isInInitMode) {
                    int selectedIndex = substituteOfKeywordThis.getSelectedIndex();
                	
                	onSelect.accept(selectedIndex);
                }
            }
        });
    }

	public void setInInitMode(boolean isInInitMode) {
		this.isInInitMode = isInInitMode;
	}

	public void setOnSelect(Consumer<Integer> onSelect) {
		this.onSelect = onSelect;
	}
}