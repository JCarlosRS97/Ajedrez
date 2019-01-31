package Server;

import GUI.Servidor.GUIServidor;
import Utils.SocketUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BrokenBarrierException;

public class ServerNetManager extends MultiThreadServer{
    private GUIServidor guiServidor;
    private PlayersManager playersManager;
    public ServerNetManager(int port, GUIServidor guiServidor, PlayersManager playersManager) {
        super(port);
        this.guiServidor = guiServidor;
        this.playersManager = playersManager;
    }

    @Override
    public void handleConnection(Socket connection) throws IOException {
        Thread.currentThread().setName("Jugador_" + connection.getPort());
        PrintWriter out = SocketUtils.getWriter(connection);
        BufferedReader in = SocketUtils.getReader(connection);
        String line;
        boolean enJuego = true;
        Player player  = new Player(connection.getInetAddress(), connection.getPort(), out);
        playersManager.addPlayer(player);
        guiServidor.appendText("Se conectó un jugador\n");
        try {
            Partida partida = playersManager.waitForTwoPlayersAndInitialize(player);
            while(enJuego){
                line = in.readLine();
                if(line != null){
                    partida.getWriterOponente(player).println(line);
                    guiServidor.appendText(Thread.currentThread().getName() + " -> " + line +'\n');
                }else{
                    enJuego = false;
                    partida.getWriterOponente(player).println((String)null); // indica el fin de conexion
                }
            }
        } catch (BrokenBarrierException | InterruptedException e) {
            playersManager.removePlayer(player);
            guiServidor.appendText("No se ha podido empezar la partida");
        }
        playersManager.removePlayer(player);
        guiServidor.appendText(Thread.currentThread().getName() + ": Fin de la conexion.\n");
        System.out.println("Fin de la conexión");
        connection.close();
    }
}
