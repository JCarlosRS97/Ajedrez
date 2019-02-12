package PWeb;

import GUI.ServidorPWeb.GUIPWeb;
import Logica.Comandos;
import Utils.RecursosWeb;
import Utils.SocketUtils;
import Utils.WebUtils;
import Utils.NetworkClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

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
        PrintWriter out = SocketUtils.getWriter(client);
        BufferedReader in = SocketUtils.getReader(client);
        out.println(Comandos.NEW_USER + " " + user + " " + password);
        String line = in.readLine();
        guipWeb.appendln(Thread.currentThread().getName() + " -> " + line);
        if(line.contains("ACK"))
            WebUtils.printConfirmPage(pageWriter, serverName);
        else
            WebUtils.printPage(pageWriter, serverName, RecursosWeb.getLoginWeb("El usuario ya existe", 255, 0, 0));
    }
}
