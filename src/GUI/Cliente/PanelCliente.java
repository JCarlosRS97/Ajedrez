package GUI.Cliente;

import javax.swing.*;

public class PanelCliente extends JPanel {
    private JButton btnComenzar = new JButton("Comenzar");
    private  Tablero tablero = new Tablero();
    public PanelCliente() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(tablero);
        add(btnComenzar);
    }

    public void controlador(Controlador controlador) {
        btnComenzar.addActionListener(controlador);
        btnComenzar.setActionCommand("COMENZAR");
        tablero.setControlador(controlador);
    }
}
