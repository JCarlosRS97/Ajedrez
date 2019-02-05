package Server;

import GUI.Servidor.GUIServidor;
import Logica.Comandos;

import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class PlayersManager {
    private ReentrantLock lock;
    int numJugadores = 0;
    private Set<Player> players;
    private ArrayList<Partida> partidas;
    private GUIServidor guiServidor;

    public PlayersManager(GUIServidor guiServidor) {
        this.guiServidor = guiServidor;
        lock = new ReentrantLock();
        players = new HashSet<>();
        partidas = new ArrayList<>();
    }

    public int getNumJugadores() { // proteje el numero de jugadores
        int a;
        lock.lock();
        a = numJugadores;
        lock.unlock();        
        return a;
    }

    public void initializeMatch(Partida partida) {
        //TODO comprobar si es necesaria el control de concurrencia
        lock.lock();
        partidas.add(partida);
        Player p1 = partida.getPlayer(0);
        Player p2 = partida.getPlayer(1);
        // Se escoge color
        Random random = new Random();
        p1.setBlancas(random.nextBoolean());
        partida.getPlayer(0).setBlancas(!p1.isBlancas());
        guiServidor.appendText(String.format("%s: %s es %s", Thread.currentThread().getName(),
                p1.getUser(), p1.isBlancas() ? "blancas\n" : "negras\n"));

        p1.sendln(Comandos.SET_COLOR + " " + (p1.isBlancas()? "BLANCAS":"NEGRAS"));
        guiServidor.appendText(String.format("%s: %s es %s", Thread.currentThread().getName(), p2.getUser(), !p1.isBlancas() ? "blancas\n" : "negras\n"));
        p2.sendln(Comandos.SET_COLOR + " " + (!p1.isBlancas()? "BLANCAS":"NEGRAS"));
        guiServidor.appendText("Comienza la partida " + (partidas.size()-1) + '\n');
        lock.unlock();
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

    public boolean sendlnToPlayer(String user, String msg){
        Optional<Player> search = players.stream().filter(e -> e.getUser().equalsIgnoreCase(user)).findAny();
        if(search.isPresent()){
            search.get().sendln(msg);
            return true;
        }
        return false;
    }

    public Player getPlayer(String user){
        Optional<Player> search = players.stream().filter(e -> e.getUser().equalsIgnoreCase(user)).findAny();
        return search.orElse(null);

    }
}
