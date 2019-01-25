package GUI.Servidor;

import javax.swing.*;
import java.awt.*;

public class PanelServer extends JPanel {

    //central panel
    private JPanel centralPanel;
    private JTextArea centralTextArea;
    //button panel
    private JPanel buttonPanel;
    private JButton timeButton;
    private JButton clearButton;
    private JButton exitButton;


    public PanelServer() {
        setLayout((new BorderLayout()));
        //Central panel
        centralPanel = new JPanel();
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        centralTextArea = new JTextArea("Text Area Empty \n", 10, 4);
        centralTextArea.setFont((new Font("Consolas", Font.BOLD, 14)));
        centralTextArea.setBackground(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(centralTextArea);
        //scrollPane.set
        centralTextArea.setEditable(false);
        centralPanel.add(scrollPane);
        add(centralPanel, BorderLayout.CENTER);
        //buton panel
        buttonPanel = new JPanel();
        timeButton = new JButton("Time");
        timeButton.setActionCommand("TIME");
        clearButton = new JButton("Clear");
        clearButton.setActionCommand("CLEAR");
        exitButton = new JButton("Exit");
        exitButton.setActionCommand("EXIT");
        buttonPanel.setLayout(new GridLayout(1,3));
        buttonPanel.add(timeButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    public void clearTextArea(){
        centralTextArea.setText("");
    }
    public void appendTextArea( String text){
        centralTextArea.append(text + "\n");
    }





}
