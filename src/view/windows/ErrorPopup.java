package view.windows;

import java.lang.Runnable;

public class ErrorPopup extends Popup {
	public static void displayError(String errorMessage, Runnable cancelButtonAction) {
		display(errorMessage, errorMessage, "OK", "Programm beenden", cancelButtonAction);
	}
}
