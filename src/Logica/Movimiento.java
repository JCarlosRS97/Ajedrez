package Logica;

public class Movimiento {
    int x;
    int y;
    public Movimiento(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Movimiento(Movimiento movimiento){
        x = movimiento.getX();
        y = movimiento.getY();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
