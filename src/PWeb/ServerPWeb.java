package PWeb;

import GUI.ServidorPWeb.GUIPWeb;
import Utils.MultiThreadServer;
import Utils.RecursosWeb;
import Utils.SocketUtils;
import Utils.WebUtils;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerPWeb extends MultiThreadServer {
    private GUIPWeb guipWeb;
    private BufferedInputStream stream;
    private File dir; //directorio de imagenes

    public ServerPWeb(int port, GUIPWeb guipWeb){
        super(port);
        this.guipWeb = guipWeb;
        dir = new File("Images");
        if(!dir.isDirectory()){
            dir.mkdir();
        }
    }
    @Override
    public void handleConnection(Socket connection) throws IOException {
        stream = new BufferedInputStream(connection.getInputStream());
        PrintWriter out = SocketUtils.getWriter(connection);
        BufferedReader in = SocketUtils.getReader(connection);
        String name = "Registro";
        List<String> inputLines = new ArrayList<>();
        String line;

        guipWeb.appendln("Se ha producido una nueva conexion.");
        try {
            //Se lee al usuario
            while ((line = readln()) != null) {
                inputLines.add(line);
                System.out.println(line);
                if (line.isEmpty()) { // blank line
                    if (WebUtils.usingPost(inputLines)) { // If POST, one more line to read,
                        String s;
                        String password;
                        String user;
                        do{
                            s = readln();
                            System.out.println(s);
                        }while(s != null && !s.contains("user"));
                        System.out.println(readln()); //Linea en blanco
                        s = readln();
                        if(s!= null && s.isEmpty()){
                            user = null;
                        }else {
                            user = s;
                        }
                        do{
                            s = readln();
                            System.out.println(s);
                        }while(s != null && !s.contains("password"));
                        //Se lee el espacio en blanco
                        System.out.println(readln());
                        s = readln();
                        if(s!= null && s.isEmpty()){
                            password = null;
                        }else {
                            password = s;
                        }
                        //String prueba[] = postData.split("(user=|password=|&)"); No merece la pena siguen siendo 4 elementos en el array
                        if(user ==null || password == null){
                            //Quedan campos por rellenar
                            WebUtils.printPage(out, name, RecursosWeb.getLoginWeb(
                                    "Hay que rellenar todos los campos", 255, 0, 0));
                        }else if(user.matches("^.*[^a-zA-Z0-9].*$") || password.matches("^.*[^a-zA-Z0-9].*$")) {
                            //Quedan campos por rellenar
                            WebUtils.printPage(out, name, RecursosWeb.getLoginWeb(
                                    "Solo se puede usar caracteres alfanumericos.", 255, 0, 0));
                        }else {
                            saveImageIfExist(user);
                            guipWeb.appendln("Nuevo registro capturado.");
                            guipWeb.appendln("User: " + user);
                            guipWeb.appendln("Password: " + password);
                            UserRegister userRegister = new UserRegister(name, out, user, password, guipWeb);
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

    private void saveImageIfExist(String user) throws IOException {
        String s;
        do{
            s = readln();
            System.out.println(s);
        }while(s != null && !s.contains("/png") && !s.contains("/octet-stream"));
        //Se comprueba si hay imagen
        if(s== null || s.contains("/octet-stream")){
            guipWeb.appendln("No habia imagen que cargar");
            return;
        }
        byte[] data = new byte[8000];    // with no CR at end.
        int chars = 1;      // Ignore multi-line posts, such as file uploads.
        String postData;
        System.out.println(readln());
        boolean fin = false;
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(
                dir.getName() + File.separator + user + ".png"));
        char fina[] = new char[4];
        fina[0] = 0;
        fina[1] = 0;
        fina[2] = 0;
        fina[3] = 0;
        while(chars > 0){
            chars = stream.read(data);
            int i = 1;
            fina[3] = (char)data[0];
            while(i < data.length && !fin){
                if(fina[0]=='I' && fina[1] == 'E'&& fina[2] == 'N'&& fina[3] == 'D'){
                    fin = true;
                }else {
                    fina[0] = fina[1];
                    fina[1] = fina[2];
                    fina[2] = fina[3];
                    fina[3] = (char) data[i];
                    i++;
                }
            }
            out.write(data, 0, (i+4)>data.length? data.length : i+4);
            postData = new String(data, 0, chars);
            System.out.println(postData);
            if(fin){
                postData = new String(data, i+4, chars-i-4);
                System.out.println(postData);
                break;
            }
        }
        out.close();

    }

    private String readln() throws IOException {
        StringBuilder builder = new StringBuilder();
        int cur = stream.read();
        while((cur != -1) && (cur != '\n') && (cur != '\r')) {
            builder.append((char)cur);
            cur = stream.read();
        }
        if(cur == -1)
            return null;
        //Se completa si es necesario el retorno de carro
        if(cur == '\r')
            stream.read();
        return builder.toString();
    }
}
