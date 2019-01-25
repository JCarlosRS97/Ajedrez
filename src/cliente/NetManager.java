package cliente;

import Logica.Movimiento;
import Utils.Tuberia;

import java.io.IOException;
import java.net.Socket;

public class NetManager extends NetworkClient implements Runnable {
    private Tuberia<Movimiento> tuberia;
    public NetManager(String host, int port, Tuberia tuberia) {
        super(host, port);
        this.tuberia = tuberia;
    }

    @Override
    protected void handleConnection(Socket client) throws IOException {
        Movimiento movimiento;

        while(true){
            movimiento = tuberia.getMovimiento();

        }
    }

    @Override
    public void run() {
        connect();
    }
}
