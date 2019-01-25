package Server;

import GUI.Servidor.GUIServidor;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PlayersManager {
    private ReentrantLock lock;
    private Condition isNotEnought;
    int numJugadores = 0;
    private CyclicBarrier barrier;
    private ArrayList<Player> players;
    private ArrayList<Partida> partidas;
    private GUIServidor guiServidor;

    public PlayersManager(GUIServidor guiServidor) {
        this.guiServidor = guiServidor;
        lock = new ReentrantLock();
        isNotEnought = lock.newCondition();
        barrier = new CyclicBarrier(2);
        players = new ArrayList<>();
        partidas = new ArrayList<>();
    }

    public int getNumJugadores() { // proteje el numero de jugadores
        int a;
        lock.lock();
        a = numJugadores;
        lock.unlock();        
        return a;
    }

    public Partida waitForTwoPlayersAndInitialize(Player yo) throws BrokenBarrierException, InterruptedException {
        barrier.await();
        //Ya son dos jugadores, se asigna un color
        lock.lock();
        Partida partida;
        //TODO Criterio elegir adversario y repasar concurrencia
        if(partidas.isEmpty() || partidas.get(partidas.size()-1).isFull()){//Esta es la partida anterior que se ha completado
            partida = new Partida();
            partida.addPlayer(yo);
            partidas.add(partida);
        }else{
            partida = partidas.get(partidas.size()-1);
            partida.addPlayer(yo);
            // Se escoge color
            Random random = new Random();
            yo.setBlancas(random.nextBoolean());
            partida.getPlayer(0).setBlancas(!yo.isBlancas());
            guiServidor.appendText("Comienza la partida " + (partidas.size()-1) + '\n');
        }
        lock.unlock();
        return partida;
    }

    public void addPlayer(Player player) {
        lock.lock();
        players.add(player);
        lock.unlock();
    }
    public void removePlayer(Player player){
        lock.lock();
        players.removeIf(e -> e.equals(player));
        lock.unlock();
    }
}
