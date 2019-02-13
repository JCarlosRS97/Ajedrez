package GUI.ServidorPWeb;

import javax.swing.*;

public class GUIPWeb {
    private JTextArea textArea1;
    private JButton clearButton;
    private JButton exitButton;
    private JPanel panel;
    private JScrollPane scroll;

    public GUIPWeb() {
        clearButton.addActionListener((e) -> textArea1.setText(""));
        exitButton.addActionListener(e -> System.exit(0));
    }

    public JPanel getPanel() {
        return panel;
    }

    public synchronized void appendln(String s){
        textArea1.append(s + '\n');
        scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
    }
}
