package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Classe qui représente un Sprite
 */
public class Sprite extends Rectangle {

    /**
     * Créer et instantie un Sprite
     * @param x coordonnée X
     * @param y coordonnée Y
     * @param w Largeur
     * @param h Hauteur
     * @param color Couleur
     */
    Sprite(int x, int y, int w, int h, Color color) {
        super(w, h, color);

        setTranslateX(x);
        setTranslateY(y);
    }

    /**
     * Déplace le sprite vers la guauche
     * @param speed distance entre le point de départ et de fin
     */
    void moveLeft(int speed) {
        setTranslateX(getTranslateX() - speed);
    }
    /**
     * Déplace le sprite vers la droite
     * @param speed distance entre le point de départ et de fin
     */
    void moveRight(int speed) {
        setTranslateX(getTranslateX() + speed);
    }
    /**
     * Déplace le sprite vers le haut
     * @param speed distance entre le point de départ et de fin
     */
    void moveUp(int speed) {
        setTranslateY(getTranslateY() - speed);
    }
    /**
     * Déplace le sprite vers le bas
     * @param speed distance entre le point de départ et de fin
     */
    void moveDown(int speed) {
        setTranslateY(getTranslateY() + speed);
    }
}