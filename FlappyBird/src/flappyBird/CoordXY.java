package flappyBird;

/**
 * Classe qui représente une coordonnée X Y
 *
 * @author Louis Bovay
 */
public class CoordXY {
    private int X = 0;
    private int Y = 0;

    public CoordXY() {

    }

    /**
     * Constructeur permettant de directement assigner une valeur X et Y
     *
     * @param px point X
     * @param py point Y
     */
    public CoordXY(int px, int py) {
        this.X = px;
        this.Y = py;
    }

    /**
     * Récupérer X
     *
     * @return le point X
     */
    public int getX() {
        return X;
    }

    /**
     * Assigné une nouvelle valeur à X
     *
     * @param x nouveau point X
     */
    public void setX(int x) {
        X = x;
    }

    /**
     * Récupérer Y
     *
     * @return le point Y
     */
    public int getY() {
        return Y;
    }

    /**
     * Assigné une nouvelle valeur à Y
     *
     * @param y nouveau point Y
     */
    public void setY(int y) {
        Y = y;
    }
}
