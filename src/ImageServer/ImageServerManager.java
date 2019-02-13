package ImageServer;

import GUI.ImageServer.ImageServerGUI;
import Utils.MultiThreadServer;
import Utils.NetworkClient;
import Utils.SocketUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class ImageServerManager extends MultiThreadServer {
    private ImageServerGUI gui;
    public ImageServerManager(int port, ImageServerGUI gui) {
        super(port);
        this.gui = gui;
    }

    @Override
    public void handleConnection(Socket client) throws IOException {
        BufferedReader in = SocketUtils.getReader(client);
        BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
        String user = in.readLine();
        File file = new File("Images" + File.separator + user + ".png");
        if(!file.isFile()){
            client.getOutputStream().close();
            return;
        }
        BufferedImage image = ImageIO.read(file);
        ImageIO.write(image, "png", client.getOutputStream());
    }
}
