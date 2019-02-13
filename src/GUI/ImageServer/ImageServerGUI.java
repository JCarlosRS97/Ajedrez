package GUI.ImageServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImageServerGUI {
    private JButton clearButton;
    private JButton exitButton;
    private JTextArea textArea1;
    private JPanel panel;
    private JScrollPane scroll;

    public ImageServerGUI() {
        clearButton.addActionListener(e -> textArea1.setText(""));
        exitButton.addActionListener(e -> System.exit(0));
    }
    public JPanel getPanel() {
        return panel;
    }

    public void appendText(String s){
        textArea1.append(s);
        scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
    }
}