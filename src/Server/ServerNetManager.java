package Server;

import GUI.Servidor.GUIServidor;
import Utils.SocketUtils;
import Utils.Tuberia;

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
        Player player  = new Player(connection.getInetAddress(), connection.getPort());
        playersManager.addPlayer(player);
        guiServidor.appendText("Se conectó un jugador\n");
        try {
            Partida partida = playersManager.waitForTwoPlayersAndInitialize(player);
            Tuberia<String> tuberiaTx = partida.getTuberiaTx(player);
            Tuberia<String> tuberiaRx = partida.getTuberiaRx(player);
            guiServidor.appendText(Thread.currentThread().getName() + ": es " + (player.isBlancas()? "blancas\n" : "negras\n"));
            out.printf("/SETCOLOR %s", player.isBlancas()? "BLANCAS\n":"NEGRAS\n");
            if (player.isBlancas()) {
                line = in.readLine();
                tuberiaTx.setMovimiento(line +'\n');
                guiServidor.appendText(Thread.currentThread().getName() + ": in -> " + line +'\n');
            }
            while(enJuego){//TODO cerrar conexiones cuando acaba la partida
                line = tuberiaRx.getMovimiento();
                out.printf(line);
                guiServidor.appendText(Thread.currentThread().getName() + ": out ->" + line);
                line = in.readLine();
                tuberiaTx.setMovimiento(line +'\n');
                guiServidor.appendText(Thread.currentThread().getName() + ": in -> " + line +'\n');
            }
        } catch (BrokenBarrierException | InterruptedException e) {
            playersManager.removePlayer(player);
            guiServidor.appendText("No se ha podido empezar la partida");
        }
        playersManager.removePlayer(player);
        connection.close();
    }
}
