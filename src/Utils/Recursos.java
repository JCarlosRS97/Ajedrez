package Utils;

import Logica.TipoPieza;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Recursos {
    private static BufferedImage piezas;
    private static Image user;

    static {
        try {
            piezas = ImageIO.read(new File("ChessPiecesArray.png"));
            user = ImageIO.read(new File("user.png")).getScaledInstance(40, 35,  java.awt.Image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.out.println("No ha sido posible cargar los recursos.");
            System.exit(-1);
        }
    }
    public static BufferedImage getSpritePieza(TipoPieza pieza, boolean  blancas) {
        return piezas.getSubimage(60*pieza.ordinal(), blancas? 60 : 0, 60 , 60);
    }
    public static Image getSpriteUser(){
        return user;
    }
}
