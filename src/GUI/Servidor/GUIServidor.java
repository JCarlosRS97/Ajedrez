package GUI.Servidor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIServidor {
    private JPanel panel1;
    private JButton clearButton;
    private JButton exitButton;
    private JTextArea textArea1;


    public GUIServidor() {
        clearButton.addActionListener((e) -> textArea1.setText(""));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void appendText(String s){
        textArea1.append(s);
    }

}
