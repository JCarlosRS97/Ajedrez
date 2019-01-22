package Server;

import Utils.SocketUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerTest extends NetworkServer {

	public ServerTest(int port) {
		super(port);
	}

	public static void main(String[] args) {
		int port = 9000;
		
		ServerTest tester = new ServerTest(port);
		tester.listen();
	}

	@Override
	protected void handleConnection(Socket socket) throws IOException {
		PrintWriter out = SocketUtils.getWriter(socket); // 3
		BufferedReader in = SocketUtils.getReader(socket); // 4
        System.out.println("Se ha producido una conexion.");
        String line = in.readLine().replace(',', '.');
        double a = Double.parseDouble(line);
        System.out.printf("El primer numero es: %f\n",a);
        line = in.readLine().replace(',', '.');
        double b = Double.parseDouble(line);
        System.out.printf("El segundo numero es: %f\n",b);
        out.printf("%f\n", a+b);
		socket.close();
	}

}
