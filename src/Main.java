import view.Popup;
import view.View;
import model.BankManagementSystem;
import control.Control;

import javax.swing.*;
import java.io.File;

public class Main {
	public static void main(String[] args) {
		// Choose your database file to load the data
		File selectedFile;
		do {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(null);
			selectedFile = fileChooser.getSelectedFile();

			if (selectedFile == null) {
				Popup.display("Info", "Bitte wählen Sie eine Datei aus.", "OK", "Programm beenden", () -> System.exit(0));
			}
		} while (selectedFile == null);

		
		// init the MVC-Structure
		BankManagementSystem bms = new BankManagementSystem(selectedFile);
		View view = new View();

		Control control = new Control(bms, view);

		// Start the GUI application
		SwingUtilities.invokeLater(() -> view.setupGUI());
	}
}