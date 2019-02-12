package Utils;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class NetworkClient {
    private final String host;
    private final int port;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public NetworkClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public void connect(){
        try(Socket client = new Socket(host, port)){
            handleConnection(client);
        }catch (UnknownHostException e){
            System.out.println("Unknown host: " + host);
            e.getStackTrace();
        }catch (IOException e){
            System.out.println("IOException: " + host);
            e.getStackTrace();
        }
    }

    protected abstract void handleConnection(Socket client) throws IOException;
}
