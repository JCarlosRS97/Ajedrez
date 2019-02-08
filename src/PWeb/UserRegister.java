package PWeb;

import GUI.ServidorPWeb.GUIPWeb;
import Logica.Comandos;
import Utils.RecursosWeb;
import Utils.SocketUtils;
import Utils.WebUtils;
import cliente.NetworkClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class UserRegister extends NetworkClient {
    private String user;
    private String password;
    private String serverName;
    private PrintWriter pageWriter;
    private GUIPWeb guipWeb;

    public UserRegister(String serverName, PrintWriter pageWriter, String user, String password, GUIPWeb guipWeb) {
        super("127.0.0.1", 9000);
        this.user = user;
        this.password = password;
        this.serverName = serverName;
        this.pageWriter = pageWriter;
        this.guipWeb = guipWeb;
    }

    @Override
    protected void handleConnection(Socket client) throws IOException {
        boolean error = false;
        try {
            PrintWriter out = SocketUtils.getWriter(client);
            out.println(Comandos.NEW_USER + " " + user + " " + password);
        }catch (IOException e){
            //Si ha habido error se modifica la pagina web
            error = true;
            //Se relanza el error
            throw new IOException();
        }finally {
            if(error) {
                WebUtils.printPage(pageWriter, serverName,
                        RecursosWeb.getLoginWeb("No se ha podido registar el usuario. Pruebe más tarde.", 255, 0, 0));
                guipWeb.appendln("No se ha podido enviar correctamente el nuevo usuario.");
            } else {
                WebUtils.printConfirmPage(pageWriter, serverName);
                guipWeb.appendln("Se ha enviado el usuario correctamente.");
            }
        }
    }
}
