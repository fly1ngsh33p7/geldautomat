package view.windows;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.SpringLayout;

import model.NotEnoughMoneyException;
import view.BooleanConsumer;
import view.TransferBooleanConsumer;

import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextField;
import javax.swing.JButton;


/**
 * The TransferWindow class represents a small window for conducting a money transfer between accounts.
 * It extends the JFrame class and provides functionality for entering transfer details, such as bank code, account number, and amount.
 * The window includes "Abbrechen" and "Überweisen" buttons for canceling the transfer and initiating the transfer, respectively.
 * 
 * The TransferWindow can be used to prompt the user for transfer details, and when the "Überweisen" button is clicked,
 * the provided TransferBooleanConsumer is invoked to perform the transfer operation.
 * The window can be closed either by clicking the "Abbrechen" button or the window's close button (X).
 * 
 * Note: The class includes two constructors - one default constructor and one constructor with parameters for setting up the window
 * with the specified parent frame, transferMoney action, and onCloseOperation action.
 */
public class TransferWindow extends JFrame {
	private JLabel transferScreenLabel;
	private JLabel toLabel;
	private JLabel bankCodeLabel;
	private JLabel amountLabel;
	private JLabel currencyLabel;
	private JLabel accountNumberLabel;
	private JTextField bankCodeField;
	private JTextField amountField;
	private JTextField accountNumberField;
	private JButton cancelButton;
	private JButton transferButton;
	
	private JPanel panel;
	private SpringLayout springLayout;
	
	private Runnable onCloseOperation;
	private JFrame parentFrame;
	private TransferBooleanConsumer<String, Integer, Double> transferMoney;

	/**
	 * Default constructor for creating a TransferWindow instance.
	 * This constructor sets up the basic attributes of the window, such as preferred size and title, and initializes the main panel.
	 */
	public TransferWindow() {
		setPreferredSize(new Dimension(470, 140));
		setResizable(false);

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		setTitle("Überweisen");
	}
	
	/**
	 * Constructor for creating a TransferWindow instance with specified parameters for parent frame, transferMoney action, and onCloseOperation action.
	 * It calls the default constructor to set up the basic attributes of the window and then proceeds to set the provided parameters for the TransferWindow.
	 * The specified parent frame defines the window's parent frame or the frame relative to which the TransferWindow will be displayed.
	 * The transferMoney parameter is a TransferBooleanConsumer representing the action to be executed when the user initiates the transfer by clicking the "Überweisen" button.
	 * The transferMoney action is responsible for processing the transfer based on the entered details, and it should return true if the operation is successful, or false if there is an error.
	 * The onCloseOperation parameter is a Runnable representing an optional action to be executed when the window is closed. It can be used to perform any clean-up or handle specific behavior upon window closure.
	 * This action will be triggered before the window is closed.
	 * 
	 * @param parentFrame The parent frame or the frame relative to which the TransferWindow will be displayed.
	 * @param transferMoney The action to be executed when the user initiates the transfer by clicking the "Überweisen" button.
	 * @param onCloseOperation An optional action to be executed when the window is closed. It is triggered before the window is closed.
	 */
	public TransferWindow(JFrame parentFrame, TransferBooleanConsumer<String, Integer, Double> transferMoney, Runnable onCloseOperation) {
		this();
		setParentFrameTransferMoneyConsumerAndOnCloseOperation(parentFrame, transferMoney, onCloseOperation);
	}
	
