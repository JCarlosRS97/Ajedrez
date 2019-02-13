package ImageServer;

import GUI.ImageServer.ImageServerGUI;
import Utils.MultiThreadServer;
import Utils.SocketUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
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
        String user = in.readLine();
        gui.appendln("Se solicita la imagen del usuario: " + user);
        File file = new File("Images" + File.separator + user + ".png");
        if(!file.isFile()){
            client.getOutputStream().close();
            gui.appendln("No existe ninguna imagen.");
            return;
        }
        gui.appendln("Se procede a enviar la imagen.");
        BufferedImage image = ImageIO.read(file);
        ImageIO.write(image, "png", client.getOutputStream());
    }
}
