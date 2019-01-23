package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Controlador implements ActionListener, MouseListener {
    private Panel panel;
    private Tablero tablero;

    public Controlador(Panel panel) {
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("Comenzar")){
            System.out.println(Thread.currentThread().getName() + " " + SwingUtilities.isEventDispatchThread());

        } else {
            System.err.println("Evento no esperado " + e.getSource().toString());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(tablero != null){
            tablero.setCasillaMarcada((e.getX())/60,  (tablero.getDimension() - e.getY())/60);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(tablero != null){
            tablero.moverPieza((e.getX())/60,  (tablero.getDimension() - e.getY())/60);
            tablero.setCasillaMarcada(-1,  -1);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }
}
