package sample;

public class CoordXY {
    private int X = 0;
    private int Y = 0;

    public CoordXY(){

    }

    public CoordXY(int px, int py){
        this.X = px;
        this.Y = py;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }
}
