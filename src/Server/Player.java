package Server;

import java.io.PrintWriter;

public class Player {
    private boolean isBlancas;
    private PrintWriter out;
    private String user;
    private boolean playing;
    private Partida partida = null;
    private int puntuacion;

    private final float K = 40;

    public String getUser() {
        return user;
    }

    public Player(String user, PrintWriter out, int puntuacion){
        this.user = user;
        this.out = out;
        playing = false;
        this.puntuacion = puntuacion;
    }

    public Player(String user, PrintWriter out){
        this(user, out, 1500);
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
            return player2.getUser().equals(user);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return user.hashCode();
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

    public int getPuntuacion() {
        return puntuacion;
    }

    @Override
    public String toString() {
        return user + " " + puntuacion;
    }

    public void setWriter(PrintWriter out) {
        this.out = out;
    }

    public void setPuntuacionUpdate(int oponente, double res){
        //Se utiliza el algoritmo de elo
        double e = 1/(1 + Math.pow(10.0, ((double) oponente - (double)puntuacion)/400));
        puntuacion += K*(res - e);
    }
}
