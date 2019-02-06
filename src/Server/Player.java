package Server;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.concurrent.locks.ReentrantLock;

public class Player {
    private InetAddress direction;
    private int port;
    private boolean isBlancas;
    private PrintWriter out;
    private String user;
    private boolean playing;
    private Partida partida = null;
    private int puntuacion;

    public String getUser() {
        return user;
    }

    public Player(String user, InetAddress direction, int port, PrintWriter out, int puntuacion){
        this.user = user;
        this.direction = direction;
        this.port = port;
        this.out = out;
        playing = false;
        this.puntuacion = puntuacion;
    }

    public Player(String user, InetAddress direction, int port, PrintWriter out){
        this(user, direction, port, out, 1500);
    }


    public InetAddress getDirection() {
        return direction;
    }

    public int getPort() {
        return port;
    }

    public boolean isBlancas() {
        return isBlancas;
    }

    public void setBlancas(boolean blancas) {
        isBlancas = blancas;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Player){
            Player player2 = (Player) obj;
            return player2.getDirection().equals(direction) && player2.getPort() == port
                    && player2.getUser().equals(user);
        }
        return false;
    }

    public synchronized void sendln(String s){
        out.println(s);
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }
}
