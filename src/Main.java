import view.View;
import control.Control;
import model.BankManagementSystem;

public class Main {
	public static void main(String[] args) {
		View view = new View();

		// Initialize and load data via BankManagementSystem.getInstance()
		// The constructor of Control sets up the rest of the view and thus starts the application
		Control control = new Control(BankManagementSystem.getInstance(), view);
	}
}