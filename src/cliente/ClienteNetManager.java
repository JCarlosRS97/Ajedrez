package cliente;

import GUI.Cliente.Controlador;
import GUI.Cliente.PanelCliente;
import GUI.Cliente.Tablero;
import Logica.Comandos;
import Logica.Movimiento;
import Utils.SocketUtils;
import Utils.Tuberia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteNetManager extends NetworkClient implements Runnable {
    private Tablero tablero;
    private PanelCliente panelCliente;
    private Controlador controlador;
    private boolean continuar = true;

    public ClienteNetManager(String host, int port, PanelCliente panelCliente, Controlador controlador) {
        super(host, port);
        this.panelCliente = panelCliente;
        this.tablero = panelCliente.getTablero();
        this.controlador = controlador;
    }

    @Override
    protected void handleConnection(Socket client) throws IOException {
        PrintWriter out = SocketUtils.getWriter(client);
        BufferedReader in = SocketUtils.getReader(client);
        String line;
        String[] params;
        controlador.setWriterSocket(out);
        while(continuar){
            line = in.readLine();
            params = line.split(" ");
            if(params[0].equalsIgnoreCase(Comandos.SETCOLOR.toString())){
                boolean isBlancas = params[1].equalsIgnoreCase("BLANCAS");
                panelCliente.setColorAndWrite(isBlancas);
            }else if(params[0].equalsIgnoreCase(Comandos.MOVE.toString())){
                tablero.moverPieza(Integer.parseInt(params[1]), Integer.parseInt(params[2]),
                        Integer.parseInt(params[3]), Integer.parseInt(params[4]));
            }else if(params[0].equalsIgnoreCase(Comandos.GIVE_UP.toString())){
                continuar = false;
                panelCliente.setEnableBtnAbandonar(false);
                panelCliente.setTextLabel("Has ganado!");
            }
        }
        System.out.println(Thread.currentThread().getName() + ": Fin de conexion");
        client.close();
    }

    @Override
    public void run() {
        connect();
    }

    public void closeConnection(){
        continuar = false;
    }
}
