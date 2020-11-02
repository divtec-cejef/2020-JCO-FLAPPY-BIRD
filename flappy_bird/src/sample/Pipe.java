package sample;

import javafx.scene.paint.Color;

import java.awt.*;

/**
 * Classe qui représente un tuyau
 */
public class Pipe extends Sprite {

    /**
     * Crée et instantie un tuyau
     *
     * @param x     coordonée X
     * @param y     coordonnée Y
     * @param w     Largeur
     * @param h     Hauteur
     * @param color Couleur
     */
    Pipe(int x, int y, int w, int h, Color color) {
        super(x, y, w, h, color);

    }

    /**
     * Check si le tuyeau se fait toucher par un oiseau
     *
     * @param oiseau qu'on surveille
     * @return true = l'oiseau a touché / false l'oiseau n'a pas touché
     */
    public boolean isHit(Oiseau oiseau) {
        boolean isTouched = false;
        if (oiseau.getArea().getTopRight().getX() < this.getArea().getDownRight().getX()
                && oiseau.getArea().getTopRight().getX() > this.getArea().getDownLeft().getX()
                && oiseau.getArea().getTopRight().getY() < this.getArea().getDownLeft().getY()
                && oiseau.getArea().getTopRight().getY() > this.getArea().getTopLeft().getY()) {
            isTouched = true;
        }
        return isTouched;
    }


}
