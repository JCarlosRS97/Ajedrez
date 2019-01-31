package GUI.Cliente;

import javax.swing.*;
import java.awt.*;

public class PanelCliente extends JPanel {
    private JButton btnComenzar = new JButton("Comenzar");
    private JButton btnAbandonar = new JButton("Abandonar");
    private  Tablero tablero = new Tablero();
    private JPanel panel  = new JPanel();
    private JLabel label = new JLabel("Color: ");

    public PanelCliente() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //Panel de botones y labels
        panel.setLayout(new GridLayout(1,5));
        panel.add(btnComenzar);
        panel.add(new JSeparator(JSeparator.HORIZONTAL));
        panel.add(btnAbandonar);
        panel.add(new JSeparator(JSeparator.HORIZONTAL));
        panel.add(label);

        //Se añaden los elementos
        add(tablero);
        add(panel);
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setEnableBtnComenzar(boolean enable){
        btnComenzar.setEnabled(enable);
    }

    public void setEnableBtnAbandonar(boolean enable){ btnAbandonar.setEnabled(enable);}

    public void setColorAndWrite(boolean color) {
        tablero.setColor(color);
        label.setText(label.getText() + (color? "blancas" : "negras"));
    }

    public void setTextLabel(String s){
        label.setText(s);
    }

    public void controlador(Controlador controlador) {
        btnComenzar.addActionListener(controlador);
        btnComenzar.setActionCommand("COMENZAR");
        btnAbandonar.addActionListener(controlador);
        btnAbandonar.setActionCommand("ABANDONAR");
    }

}
