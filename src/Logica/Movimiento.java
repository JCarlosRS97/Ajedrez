package Logica;

public class Movimiento {
    private int x, xOri;
    private int y, yOri;

    public Movimiento(int xOri, int yOri, int x, int y){
        this.x = x;
        this.xOri = xOri;
        this.y = y;
        this.yOri = yOri;
    }

    public int getxOri() {
        return xOri;
    }

    public void setxOri(int xOri) {
        this.xOri = xOri;
    }

    public int getyOri() {
        return yOri;
    }

    public void setyOri(int yOri) {
        this.yOri = yOri;
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

    @Override
    public String toString() {
        return new StringBuilder().append(xOri).append(" ").append(yOri).append(" ").append(x).append(" ").append(y).toString();
    }
}
