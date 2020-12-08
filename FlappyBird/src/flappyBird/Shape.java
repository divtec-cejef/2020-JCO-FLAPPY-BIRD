package flappyBird;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Classe qui représente une Shape (forme)
 *
 * @author Louis Bovay
 */
public class Shape extends Rectangle {

    private flappyBird.Area area = new flappyBird.Area();

    /**
     * Crée et instantie une Shape à la position, la taille et la couleur voulue
     *
     * @param x     coordonnée X
     * @param y     coordonnée Y
     * @param w     Largeur
     * @param h     Hauteur
     * @param color Couleur
     */
    Shape(int x, int y, int w, int h, Color color) {
        super(w, h, color);

        setTranslateX(x);
        setTranslateY(y);

        //Mise à jours des coins
        refreshCoord();
    }

    /**
     * Déplace la Shape vers la gauche
     *
     * @param speed distance entre le point de départ et de fin
     */
    void moveLeft(int speed) {
        setTranslateX(getTranslateX() - speed);
        //Mise à jours des coordonnées des quatre coins
        refreshCoord();
    }

    /**
     * Déplace la Shape vers la droite
     *
     * @param speed distance entre le point de départ et de fin
     */
    void moveRight(int speed) {
        setTranslateX(getTranslateX() + speed);
        //Mise à jours des coordonnées des quatre coins
        refreshCoord();
    }

    /**
     * Déplace la Shape vers le haut
     *
     * @param speed distance entre le point de départ et de fin
     */
    void moveUp(int speed) {
        setTranslateY(getTranslateY() - speed);
        //Mise à jours des coordonnées des quatre coins
        refreshCoord();
    }

    /**
     * Déplace la Shape vers le bas
     *
     * @param speed distance entre le point de départ et de fin
     */
    void moveDown(int speed) {
        setTranslateY(getTranslateY() + speed);
        //Mise à jours des coordonnées des quatre coins
        refreshCoord();
    }

    /**
     * met à jours les coordonnée des quatre coins de la Shape
     */
    public void refreshCoord() {
        //Y
        area.getTopLeft().setY((int) getTranslateY() - ((int)this.getHeight() / 2));
        area.getTopRight().setY((int) getTranslateY() - ((int)this.getHeight()  / 2));
        area.getDownLeft().setY((int) getTranslateY() + ((int)this.getHeight()  / 2));
        area.getDownRight().setY((int) getTranslateY() + ((int)this.getHeight()  / 2));

        //X
        area.getTopLeft().setX((int) getTranslateX() - ((int)this.getWidth() / 2));
        area.getTopRight().setX((int) getTranslateX() + ((int)this.getWidth() / 2));
        area.getDownLeft().setX((int) getTranslateX() - ((int)this.getWidth() / 2));
        area.getDownRight().setX((int) getTranslateX() + ((int)this.getWidth() / 2));

    }

    /**
     * Permet d'accéder à area
     *
     * @return area
     */
    public flappyBird.Area getArea() {
        return area;
    }

}