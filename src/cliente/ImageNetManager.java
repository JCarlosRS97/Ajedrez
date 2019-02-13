package cliente;

import Utils.NetworkClient;
import Utils.RecursosCliente;
import Utils.SocketUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class ImageNetManager extends NetworkClient {
    private String user;
    private boolean miUser;
    public ImageNetManager(String host, int port, String user, boolean miUser) {
        super(host, port);
        this.user = user;
        this.miUser = miUser;
    }

    @Override
    protected void handleConnection(Socket client) throws IOException {
        PrintWriter out = SocketUtils.getWriter(client);

        out.println(user);
        BufferedImage image = ImageIO.read(client.getInputStream());
        if(miUser)
            RecursosCliente.setUserImage(image);
        else
            RecursosCliente.setOpponentImage(image);
    }
}
