package Logica;

import java.awt.image.BufferedImage;

public class Pieza {
    private int x;
    private int y;
    private boolean blancas;
    private TipoPieza tipo;

    public boolean isBlancas() {
        return blancas;
    }

    public TipoPieza getTipo() {
        return tipo;
    }

    public Pieza(int x, int y, boolean blancas, TipoPieza tipoPieza){
        this.x = x;
        this.y = y;
        this.blancas = blancas;
        this.tipo = tipoPieza;
    }

    public BufferedImage getSprite(){
        return Recursos.getSprite(tipo, blancas);
    }
}
