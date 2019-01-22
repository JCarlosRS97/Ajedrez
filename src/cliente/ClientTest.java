package cliente;

import Utils.SocketUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientTest extends NetworkClient {
    @Override
    protected void handleConnection(Socket client) throws IOException {
        PrintWriter out = SocketUtils.getWriter(client);
        BufferedReader in = SocketUtils.getReader(client);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Conectado al servidor.");
        System.out.printf("Introduzca un numero: ");
        double a = Double.parseDouble(scanner.next().replace(',', '.'));
        System.out.printf("Ponga otro numero: ");
        double b = Double.parseDouble(scanner.next().replace(',', '.'));
        out.printf("%f\n%f\n", a, b);
        String line = in.readLine().replace(',', '.');
        double res = Double.parseDouble(line);
        System.out.printf("El resultado es: %f\n",res);
        client.close();

    }
    public ClientTest(String host, int port){
        super(host, port);
    }

    public static void main(String[] args) {
        String host;
        int puerto;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduzca el servidor: ");
        host = scanner.next();
        System.out.println("Introduzca el puerto: ");
        puerto = scanner.nextInt();
        ClientTest tester = new ClientTest(host, puerto);
        tester.connect();
    }
}
