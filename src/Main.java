import model.Model;
import view.View;

import javax.swing.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.showOpenDialog(null);
        File selectedFile = fileChooser.getSelectedFile();
        
        Model model = new Model(selectedFile);

        View view = new View();
    }
}