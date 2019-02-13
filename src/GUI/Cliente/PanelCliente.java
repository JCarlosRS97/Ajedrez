package GUI.Cliente;

import javax.swing.*;
import java.awt.*;

public class PanelCliente extends JPanel {
    private JButton btnVolver = new JButton("Volver");
    private JButton btnAbandonar = new JButton("Abandonar");
    private  Tablero tablero = new Tablero();
    private JPanel panel  = new JPanel();
    private JLabel label = new JLabel("Color: ");

    public PanelCliente() {
        btnVolver.setEnabled(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //Panel de botones y labels
        panel.setLayout(new GridLayout(1,5));
        panel.add(btnVolver);
        panel.add(new JSeparator(JSeparator.HORIZONTAL));
        panel.add(btnAbandonar);
        btnAbandonar.setEnabled(false);
        panel.add(new JSeparator(JSeparator.HORIZONTAL));
        panel.add(label);

        //Se añaden los elementos
        add(tablero);
        add(panel);
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setEnableBtnVolver(boolean enable){
        btnVolver.setEnabled(enable);
    }

    public void setEnableBtnAbandonar(boolean enable){ btnAbandonar.setEnabled(enable);}

    public void setColorAndWrite(boolean color) {
        tablero.setColor(color);
        label.setText("Color : " + (color? "blancas" : "negras"));
    }

    public void controlador(Controlador controlador) {
        btnVolver.addActionListener(controlador);
        btnVolver.setActionCommand("VOLVER");
        btnAbandonar.addActionListener(controlador);
        btnAbandonar.setActionCommand("ABANDONAR");
    }
}
