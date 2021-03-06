package Server;

import GUI.Servidor.GUIServidor;
import Logica.Comandos;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class PlayersManager {
    private ReentrantLock lock;
    private Set<Player> players;
    private ArrayList<Partida> partidas;
    private GUIServidor guiServidor;

    public PlayersManager(GUIServidor guiServidor) {
        this.guiServidor = guiServidor;
        lock = new ReentrantLock();
        players = new HashSet<>();
        partidas = new ArrayList<>();
    }

    public void initializeMatch(Partida partida) {
        //TODO comprobar si es necesaria el control de concurrencia
        lock.lock();
        partidas.add(partida);
        lock.unlock();
        Player p1 = partida.getPlayer(0);
        Player p2 = partida.getPlayer(1);
        // Se escoge color
        Random random = new Random();
        p1.setBlancas(random.nextBoolean());
        p2.setBlancas(!p1.isBlancas());
        guiServidor.appendText(new StringBuilder(Thread.currentThread().getName()).append(": ")
                .append(p1.getUser()).append(" es ").append(p1.isBlancas() ? "blancas\n" : "negras\n").toString());
        p1.sendln(Comandos.SET_COLOR + " " + (p1.isBlancas()? "BLANCAS":"NEGRAS") + " " + p2.getUser() + " " + p2.getPuntuacion());

        guiServidor.appendText(new StringBuilder(Thread.currentThread().getName()).append(": ").append(p2.getUser())
                .append(" es ").append(p2.isBlancas() ? "blancas\n" : "negras\n").toString());
        p2.sendln(Comandos.SET_COLOR + " " + (p2.isBlancas()? "BLANCAS":"NEGRAS") + " " + p1.getUser() + " " +  p1.getPuntuacion());

        guiServidor.appendText("Comienza la partida " + (partidas.size()-1) + '\n');
    }

    public boolean addPlayer(Player player) {
        lock.lock();
        boolean ok = players.add(player);
        lock.unlock();
        return ok;
    }
    public void removePlayer(Player player){
        lock.lock();
        players.removeIf(e -> e.equals(player));
        lock.unlock();
    }

    public String getMyPlayers(Player player){
        return players.stream().filter(e -> !e.isPlaying() && !e.equals(player)).map(Player::getUser).collect(Collectors.joining(","));
    }

    public void sendlnBroadcast(String s){
        players.stream().forEach(e -> e.sendln(s));
    }

    public void sendlnToPlayer(String user, String msg){
        Optional<Player> search = players.stream().filter(e -> e.getUser().equalsIgnoreCase(user)).findAny();
        search.ifPresent(player -> player.sendln(msg));
    }

    public Player getPlayer(String user){
        Optional<Player> search = players.stream().filter(e -> e.getUser().equalsIgnoreCase(user)).findAny();
        return search.orElse(null);

    }

    public void removerPartida(Partida partida){
        partidas.remove(partida);
    }
}
