package GUI;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    private JButton btnComenzar = new JButton("Comenzar");
    private  Tablero tablero = new Tablero();
    public Panel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(tablero);
        add(btnComenzar);
    }

    public void controlador(Controlador controlador) {
        btnComenzar.addActionListener(controlador);
        btnComenzar.setActionCommand("Comenzar");
        tablero.setControlador(controlador);
    }
}
