package flappyBird;

/**
 * Classe représentant quatre coins d'un rectangle quelconque
 *
 * @author Louis Bovay
 */
public class Area {
    private CoordXY topLeft = new CoordXY();
    private CoordXY topRight = new CoordXY();
    private CoordXY downLeft = new CoordXY();
    private CoordXY downRight = new CoordXY();

    public Area() {
    }

    /**
     * Constructeur permettant de directement assigner les quatres coins d'un rectangle
     *
     * @param topLeft   coin haut-gauche
     * @param topRight  coin haut-droite
     * @param downLeft  coin bas-gauche
     * @param downRight coin bas-droite
     */
    public Area(CoordXY topLeft, CoordXY topRight, CoordXY downLeft, CoordXY downRight) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.downLeft = downLeft;
        this.downRight = downRight;
    }

    /**
     * Répcupérer la coordonnéeXY de TopLeft
     *
     * @return le coin haut gauche
     */
    public CoordXY getTopLeft() {
        return topLeft;
    }

    /**
     * Assigné une nouvelle valeur à topLeft
     *
     * @param topLeft coin bas gauche
     */
    public void setTopLeft(CoordXY topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * Répcupérer la coordonnéeXY de topRight
     *
     * @return le coin haut droit
     */
    public CoordXY getTopRight() {
        return topRight;
    }

    /**
     * Assigner une nouvelle valeur à topRight
     *
     * @param topRight coin haut droite
     */
    public void setTopRight(CoordXY topRight) {
        this.topRight = topRight;
    }

    /**
     * Répcupérer la coordonnéeXY de  DownLeft
     *
     * @return le coin bas gauche
     */
    public CoordXY getDownLeft() {
        return downLeft;
    }

    /**
     * Assigner une nouvelle valeur à downLEft
     *
     * @param downLeft coin bad gauche
     */
    public void setDownLeft(CoordXY downLeft) {
        this.downLeft = downLeft;
    }

    /**
     * Répcupérer la coordonnéeXY de DownRight
     *
     * @return le coin bas droit
     */
    public CoordXY getDownRight() {
        return downRight;
    }

    /**
     * Assigner une nouvelle valeur à downRight
     *
     * @param downRight coin bas droit
     */
    public void setDownRight(CoordXY downRight) {
        this.downRight = downRight;
    }

    /**
     * Check si l'area d'un objet touche l'aera d'un autre objet
     *
     * @param area1  Area de l'oiseau qu'on surveille
     * @param area2 autre Area qu'on suvreille
     * @return true = area1 à touché area2 / false area1 n'a pas touché area2
     */
    public static boolean isHit(Area area1, Area area2) {
        boolean isTouched = false;
        if (
            //Haut-droite de area1
                area1.getTopRight().getX() < area2.getDownRight().getX()
                        && area1.getTopRight().getX() > area2.getDownLeft().getX()
                        && area1.getTopRight().getY() < area2.getDownLeft().getY()
                        && area1.getTopRight().getY() > area2.getTopLeft().getY()
                        //Haut-gauche de area1
                        || area1.getTopLeft().getX() < area2.getDownRight().getX()
                        && area1.getTopLeft().getX() > area2.getDownLeft().getX()
                        && area1.getTopLeft().getY() < area2.getDownLeft().getY()
                        && area1.getTopLeft().getY() > area2.getTopLeft().getY()
                        //Bas-gauche de area1
                        || area1.getDownRight().getX() < area2.getDownRight().getX()
                        && area1.getDownRight().getX() > area2.getDownLeft().getX()
                        && area1.getDownRight().getY() < area2.getDownLeft().getY()
                        && area1.getDownRight().getY() > area2.getTopLeft().getY()
                        //Bas-droit de area1
                        || area1.getDownLeft().getX() < area2.getDownRight().getX()
                        && area1.getDownLeft().getX() > area2.getDownLeft().getX()
                        && area1.getDownLeft().getY() < area2.getDownLeft().getY()
                        && area1.getDownLeft().getY() > area2.getTopLeft().getY()
        ) {
            isTouched = true;
        }
        return isTouched;
    }
}
