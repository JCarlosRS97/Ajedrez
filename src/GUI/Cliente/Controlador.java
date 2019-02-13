package GUI.Cliente;

import Logica.Comandos;
import Logica.Movimiento;
import cliente.ClienteNetManager;

import java.awt.event.*;
import java.io.PrintWriter;

public class Controlador implements ActionListener, MouseListener {
    private PanelCliente panelCliente;
    private BaseGUI baseGUI;
    private Tablero tablero;
    private String host;
    private int port;
    private PrintWriter out;
    private ClienteNetManager netManager;
    private String user;

    public Controlador(PanelCliente panelCliente, BaseGUI baseGUI, String host, int port) {
        this.panelCliente = panelCliente;
        tablero = panelCliente.getTablero();
        this.baseGUI = baseGUI;
        this.host = host;
        this.port = port;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("CONECTAR")) {
            user = baseGUI.getUser();
            if(user.matches("^.*[^a-zA-Z0-9].*$") || baseGUI.getPassword().matches("^.*[^a-zA-Z0-9].*$")){
                baseGUI.setTextError("Solo se pueden usar caracteres alfanumericos.");
            }else {
                baseGUI.setEnableBtnConectar(false);
                baseGUI.setEnableTxtUser(false);
                baseGUI.setEnableTxtPassword(false);
                netManager = new ClienteNetManager(host, port, panelCliente, baseGUI, this);
                Thread connection = new Thread(netManager);
                connection.setName("Thread_Red");
                connection.start();
            }
        }else if(e.getActionCommand().equalsIgnoreCase("RETAR")){
            String s = baseGUI.getSelectedItem();
            if(s != null)
                out.println(Comandos.MATCH + " " + baseGUI.getSelectedItem());
        } else if(e.getActionCommand().equalsIgnoreCase("ABANDONAR")){
            out.println(Comandos.GIVE_UP);
            panelCliente.setEnableBtnAbandonar(false);
            panelCliente.setEnableBtnVolver(true);
            baseGUI.showLooserDialog();
            tablero.removeMouseListener(this);
        }else if(e.getActionCommand().equalsIgnoreCase("VOLVER")){
            baseGUI.changePanel(BaseGUI.HOME);
        } else if(e.getActionCommand().equalsIgnoreCase("SEND")) {
            //hay que proteger los espacios con un caracter no imprimible
            String s = baseGUI.getMsg();
            if(!s.isEmpty()) {
                out.println(Comandos.SEND_MSG + " <b>" + user +
                        ("</b>: " + baseGUI.getMsg()).replace(" ", Character.valueOf((char) 29).toString()) + "<br>");
                baseGUI.clearMsg();
            }
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

    public String getUser() {
        return user;
    }

    public void sendln(String s){
        out.println(s);
    }

}
