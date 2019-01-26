package GUI.Cliente;

import javax.swing.*;
import java.awt.*;

public class PanelCliente extends JPanel {
    private JButton btnComenzar = new JButton("Comenzar");
    private  Tablero tablero = new Tablero();
    private JPanel panel  = new JPanel();
    private JLabel label = new JLabel("Color: ");
    public PanelCliente() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //Panel de botones y labels
        panel.setLayout(new GridLayout(1,3));
        panel.add(btnComenzar);
        panel.add(new JSeparator(JSeparator.VERTICAL));
        panel.add(label);

        //Se añaden los elementos
        add(tablero);
        add(panel);
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void disableBtnComenzar(){
        btnComenzar.setEnabled(false);
    }

    public void setColorAndWrite(boolean miColor) {
        tablero.setColor(miColor);
        label.setText(label.getText() + (miColor? "blancas" : "negras"));
    }

    public void controlador(Controlador controlador) {
        btnComenzar.addActionListener(controlador);
        btnComenzar.setActionCommand("COMENZAR");
        tablero.setControlador(controlador);
    }
}
