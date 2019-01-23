package cliente;

import java.io.IOException;
import java.net.Socket;

public class NetManager extends NetworkClient {
    public NetManager(String host, int port) {
        super(host, port);
    }

    @Override
    protected void handleConnection(Socket client) throws IOException {

    }
}
