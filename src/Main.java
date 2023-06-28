import view.View;

import javax.swing.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.showOpenDialog(null);
        File selectedFile = fileChooser.getSelectedFile();

        View view = new View();
    }
}