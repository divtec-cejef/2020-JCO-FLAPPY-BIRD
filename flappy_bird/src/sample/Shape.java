package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Classe qui représente une Shape (forme)
 *
 * @author Louis Bovay
 */
public class Shape extends Rectangle {

    private Area area = new Area();
    public int largeur = 0;
    public int hauteur = 0;

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

        //simplification de la variable, remplace getWidth/getHeight
        largeur = w;
        hauteur = h;

        //Mise à jours des coins
        refreshCoord();
    }

    /**
     * Déplace la Shape vers la guauche
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
        area.getTopLeft().setY((int) getTranslateY() - (hauteur / 2));
        area.getTopRight().setY((int) getTranslateY() - (hauteur / 2));
        area.getDownLeft().setY((int) getTranslateY() + (hauteur / 2));
        area.getDownRight().setY((int) getTranslateY() + (hauteur / 2));

        //X
        area.getTopLeft().setX((int) getTranslateX() - (this.largeur / 2));
        area.getTopRight().setX((int) getTranslateX() + (this.largeur / 2));
        area.getDownLeft().setX((int) getTranslateX() - (this.largeur / 2));
        area.getDownRight().setX((int) getTranslateX() + (this.largeur / 2));

    }

    /**
     * Permet d'accéder à area
     *
     * @return area
     */
    public Area getArea() {
        return area;
    }

}