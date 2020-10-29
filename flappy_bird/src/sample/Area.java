package sample;

//Classe représentant quatre coins d'un rectangle quelconque
public class Area {
    private CoordXY topLeft = new CoordXY();
    private CoordXY topRight = new CoordXY();
    private CoordXY downLeft = new CoordXY();
    private CoordXY downRight = new CoordXY();

    public Area(){}

    public Area(CoordXY topLeft, CoordXY topRight, CoordXY downLeft, CoordXY downRight) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.downLeft = downLeft;
        this.downRight = downRight;
    }

    public CoordXY getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(CoordXY topLeft) {
        this.topLeft = topLeft;
    }

    public CoordXY getTopRight() {
        return topRight;
    }

    public void setTopRight(CoordXY topRight) {
        this.topRight = topRight;
    }

    public CoordXY getDownLeft() {
        return downLeft;
    }

    public void setDownLeft(CoordXY downLeft) {
        this.downLeft = downLeft;
    }

    public CoordXY getDownRight() {
        return downRight;
    }

    public void setDownRight(CoordXY downRight) {
        this.downRight = downRight;
    }
}
