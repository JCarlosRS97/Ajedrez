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
        Player oponente;
        Partida partida;
        guiServidor.appendText("Nueva conexion.\n");

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
                            if (params.length < 3) {
                                guiServidor.appendText(Thread.currentThread().getName() + " -> " + "No ha rellenado los campos.\n");
                                out.println(Comandos.CONNECT + " Introduzca,un,usuario,y,una,contraseña.");
                                conectado = false;
                                break;
                            }
                            player = UserData.loadUser(params[1], params[2]);
                            boolean ok = false;
                            if(player != null){
                                player.setWriter(out);
                                ok = playersManager.addPlayer(player);
                            }else {
                                guiServidor.appendText(Thread.currentThread().getName() + " -> Los datos son incorrectos.\n");
                                out.println(Comandos.CONNECT + " Los,datos,son,incorrectos.");
                                conectado = false;
                            }
                            if (!ok) {
                                player = null;
                                guiServidor.appendText(Thread.currentThread().getName() + " -> El usuario ya esta conectado.\n");
                                out.println(Comandos.CONNECT + " El,usuario,ya,esta,conectado");
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
                            oponente = playersManager.getPlayer(params[1]);
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
                            if(player != null && player.isPlaying()){
                                partida = player.getPartida();
                                if(partida != null){
                                    partida.getOponente(player).sendln(line);
                                }else{
                                    guiServidor.appendText("Se ha producido un error en la partida.\n");
                                }
                            }else{
                                guiServidor.appendText("Se ha producido un error en la partida.\n");
                            }
                            break;
                        case GIVE_UP:
                            if(player != null && player.isPlaying()){
                                partida = player.getPartida();
                                if(partida != null){
                                    guiServidor.appendText(player.getUser() + " se ha rendido.\n");
                                    oponente = partida.getOponente(player);
                                    oponente.sendln(line);
                                    //Se gestiona la reintroduccion en la lista de los jugadores
                                    oponente.setPlaying(false);
                                    player.setPlaying(false);
                                    oponente.setPartida(null);
                                    player.setPartida(null);
                                    playersManager.removerPartida(partida);
                                    playersManager.sendlnBroadcast(Comandos.ADD_USERS + " " + player.getUser() +
                                            "," + oponente.getUser());
                                }else{
                                    guiServidor.appendText("Se ha producido un error en la partida.\n");
                                }
                            }else{
                                guiServidor.appendText("Se ha producido un error en la partida.\n");
                            }
                            break;
                        case NEW_USER:
                            guiServidor.appendText("Se ha añadido el jugador: " + params[1] + '\n');
                            UserData.saveNewUser(params[1], params[2]);
                            conectado = false;
                            break;
                    }

                }
            }
        }catch (SocketException e){
            //Si se cierra el canal
            guiServidor.appendText("Se ha cerrado el canal\n");
        }finally {
            guiServidor.appendText(Thread.currentThread().getName() + ": Fin de la conexion por el usuario.\n");
            if(player != null){
                playersManager.removePlayer(player);
                playersManager.sendlnBroadcast(Comandos.DELETE_USERS + " " + player.getUser());
                partida = player.getPartida();
                if(player.isPlaying() && partida != null){
                    oponente = partida.getOponente(player);
                    oponente.setPlaying(false);
                    oponente.setPartida(null);
                    playersManager.removerPartida(partida);
                    playersManager.sendlnBroadcast(Comandos.ADD_USERS + " " + oponente.getUser());
                }
                UserData.saveUser(player);
            }
        }

        System.out.println("Fin de la conexión");
        connection.close();
    }
}
