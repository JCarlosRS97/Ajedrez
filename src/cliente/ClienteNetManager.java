package cliente;

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
    private Tuberia<String> tuberia;
    private Tablero tablero;
    private PanelCliente panelCliente;

    public ClienteNetManager(String host, int port, Tuberia<String> tuberia, PanelCliente panelCliente) {
        super(host, port);
        this.tuberia = tuberia;
        this.panelCliente = panelCliente;
        this.tablero = panelCliente.getTablero();
    }

    @Override
    protected void handleConnection(Socket client) throws IOException {
        PrintWriter out = SocketUtils.getWriter(client);
        BufferedReader in = SocketUtils.getReader(client);
        boolean continuar = true;
        //Recibir color
        boolean isBlancas = false;
        String line = in.readLine();
        String[] params = line.split(" ");
        if(params[0].equalsIgnoreCase(Comandos.SETCOLOR.toString())){
            isBlancas = params[1].equalsIgnoreCase("BLANCAS");
            panelCliente.setColorAndWrite(isBlancas);
        }

        if(isBlancas){
            out.printf("%s\n", tuberia.getMovimiento());
        }

        while(continuar){
            line = in.readLine();
            params = line.split(" ");
            if(params[0].equalsIgnoreCase(Comandos.MOVE.toString())){
                tablero.moverPieza(Integer.parseInt(params[1]), Integer.parseInt(params[2]),
                        Integer.parseInt(params[3]), Integer.parseInt(params[4]));
                line = tuberia.getMovimiento();
                if(line.equalsIgnoreCase(Comandos.GIVE_UP.toString())){
                    continuar = false;
                    panelCliente.setEnableBtnAbandonar(false);
                }
                out.printf("%s\n", line);
            }else if(params[0].equalsIgnoreCase(Comandos.GIVE_UP.toString())){
                continuar = false;
                panelCliente.setTextLabel("Has ganado!");
            }
        }
        client.close();
    }

    @Override
    public void run() {
        connect();
    }
}
