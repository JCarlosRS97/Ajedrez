package Server;

import java.io.PrintWriter;
import java.net.InetAddress;

public class Player {
    private InetAddress direction;
    private int port;
    private boolean isBlancas;
    private PrintWriter out;

    public Player(InetAddress direction, int port, PrintWriter out){
        this.direction = direction;
        this.port = port;
        this.out = out;
    }

    public Player(){
        port = -1;
        direction = null;
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
            return player2.getDirection().equals(direction) && player2.getPort() == port;
        }
        return false;
    }

    public PrintWriter getWriter(){
        return out;
    }
}
