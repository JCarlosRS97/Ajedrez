package Logica;

import Utils.Recursos;

import java.awt.image.BufferedImage;

public class Pieza {
    private boolean blancas;
    private TipoPieza tipo;

    public boolean isBlancas() {
        return blancas;
    }

    public TipoPieza getTipo() {
        return tipo;
    }

    public Pieza(boolean blancas, TipoPieza tipoPieza){
        this.blancas = blancas;
        this.tipo = tipoPieza;
    }

    public BufferedImage getSprite(){
        return Recursos.getSpritePieza(tipo, blancas);
    }
}
