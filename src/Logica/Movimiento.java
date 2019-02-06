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

    @Override
    public String toString() {
        return new StringBuilder().append(xOri).append(" ").append(yOri).append(" ").append(x).append(" ").append(y).toString();
    }
}
