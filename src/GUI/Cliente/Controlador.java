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
import java.io.PrintWriter;

public class Controlador implements ActionListener, MouseListener {
    private PanelCliente panelCliente;
    private Tablero tablero;
    private String host = "127.0.0.1";
    private int port = 9000;
    private Thread connection;
    private Tuberia<String> tuberia;
    private PrintWriter out;
    private ClienteNetManager netManager;

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
            netManager = new ClienteNetManager(host, port, panelCliente, this);
            connection = new Thread(netManager);
            connection.setName("Thread_Red");
            connection.start();
            panelCliente.setEnableBtnComenzar(false);
        } else if(e.getActionCommand().equalsIgnoreCase("ABANDONAR")){
            out.printf("%s\n", Comandos.GIVE_UP);
            netManager.closeConnection();
            panelCliente.setEnableBtnAbandonar(false);
            panelCliente.setTextLabel("Has abandonado.");
        } else {
            System.err.println("Evento no esperado " + e.getSource().toString());
        }
    }

    public void setWriterSocket(PrintWriter writer){
        out = writer;
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
                out.printf("%s %s\n", Comandos.MOVE.toString(), movimiento);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
