package Logica;

import Utils.RecursosCliente;

import java.awt.image.BufferedImage;

public class Pieza {
    private boolean blancas;
    private TipoPieza tipo;

    public boolean isBlancas() {
        return blancas;
    }

    public Pieza(boolean blancas, TipoPieza tipoPieza){
        this.blancas = blancas;
        this.tipo = tipoPieza;
    }

    public BufferedImage getSprite(){
        return RecursosCliente.getSpritePieza(tipo, blancas);
    }
}
