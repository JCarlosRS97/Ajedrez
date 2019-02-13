package GUI.ImageServer;

import javax.swing.*;

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

    public void appendln(String s){
        textArea1.append(s + '\n');
        scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
    }
}