	/**
	 * Sets the parent frame, transferMoney action, and onCloseOperation action for the TransferWindow instance.
	 * This method is used when creating a TransferWindow with specified parameters after the default constructor has been called.
	 * It sets up the provided parameters for the TransferWindow and initializes the window layout and components.
	 * 
	 * @param parentFrame The parent frame or the frame relative to which the TransferWindow will be displayed.
	 * @param transferMoney The action to be executed when the user initiates the transfer by clicking the "Überweisen" button.
	 * @param onCloseOperation An optional action to be executed when the window is closed. It is triggered before the window is closed.
	 */
	public void setParentFrameTransferMoneyConsumerAndOnCloseOperation(JFrame parentFrame, TransferBooleanConsumer<String, Integer, Double> transferMoney, Runnable onCloseOperation) {
		this.parentFrame = parentFrame;
		this.transferMoney = transferMoney;
		this.onCloseOperation = onCloseOperation;
		
		// Add a WindowListener to handle the closing event when clicking the (X)-button
		if (onCloseOperation != null) {
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					onCloseOperation.run();
				}
			});
		}
		
		initialize();

		setLocationRelativeTo(parentFrame);
		pack();
	}
	
	/**
	 * Closes the TransferWindow and runs the onCloseOperation action.
	 * This method is used to close the window programmatically when the transfer is completed or canceled.
	 * It also triggers the provided onCloseOperation action before closing the window.
	 */
	public void closeWindow() {
		onCloseOperation.run();
		dispose();
	}
	
	/**
	 * The onSubmitFunction method is a private helper method used in the TransferWindow class.
	 * It is responsible for processing the transfer when the user clicks the "Überweisen" button in the transfer window.
	 * The method first checks if the bank code, account number, and amount fields are not empty before proceeding with the transfer.
	 * If all the required fields have valid values, the method invokes the provided transferMoney action to perform the transfer.
	 * The transferMoney action takes the entered bank code, account number, and amount as parameters and processes the transfer.
	 * The action should return true if the transfer is successful and false if there is an error or any issues during the transfer process.
	 * 
	 * If the transferMoney action returns true, indicating a successful transfer, the method triggers the onCloseOperation action to perform any cleanup or handle specific behavior upon window closure.
	 * Finally, the method closes the transfer window by invoking the dispose() method on the window instance.
	 * 
	 * Note: Proper validation and parsing of input data are essential for a successful transfer process. It is important to ensure that the input data is in the correct format and valid for processing the transfer.
	 * If the input data is invalid or any exceptions occur during the parsing or transfer process, they should be appropriately handled to provide meaningful feedback to the user.
	 * 
	 */
	private void onSubmitFunction() {
		if (!this.bankCodeField.getText().equals("") && !this.amountField.getText().equals("") && !this.accountNumberField.getText().equals("")) {
			
			// run transferMoney
			boolean wasOperationSuccessful = transferMoney.accept(this.bankCodeField.getText(), Integer.parseInt(this.accountNumberField.getText()), Double.parseDouble(this.amountField.getText())); // TODO gescheites Parsing wie bei den anderen stellen auch
			
			// Close the transfer window if it was successful:
			if (wasOperationSuccessful) {
				onCloseOperation.run();
				dispose();
			}
		}
	}

	// init fields
	
	private void initialize() {
		initTransferScreenLabel();
		initToLabel();
		initBankCodeLabel();
		initAmountLabel();
		initBankCodeField();
		initAmountField();
		initCurrencyLabel();
		initAccountNumberLabel();
		initAccountNumberField();
		initButtons();
	}
	
	private void initButtons() {
		this.cancelButton = new JButton("Abbrechen");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.cancelButton, 110, SpringLayout.NORTH, this.panel);
		this.springLayout.putConstraint(SpringLayout.WEST, this.cancelButton, 345, SpringLayout.WEST, this.panel);
		this.springLayout.putConstraint(SpringLayout.EAST, this.cancelButton, 464, SpringLayout.WEST, this.panel);
		cancelButton.addActionListener(e -> {
			// clear the fields
			bankCodeField.setText("");
			amountField.setText("");
			accountNumberField.setText("");
			
			// Run onCloseAction and close the transfer window
			onCloseOperation.run();
			dispose();
		});
		
		
		this.transferButton = new JButton("Überweisen");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.transferButton, 110, SpringLayout.NORTH, this.panel);
		this.springLayout.putConstraint(SpringLayout.WEST, this.transferButton, 220, SpringLayout.WEST, this.panel);

		// Add the submit action 
		ActionListener onSubmit = e -> onSubmitFunction();
		transferButton.addActionListener(onSubmit);
		
		KeyAdapter submitOnEnter = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	        		onSubmitFunction();
	            }
			}
		};
		bankCodeField.addKeyListener(submitOnEnter);
		amountField.addKeyListener(submitOnEnter);
		accountNumberField.addKeyListener(submitOnEnter);
		
		panel.add(this.cancelButton);
		panel.add(this.transferButton);
	}
	
	private void initTransferScreenLabel() {
		springLayout = new SpringLayout();
		this.panel.setLayout(springLayout);
		this.transferScreenLabel = new JLabel("Überweisen");
		springLayout.putConstraint(SpringLayout.NORTH, this.transferScreenLabel, 10, SpringLayout.NORTH, this.panel);
		springLayout.putConstraint(SpringLayout.WEST, this.transferScreenLabel, 10, SpringLayout.WEST, this.panel);
		this.transferScreenLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		panel.add(this.transferScreenLabel);
	}

	private void initToLabel() {
		this.toLabel = new JLabel("An:");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.toLabel, 31, SpringLayout.NORTH, this.panel);
		this.springLayout.putConstraint(SpringLayout.WEST, this.toLabel, 10, SpringLayout.WEST, this.panel);
		panel.add(this.toLabel);
	}

	private void initBankCodeLabel() {
		this.bankCodeLabel = new JLabel("BLZ:");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.bankCodeLabel, 50, SpringLayout.NORTH, this.panel);
		this.springLayout.putConstraint(SpringLayout.WEST, this.bankCodeLabel, 51, SpringLayout.WEST, this.panel);
		panel.add(this.bankCodeLabel);
	}

	private void initAmountLabel() {
		this.amountLabel = new JLabel("Betrag:");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.amountLabel, 80, SpringLayout.NORTH, this.panel);
		this.springLayout.putConstraint(SpringLayout.WEST, this.amountLabel, 30, SpringLayout.WEST, this.panel);
		panel.add(this.amountLabel);
	}

	private void initBankCodeField() {
		this.bankCodeField = new JTextField();
		this.springLayout.putConstraint(SpringLayout.NORTH, this.bankCodeField, 50, SpringLayout.NORTH, this.panel);
		this.springLayout.putConstraint(SpringLayout.WEST, this.bankCodeField, 89, SpringLayout.WEST, this.panel);
		this.springLayout.putConstraint(SpringLayout.EAST, this.bankCodeField, 197, SpringLayout.WEST, this.panel);
		panel.add(this.bankCodeField);
		this.bankCodeField.setColumns(10);
	}

	private void initAmountField() {
		this.amountField = new JTextField();
		this.springLayout.putConstraint(SpringLayout.NORTH, this.amountField, 80, SpringLayout.NORTH, this.panel);
		this.springLayout.putConstraint(SpringLayout.WEST, this.amountField, 89, SpringLayout.WEST, this.panel);
		this.springLayout.putConstraint(SpringLayout.EAST, this.amountField, 181, SpringLayout.WEST, this.panel);
		this.amountField.setColumns(10);
		panel.add(this.amountField);
	}

	private void initCurrencyLabel() {
		this.currencyLabel = new JLabel("€");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.currencyLabel, 80, SpringLayout.NORTH, this.panel);
		this.springLayout.putConstraint(SpringLayout.WEST, this.currencyLabel, 187, SpringLayout.WEST, this.panel);
		panel.add(this.currencyLabel);
	}

	private void initAccountNumberLabel() {
		this.accountNumberLabel = new JLabel("Kontonummer:");
		this.springLayout.putConstraint(SpringLayout.NORTH, this.accountNumberLabel, 50, SpringLayout.NORTH, this.panel);
		this.springLayout.putConstraint(SpringLayout.WEST, this.accountNumberLabel, 223, SpringLayout.WEST, this.panel);
		panel.add(this.accountNumberLabel);
	}

	private void initAccountNumberField() {
		this.accountNumberField = new JTextField();
		this.springLayout.putConstraint(SpringLayout.NORTH, this.accountNumberField, 50, SpringLayout.NORTH, this.panel);
		this.springLayout.putConstraint(SpringLayout.WEST, this.accountNumberField, 333, SpringLayout.WEST, this.panel);
		this.springLayout.putConstraint(SpringLayout.EAST, this.accountNumberField, 433, SpringLayout.WEST, this.panel);
		panel.add(this.accountNumberField);
		this.accountNumberField.setColumns(10);
	}
}
