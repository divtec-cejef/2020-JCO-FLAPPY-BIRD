package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Classe qui représente un Sprite
 */
public class Sprite extends Rectangle {

    private Area area = new Area();
    public int largeur = 0;
    public int hauteur = 0;

    /**
     * Crée et instantie un Sprite
     *
     * @param x     coordonnée X
     * @param y     coordonnée Y
     * @param w     Largeur
     * @param h     Hauteur
     * @param color Couleur
     */
    Sprite(int x, int y, int w, int h, Color color) {
        super(w, h, color);

        setTranslateX(x);
        setTranslateY(y);

        //simplification de la variable, remplace getWidth/getHeight
        largeur = w;
        hauteur = h;

        //Initialisation des coins
        //area.setTopLeft(new CoordXY(w / 2 * -1, h / 2 * -1));
        //area.setTopRight(new CoordXY(w / 2, h / 2 * -1));
        //area.setDownLeft(new CoordXY(w / 2 * -1, h / 2));
        //area.setDownRight(new CoordXY(w / 2, h / 2));

        //Mise à jours des coins
        refreshCoord();
    }

    /**
     * Déplace le sprite vers la guauche
     *
     * @param speed distance entre le point de départ et de fin
     */
    void moveLeft(int speed) {
        setTranslateX(getTranslateX() - speed);
        //Mise à jours des coordonnées des quatre coins
        refreshCoord();
    }

    /**
     * Déplace le sprite vers la droite
     *
     * @param speed distance entre le point de départ et de fin
     */
    void moveRight(int speed) {
        setTranslateX(getTranslateX() + speed);
        //Mise à jours des coordonnées des quatre coins
        refreshCoord();
    }

    /**
     * Déplace le sprite vers le haut
     *
     * @param speed distance entre le point de départ et de fin
     */
    void moveUp(int speed) {
        setTranslateY(getTranslateY() - speed);
        //Mise à jours des coordonnées des quatre coins
        refreshCoord();
    }

    /**
     * Déplace le sprite vers le bas
     *
     * @param speed distance entre le point de départ et de fin
     */
    void moveDown(int speed) {
        setTranslateY(getTranslateY() + speed);
        //Mise à jours des coordonnées des quatre coins
        refreshCoord();
    }

    /**
     * met à jours les coordonnée des quatre coins du Sprite
     */
    private void refreshCoord() {
        //Y
        area.getTopLeft().setY((int) getTranslateY()  - (hauteur / 2));
        area.getTopRight().setY((int) getTranslateY()  - (hauteur / 2));
        area.getDownLeft().setY((int) getTranslateY()  + (hauteur / 2));
        area.getDownRight().setY((int) getTranslateY()  + (hauteur / 2));

        //X
        area.getTopLeft().setX((int) getTranslateX() - (this.largeur / 2));
        area.getTopRight().setX((int) getTranslateX() + (this.largeur / 2));
        area.getDownLeft().setX((int) getTranslateX() - (this.largeur / 2));
        area.getDownRight().setX((int) getTranslateX() + (this.largeur / 2));

    }

    public Area getArea() {
        return area;
    }

}