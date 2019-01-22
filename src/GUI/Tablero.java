package GUI;

import Logica.Pieza;
import Logica.TipoPieza;

import javax.swing.*;
import java.awt.*;

public class Tablero extends JPanel {
    public static final int TAM_TABLERO = 8;
    private static int TAM_CASILLA = 60;
    private Pieza[][] tablero;
    private Controlador controlador = null;
    private int xMarcada = -1;
    private int yMarcada = -1;

    public Tablero(){
        tablero = new Pieza[TAM_TABLERO][TAM_TABLERO];
        setPreferredSize(new Dimension(TAM_TABLERO*TAM_CASILLA, TAM_TABLERO*TAM_CASILLA));
        setMaximumSize(getPreferredSize());
        setMinimumSize(getPreferredSize());
        setInitialPosicion();
        repaint();

    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        for(int i = 0; i < TAM_TABLERO; i++){
            for(int j = 0; j < TAM_TABLERO; j++){ //De la esquina superior izquierda para la derecha y abajo
                if((i+j)%2 == 0){
                    if(xMarcada == i && yMarcada == (TAM_TABLERO-j-1)){
                        g2d.setColor(Color.CYAN);
                    }else{
                        g2d.setColor(Color.getHSBColor(14, 25, 82));
                    }
                }else{
                    if(xMarcada == i && yMarcada == (TAM_TABLERO-j-1)){
                        g2d.setColor(Color.CYAN);
                    }else{
                        g2d.setColor(Color.getHSBColor(14, 73, 56));
                    }
                }
                g2d.fillRect(i*60-1, j*60-1, 60, 60);
                if(tablero[i][TAM_TABLERO-j-1] != null){
                    g2d.drawImage(tablero[i][TAM_TABLERO-j-1].getSprite(), i*60-1, j*60-1, null);
                }
            }
        }
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
        addMouseListener(this.controlador);
        controlador.setTablero(this);
    }

    public void setInitialPosicion(){
        for(int i = 0 ; i < TAM_TABLERO; i++){
            tablero[i][1] = new Pieza(i, 1, true, TipoPieza.Peon);
            tablero[i][6] = new Pieza(i, 1, false, TipoPieza.Peon);
        }
        tablero[0][0] = new Pieza(0, 0, true, TipoPieza.Torre);
        tablero[7][0] = new Pieza(7, 0, true, TipoPieza.Torre);
        tablero[0][7] = new Pieza(7, 7, false, TipoPieza.Torre);
        tablero[7][7] = new Pieza(7, 7, false, TipoPieza.Torre);
        tablero[1][0] = new Pieza(1, 0, true, TipoPieza.Caballo);
        tablero[6][0] = new Pieza(6, 0, true, TipoPieza.Caballo);
        tablero[1][7] = new Pieza(1, 7, false, TipoPieza.Caballo);
        tablero[6][7] = new Pieza(6, 7, false, TipoPieza.Caballo);
        tablero[2][0] = new Pieza(2, 0, true, TipoPieza.Alfil);
        tablero[5][0] = new Pieza(5, 0, true, TipoPieza.Alfil);
        tablero[2][7] = new Pieza(2, 7, false, TipoPieza.Alfil);
        tablero[5][7] = new Pieza(5, 7, false, TipoPieza.Alfil);
        tablero[3][0] = new Pieza(3, 0, true, TipoPieza.Dama);
        tablero[4][0] = new Pieza(4, 0, true, TipoPieza.Rey);
        tablero[3][7] = new Pieza(3, 7, false, TipoPieza.Dama);
        tablero[4][7] = new Pieza(4, 7, false, TipoPieza.Rey);


    }

    public void setCasillaMarcada(int x, int y) {
            xMarcada = x;
            yMarcada = y;
        if((x >= 0 && y >= 0) && tablero[x][y] != null){
            repaint();
        }
    }

    public void moverPieza(int x, int y) {
        if(x >= 0 && y >= 0){
            tablero[x][y] = tablero[xMarcada][yMarcada];
            tablero[xMarcada][yMarcada] = null;
            repaint();
        }
    }

    public int getDimension() {
        return TAM_TABLERO*TAM_CASILLA;
    }
}
