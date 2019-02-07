package PWeb;

import Logica.Comandos;
import Utils.SocketUtils;
import cliente.NetworkClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class UserRegister extends NetworkClient {
    private String user;
    private String password;

    public UserRegister(String user, String password) {
        super("127.0.0.1", 9000);
        this.user = user;
        this.password = password;
    }

    @Override
    protected void handleConnection(Socket client) throws IOException {
        PrintWriter out = SocketUtils.getWriter(client);
        BufferedReader in = SocketUtils.getReader(client);
        out.println(Comandos.NEW_USER + " " + user + " " + password);
    }
}
