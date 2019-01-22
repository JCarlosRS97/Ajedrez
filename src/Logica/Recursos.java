package Logica;

import Logica.TipoPieza;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Recursos {
    private static BufferedImage recursos;

    static {
        try {
            recursos = ImageIO.read(new File("ChessPiecesArray.png"));
        } catch (IOException e) {
            System.out.println("No ha sido posible cargar los recursos.");
            System.exit(-1);
        }
    }
    public static BufferedImage getSprite(TipoPieza pieza, boolean  blancas) {
        return recursos.getSubimage(60*pieza.ordinal(), blancas? 60 : 0, 60 , 60);
    }
}
