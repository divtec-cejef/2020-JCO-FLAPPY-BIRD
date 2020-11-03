package sample;

import javafx.scene.paint.Color;

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
     * @param bird qu'on surveille
     * @return true = l'oiseau a touché / false l'oiseau n'a pas touché
     */
    public boolean isHit(Bird bird) {
        boolean isTouched = false;
        if (
            //topRight de l'oiseau
                bird.getArea().getTopRight().getX() < this.getArea().getDownRight().getX()
                        && bird.getArea().getTopRight().getX() > this.getArea().getDownLeft().getX()
                        && bird.getArea().getTopRight().getY() < this.getArea().getDownLeft().getY()
                        && bird.getArea().getTopRight().getY() > this.getArea().getTopLeft().getY()
                        //topLeft de l'oiseau
                        || bird.getArea().getTopLeft().getX() < this.getArea().getDownRight().getX()
                        && bird.getArea().getTopLeft().getX() > this.getArea().getDownLeft().getX()
                        && bird.getArea().getTopLeft().getY() < this.getArea().getDownLeft().getY()
                        && bird.getArea().getTopLeft().getY() > this.getArea().getTopLeft().getY()
                        //downRight de l'oiseau
                        || bird.getArea().getDownRight().getX() < this.getArea().getDownRight().getX()
                        && bird.getArea().getDownRight().getX() > this.getArea().getDownLeft().getX()
                        && bird.getArea().getDownRight().getY() < this.getArea().getDownLeft().getY()
                        && bird.getArea().getDownRight().getY() > this.getArea().getTopLeft().getY()
                        //downLeft de l'oiseau
                        || bird.getArea().getDownLeft().getX() < this.getArea().getDownRight().getX()
                        && bird.getArea().getDownLeft().getX() > this.getArea().getDownLeft().getX()
                        && bird.getArea().getDownLeft().getY() < this.getArea().getDownLeft().getY()
                        && bird.getArea().getDownLeft().getY() > this.getArea().getTopLeft().getY()
        ) {
            isTouched = true;
        }
        return isTouched;
    }


}
