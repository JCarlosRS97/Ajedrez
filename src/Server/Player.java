package Server;

import java.net.InetAddress;

public class Player {
    private InetAddress direction;
    int port;
    boolean isBlancas;

    public Player(InetAddress direction, int port){
        this.direction = direction;
        this.port = port;
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
        Player player2 = (Player) obj;
        if(player2.getDirection().equals(direction) && player2.getPort() == port){
            return true;
        }
        return false;
    }
}
