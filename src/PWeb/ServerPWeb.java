package PWeb;

import GUI.ServidorPWeb.GUIPWeb;
import Utils.MultiThreadServer;
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
                        //String prueba[] = postData.split("(user=|password=|&)"); No merece la pena siguen siendo 4 elementos en el array
                        if(params.length < 4){
                            //Quedan campos por rellenar
                            WebUtils.printPage(out, name, RecursosWeb.getLoginWeb(
                                    "Hay que rellenar todos los campos", 255, 0, 0));
                        }else if(params[1].matches("^.*[^a-zA-Z0-9].*$") || params[3].matches("^.*[^a-zA-Z0-9].*$")) {
                            //Quedan campos por rellenar
                            WebUtils.printPage(out, name, RecursosWeb.getLoginWeb(
                                    "Solo se puede usar caracteres alfanumericos.", 255, 0, 0));
                        }else {
                                guipWeb.appendln("Nuevo registro capturado.");
                                guipWeb.appendln("User: " + params[1]);
                                guipWeb.appendln("Password: " + params[3]);
                                UserRegister userRegister = new UserRegister(name, out, params[1], params[3], guipWeb);
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
