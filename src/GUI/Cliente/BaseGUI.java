package GUI.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BaseGUI {
    private JPanel panel1;
    private JPanel partida;
    private JPanel Central;
    private JList list1;
    private JButton conectarButton;
    private JButton retarButton;
    private JTextField textField1;

    public BaseGUI(PanelCliente panelCliente) {
        partida.add(panelCliente);
        conectarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout)panel1.getLayout()).show(panel1, "Card1");
            }
        });
    }

    public JPanel getPanel1() {
        return panel1;
    }
}
