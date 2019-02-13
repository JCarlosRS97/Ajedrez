package GUI.Cliente;

import Logica.Movimiento;
import Logica.Pieza;
import Logica.TipoPieza;

import javax.swing.*;
import java.awt.*;

public class Tablero extends JPanel {
    private static final int TAM_TABLERO = 8;
    private static int TAM_CASILLA = 60;
    private Pieza[][] tablero;
    private int xMarcada = -1;
    private int yMarcada = -1;
    private boolean color = false;
    private boolean turno = false;

    public Tablero(){
        tablero = new Pieza[TAM_TABLERO][TAM_TABLERO];
        setPreferredSize(new Dimension(TAM_TABLERO*TAM_CASILLA, TAM_TABLERO*TAM_CASILLA));
        setMaximumSize(getPreferredSize());
        setMinimumSize(getPreferredSize());
    }

    public void setColor(boolean miColor) {
        this.color = miColor;
        turno = color;
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        for(int i = 0; i < TAM_TABLERO; i++){
            for(int j = 0; j < TAM_TABLERO; j++){ //De la esquina superior izquierda para la derecha y abajo
                //Se colorea el tablero
                if((i+j)%2 == 0){
                    g2d.setColor(Color.getHSBColor(14, 25, 82));
                }else{
                    g2d.setColor(Color.getHSBColor(14, 73, 56));
                }
                g2d.fillRect(i*60-1, j*60-1, 60, 60);
                //Si hay una casilla señalada
                if(xMarcada == i && yMarcada == (TAM_TABLERO-j-1)){
                    g2d.setColor(new Color(92, 176, 234, 60));
                }
                g2d.fillRect(i*60-1, j*60-1, 60, 60);
                //Se dibuja la pieza en la casilla(en su caso)
                if(tablero[i][TAM_TABLERO-j-1] != null){
                    g2d.drawImage(tablero[i][TAM_TABLERO-j-1].getSprite(), i*60-1, j*60-1, null);
                }
            }
        }
    }

    public void setControlador(Controlador controlador) {
        addMouseListener(controlador);
    }

    private void setInitialPosicion(){
        for(int i = 0 ; i < TAM_TABLERO; i++){
            tablero[i][1] = new Pieza( true, TipoPieza.Peon);
            tablero[i][6] = new Pieza(false, TipoPieza.Peon);
        }
        tablero[0][0] = new Pieza(true, TipoPieza.Torre);
        tablero[7][0] = new Pieza(true, TipoPieza.Torre);
        tablero[0][7] = new Pieza(false, TipoPieza.Torre);
        tablero[7][7] = new Pieza(false, TipoPieza.Torre);
        tablero[1][0] = new Pieza(true, TipoPieza.Caballo);
        tablero[6][0] = new Pieza(true, TipoPieza.Caballo);
        tablero[1][7] = new Pieza(false, TipoPieza.Caballo);
        tablero[6][7] = new Pieza(false, TipoPieza.Caballo);
        tablero[2][0] = new Pieza(true, TipoPieza.Alfil);
        tablero[5][0] = new Pieza(true, TipoPieza.Alfil);
        tablero[2][7] = new Pieza(false, TipoPieza.Alfil);
        tablero[5][7] = new Pieza(false, TipoPieza.Alfil);
        tablero[3][0] = new Pieza(true, TipoPieza.Dama);
        tablero[4][0] = new Pieza(true, TipoPieza.Rey);
        tablero[3][7] = new Pieza(false, TipoPieza.Dama);
        tablero[4][7] = new Pieza(false, TipoPieza.Rey);

    }

    public void setCasillaMarcada(int x, int y) {
        if(turno && (x >= 0 && y >= 0) && tablero[x][y] != null && (tablero[x][y].isBlancas() == color)){
            xMarcada = x;
            yMarcada = y;
            repaint();
        }
    }

    public Movimiento moverPieza(int x, int y) {
        //Esta funcion es usada a nivel local
        Movimiento movimiento = null;
        //Comprueba si es una posicion válida y que no es una pieza propia
        if(turno && (xMarcada>=0 && yMarcada>=0) && (x >= 0 && y >= 0 && x < TAM_TABLERO && y < TAM_TABLERO)  &&
                (tablero[x][y] == null || tablero[x][y].isBlancas() != tablero[xMarcada][yMarcada].isBlancas())){
            movimiento = new Movimiento(xMarcada, yMarcada, x, y);
            tablero[x][y] = tablero[xMarcada][yMarcada];
            tablero[xMarcada][yMarcada] = null;
            turno = !turno;
        }
        xMarcada = -1;
        yMarcada = -1;
        repaint();
        return movimiento;
    }

    public void moverPieza(int x, int y, int xDes, int yDes) {
        //Esta funcion es usada a nivel de red
        //Comprueba si es una posicion válida y que no es una pieza propia
        if((xDes >= 0 && yDes >= 0 && xDes < TAM_TABLERO && yDes < TAM_TABLERO)  &&
                (tablero[xDes][yDes] == null || tablero[x][y].isBlancas() != tablero[xDes][yDes].isBlancas())){
            tablero[xDes][yDes] = tablero[x][y];
            tablero[x][y] = null;
            turno = !turno;
        }
        xMarcada = -1;
        yMarcada = -1;
        repaint();
    }

    public int getDimension() {
        return TAM_TABLERO*TAM_CASILLA;
    }

    public void restart(){
        turno = false;
        for (int i = 0; i < TAM_TABLERO; i++){
            for(int j = 0; j < TAM_TABLERO; j++){
                tablero[i][j] = null;
            }
        }
        setInitialPosicion();
        repaint();
    }

}
