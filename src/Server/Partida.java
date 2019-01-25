package Server;

import Utils.ExcededPlayers;
import Utils.Tuberia;

public class Partida {
    private final Player[] players;
    private Tuberia<String> tuberia01, tuberia10;

    public Partida(){
        players = new Player[2];
        tuberia01 = new Tuberia<>();
        tuberia10 = new Tuberia<>();
    }

    public Tuberia getTuberiaTx(Player player){
        if(player == players[0]){
            return tuberia01;
        }
        return tuberia10;
    }

    public Tuberia getTuberiaRx(Player player){
        if(player == players[0]){
            return tuberia10;
        }
        return tuberia01;
    }

    public boolean isFull(){
        return players[1] != null;
    }
    public void addPlayer(Player player) {
/*        if(isFull()){
            throw new ExcededPlayers();
        }*/ //TODO mas adelante
        if(players[0] == null){
            players[0] = player;
        }else{
            players[1] = player;
        }
    }

    public Player getPlayer(int i){
        return players[i];
    }
}
