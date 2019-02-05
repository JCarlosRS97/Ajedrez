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

    public Player getOponente(Player player) {
        if(player == players[0]) return players[1];
        return players[0];
    }
}
