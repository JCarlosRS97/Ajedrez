package GUI.Cliente;

import Logica.Comandos;
import Logica.Movimiento;
import cliente.ClienteNetManager;

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
    private PrintWriter out;
    private ClienteNetManager netManager;

    public Controlador(PanelCliente panelCliente) {
        this.panelCliente = panelCliente;
        tablero = panelCliente.getTablero();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("COMENZAR")){
            tablero.setControlador(this);
            netManager = new ClienteNetManager(host, port, panelCliente, this);
            Thread connection = new Thread(netManager);
            connection.setName("Thread_Red");
            connection.start();
            panelCliente.setEnableBtnComenzar(false);
        } else if(e.getActionCommand().equalsIgnoreCase("ABANDONAR")){
            out.println(Comandos.GIVE_UP);
            netManager.closeConnection();
            panelCliente.setEnableBtnAbandonar(false);
            panelCliente.setTextLabel("Has abandonado.");
            tablero.removeMouseListener(this);
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
