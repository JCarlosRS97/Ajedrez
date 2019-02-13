package Utils;

import Logica.TipoPieza;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RecursosCliente {
    private static BufferedImage piezas;
    private static Image defaultUser;
    private static Image user;
    private static Image opponent;

    static {
        try {
            piezas = ImageIO.read(new File("Resources/ChessPiecesArray.png"));
            defaultUser= ImageIO.read(new File("Resources/user.png"));
        } catch (IOException e) {
            System.out.println("No ha sido posible cargar los recursos.");
            System.exit(-1);
        }
    }
    public static BufferedImage getSpritePieza(TipoPieza pieza, boolean  blancas) {
        return piezas.getSubimage(60*pieza.ordinal(), blancas? 60 : 0, 60 , 60);
    }
    public static Image getSpriteUser(){
        return user.getScaledInstance(60, 85,  java.awt.Image.SCALE_SMOOTH);
    }

    public static Image getSpriteOpponent(){
        return opponent.getScaledInstance(60, 85,  java.awt.Image.SCALE_SMOOTH);
    }

    public static void setUserImage(BufferedImage image){
        if(image == null)
            user = defaultUser;
        else
            user = image;
    }

    public static void setOpponentImage(BufferedImage image){
        if(image == null)
            opponent = defaultUser;
        else
            opponent = image;
    }
}
