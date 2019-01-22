package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/** A starting point for network servers. */
public abstract class NetworkServer {
	private int port = 8080;

	/** Build a server on specified port. 
	 * It will continue to accept connections, 
	 * passing each to handleConnection until the server is killed 
	 * (e.g., Control-C in the startup window) 
	 * or System.exit() from handleConnection 
	 * or elsewhere in the Java code).
	*/
	public NetworkServer(int port) {
		this.port = port;
		System.out.println("New Server on port "+ this.port);
	}

	/** Monitor a port for connections. 
	 * Each time one is established, 
	 * pass resulting Socket to handleConnection.
	*/
	public void listen(){
		try(ServerSocket listener = new ServerSocket(port)){ // 1
			Socket socket;
			while (true){ // 2. until killed
				socket = listener.accept(); // 2
				handleConnection(socket);   // 2
			}
		} catch (IOException e) {
			System.out.println("IOException: "+e);
			e.printStackTrace();
		}
	}

	/** This is the method that should be provide 
	 * the behavior to the server, 
	 * since it determines what is done with the resulting socket. 
	 * Override this method in servers you write.
	 */
	protected abstract void handleConnection(Socket socket) 
		throws IOException;

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
}