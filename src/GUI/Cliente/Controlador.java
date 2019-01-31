package GUI.Cliente;

import Logica.Comandos;
import Logica.Movimiento;
import Utils.Tuberia;
import cliente.ClienteNetManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controlador implements ActionListener, MouseListener {
    private PanelCliente panelCliente;
    private Tablero tablero;
    private String host = "127.0.0.1";
    private int port = 9000;
    private Thread connection;
    private Tuberia<String> tuberia;

    public Controlador(PanelCliente panelCliente) {
        this.panelCliente = panelCliente;
        tuberia = new Tuberia<>();
        tablero = panelCliente.getTablero();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("COMENZAR")){
            System.out.println(Thread.currentThread().getName() + " " + SwingUtilities.isEventDispatchThread());
            tablero.setControlador(this);
            ClienteNetManager netManager = new ClienteNetManager(host, port, tuberia, panelCliente);
            connection = new Thread(netManager);
            connection.setName("Thread_Red");
            connection.start();
            panelCliente.setEnableBtnComenzar(false);
        } else if(e.getActionCommand().equalsIgnoreCase("ABANDONAR")){
            tuberia.setMovimiento(Comandos.GIVE_UP.toString());
            panelCliente.setTextLabel("Has abandonado.");
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
            Movimiento movimiento = tablero.moverPieza((e.getX())/60,  (tablero.getDimension() - e.getY())/60);
            if(movimiento != null)
                tuberia.setMovimiento(Comandos.MOVE.toString() + " " + movimiento);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
