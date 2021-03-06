package GUI.Servidor;

import javax.swing.*;

public class GUIServidor {
    private JPanel panel1;
    private JButton clearButton;
    private JButton exitButton;
    private JTextArea textArea1;
    private JScrollPane scroll;


    public GUIServidor() {
        clearButton.addActionListener((e) -> textArea1.setText(""));
        exitButton.addActionListener(e -> System.exit(0));
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public synchronized void appendText(String s){
        textArea1.append(s);
        scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
    }

}
