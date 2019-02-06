package cliente;

import GUI.Cliente.BaseGUI;
import GUI.Cliente.Controlador;
import GUI.Cliente.PanelCliente;
import GUI.Cliente.Tablero;
import Logica.Comandos;
import Utils.SocketUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class ClienteNetManager extends NetworkClient implements Runnable {
    private Tablero tablero;
    private PanelCliente panelCliente;
    private BaseGUI baseGUI;
    private Controlador controlador;
    private boolean conectado = true;

    public ClienteNetManager(String host, int port, PanelCliente panelCliente, BaseGUI baseGUI, Controlador controlador) {
        super(host, port);
        this.panelCliente = panelCliente;
        this.tablero = panelCliente.getTablero();
        this.controlador = controlador;
        this.baseGUI = baseGUI;
    }

    @Override
    protected void handleConnection(Socket client) throws IOException {
        PrintWriter out = SocketUtils.getWriter(client);
        BufferedReader in = SocketUtils.getReader(client);
        String line;
        String[] params;
        out.printf("%s %s\n", Comandos.CONNECT, controlador.getUser());
        controlador.setWriterSocket(out);
        while(conectado){
            line = in.readLine();
            if(line != null) {
                params = line.split(" ");
                switch (Comandos.valueOf(params[0])){
                    case SET_COLOR:
                        tablero.setControlador(controlador);
                        tablero.restart();
                        baseGUI.changePanel(BaseGUI.PARTIDA);
                        boolean isBlancas = params[1].equalsIgnoreCase("BLANCAS");
                        panelCliente.setColorAndWrite(isBlancas);
                        baseGUI.setTxtUsers(isBlancas, params[2]);
                        panelCliente.setEnableBtnAbandonar(true);
                        panelCliente.setEnableBtnVolver(false);
                        break;
                    case MOVE:
                        tablero.moverPieza(Integer.parseInt(params[1]), Integer.parseInt(params[2]),
                                Integer.parseInt(params[3]), Integer.parseInt(params[4]));
                        break;
                    case GIVE_UP:
                        finPartida();
                        break;
                    case CONNECT:
                        if(!params[1].equalsIgnoreCase("ACK")){
                            conectado = false;
                            baseGUI.setTextError(params[1].replaceAll(",", " "));
                            baseGUI.setEnableBtnConectar(true);
                            baseGUI.setEnableTxtUser(true);
                        }else{
                            //Si el cliente se conecta de forma correcta
                            baseGUI.setTextErrorVisible(false);
                            baseGUI.setEnableBtnRetar(true);
                        }
                        break;
                    case ADD_USERS:
                        if(params.length>=2)
                            baseGUI.addList(params[1].split(","));
                        break;
                    case DELETE_USERS:
                        if(params.length>=2){
                            String users[] = params[1].split(",");
                            baseGUI.deleteUserList(users);
                            if(tablero.getMouseListeners().length != 0){
                                //Si esta en juego una partida
                                if(Arrays.stream(users).anyMatch(e -> e.equals(baseGUI.getOponente()))){
                                    finPartida();
                                }
                            }


                        }
                        break;
                    case MATCH:
                        //Se recibe un reto
                        baseGUI.askForMatch(params[1]); // Se le pasa el usuario retador
                        break;
                }
            }else{
                conectado = false;
            }
        }
        controlador.setWriterSocket(null);
        System.out.println(Thread.currentThread().getName() + ": Fin de conexion");
        client.close();
    }

    @Override
    public void run() {
        connect();
    }

    public void closeConnection(){
        conectado = false;
    }

    private void finPartida(){
        panelCliente.setEnableBtnAbandonar(false);
        tablero.removeMouseListener(controlador);
        baseGUI.showWinnerDialog();
        panelCliente.setEnableBtnVolver(true);
    }
}
