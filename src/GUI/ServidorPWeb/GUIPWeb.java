package GUI.ServidorPWeb;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIPWeb {
    private JTextArea textArea1;
    private JButton clearButton;
    private JButton exitButton;
    private JPanel panel;

    public GUIPWeb() {
        clearButton.addActionListener((e) -> textArea1.setText(""));
        exitButton.addActionListener(e -> System.exit(0));
    }

    public JPanel getPanel() {
        return panel;
    }

    public void appendln(String s){
        textArea1.append(s + '\n');
    }
}
