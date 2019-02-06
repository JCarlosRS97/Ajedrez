package Server;

public class Partida {
    private final Player[] players;

    public Partida(){
        players = new Player[2];
    }

    public Partida(Player p1, Player p2){
        players = new Player[2];
        players[0] = p1;
        players[1] = p2;
    }

    public Player getPlayer(int i){
        return players[i];
    }

    public Player getOponente(Player player) {
        if(player == players[0]) return players[1];
        return players[0];
    }
}
