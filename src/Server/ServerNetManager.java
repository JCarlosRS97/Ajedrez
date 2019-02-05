package Server;

import GUI.Servidor.GUIServidor;
import Logica.Comandos;
import Utils.SocketUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ServerNetManager extends MultiThreadServer{
    private GUIServidor guiServidor;
    private PlayersManager playersManager;
    public ServerNetManager(int port, GUIServidor guiServidor, PlayersManager playersManager) {
        super(port);
        this.guiServidor = guiServidor;
        this.playersManager = playersManager;
    }

    @Override
    public void handleConnection(Socket connection) throws IOException {
        Thread.currentThread().setName("Jugador_" + connection.getPort());
        PrintWriter out = SocketUtils.getWriter(connection);
        BufferedReader in = SocketUtils.getReader(connection);
        String line;
        String[] params;
        boolean conectado = true;
        Player player = null;
        Partida partida = null;
        guiServidor.appendText("Se intenta conectar un jugador\n");

        try {
            while (conectado) {
                line = in.readLine();
                guiServidor.appendText(Thread.currentThread().getName() + " -> " + line + '\n');
                if (line == null) {
                    //Fin de la conexion
                    conectado = false;
                } else {
                    params = line.split(" ");
                    switch (Comandos.valueOf(params[0])) {
                        case CONNECT:
                            if (params.length < 2) {
                                guiServidor.appendText(Thread.currentThread().getName() + " -> " + "No ha escrito ningun usuario.\n");
                                out.println(Comandos.CONNECT + " Introduzca,un,usuario.");
                                conectado = false;
                                break;
                            }
                            player = new Player(params[1], connection.getInetAddress(), connection.getPort(), out);
                            boolean ok = playersManager.addPlayer(player);
                            if (!ok) {
                                guiServidor.appendText(Thread.currentThread().getName() + " -> El nombre de usuario ya esta en uso.\n");
                                out.println(Comandos.CONNECT + " El,usuario,ya,existe.");
                                conectado = false;
                            } else {
                                guiServidor.appendText(player.getUser() + " se ha conectado correctamente.\n");
                                player.sendln(Comandos.CONNECT + " ACK");
                                player.sendln(Comandos.ADD_USERS + " " + playersManager.getMyPlayers(player));
                                //Se envia el nuevo usuario la resto
                                playersManager.sendlnBroadcast(Comandos.ADD_USERS + " " + player.getUser());
                            }
                            break;
                        case MATCH:
                            if(player!= null){
                                guiServidor.appendText(new StringBuilder(Thread.currentThread().getName()).append(" -> El jugador ")
                                        .append(player.getUser()).append(" reta a ").append(params[1]).append("\n").toString());
                                playersManager.sendlnToPlayer(params[1], Comandos.MATCH + " " + player.getUser());
                            }
                            break;
                        case MATCH_ACK:
                            Player oponente = playersManager.getPlayer(params[1]);
                            if(player != null && oponente!= null && !oponente.isPlaying()){
                                player.setPlaying(true);
                                oponente.setPlaying(true);
                                playersManager.sendlnBroadcast(Comandos.DELETE_USERS + " " + player.getUser() +
                                        "," + oponente.getUser());
                                partida = new Partida(player, oponente);
                                player.setPartida(partida);
                                oponente.setPartida(partida);
                                playersManager.initializeMatch(partida);
                            }
                            break;
                        case MOVE:
                            if(player.isPlaying()){
                                partida = player.getPartida();
                                if(partida != null){
                                    partida.getOponente(player).sendln(line);
                                }else{
                                    guiServidor.appendText("Se ha producido un error en la partida.\n");
                                }
                            }
                            break;

                    }

                }
            }
        }catch (SocketException e){
            //Si se cierra el canal
            guiServidor.appendText(Thread.currentThread().getName() + ": Fin de la conexion por el usuario.\n");
        }finally {
            if(player != null){
                playersManager.removePlayer(player);
                playersManager.sendlnBroadcast(Comandos.DELETE_USERS + " " + player.getUser());
            }
        }

        System.out.println("Fin de la conexión");
        connection.close();
    }
}
