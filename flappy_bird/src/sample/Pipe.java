package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Classe qui représente un tuyau
 */
public class Pipe extends Sprite {

    //sprite du tuyau
    private ImageView pipeSprite;
    private Image pipeImage;

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
        pipeImage = new Image("Sprites/longpipe.png");
        pipeSprite = new ImageView(pipeImage);
    }

    /**
     * Check si le tuyeau se fait toucher par un oiseau
     * pour ça on regarde si au moins un coin de l'oiseau se trouve entre le coin haut-gauche et bas-gauche
     * du tuyau ET S'il se trouvent entre le coins bas-gauche et bas-droit du tuyau.
     *
     * @param bird oiseau qu'on surveille
     * @return true = l'oiseau a touché / false l'oiseau n'a pas touché
     */
    public boolean isHit(Bird bird) {
        boolean isTouched = false;
        if (
            //Haut-droite de l'oiseau
                bird.getArea().getTopRight().getX() < this.getArea().getDownRight().getX()
                        && bird.getArea().getTopRight().getX() > this.getArea().getDownLeft().getX()
                        && bird.getArea().getTopRight().getY() < this.getArea().getDownLeft().getY()
                        && bird.getArea().getTopRight().getY() > this.getArea().getTopLeft().getY()
                        //Haut-gauche de l'oiseau
                        || bird.getArea().getTopLeft().getX() < this.getArea().getDownRight().getX()
                        && bird.getArea().getTopLeft().getX() > this.getArea().getDownLeft().getX()
                        && bird.getArea().getTopLeft().getY() < this.getArea().getDownLeft().getY()
                        && bird.getArea().getTopLeft().getY() > this.getArea().getTopLeft().getY()
                        //Bas-gauche de l'oiseau
                        || bird.getArea().getDownRight().getX() < this.getArea().getDownRight().getX()
                        && bird.getArea().getDownRight().getX() > this.getArea().getDownLeft().getX()
                        && bird.getArea().getDownRight().getY() < this.getArea().getDownLeft().getY()
                        && bird.getArea().getDownRight().getY() > this.getArea().getTopLeft().getY()
                        //Bas-droit de l'oiseau
                        || bird.getArea().getDownLeft().getX() < this.getArea().getDownRight().getX()
                        && bird.getArea().getDownLeft().getX() > this.getArea().getDownLeft().getX()
                        && bird.getArea().getDownLeft().getY() < this.getArea().getDownLeft().getY()
                        && bird.getArea().getDownLeft().getY() > this.getArea().getTopLeft().getY()
        ) {
            isTouched = true;
        }
        return isTouched;
    }

    /**
     * Replace le spritre du tyau sur le tuyau
     */
    public void refreshPipeSprite() {
        pipeSprite.setTranslateX(this.getTranslateX());
        pipeSprite.setTranslateY(this.getTranslateY());
    }

    /**
     * Permet d'accéder au sprite du tuyau (utile pour le mettre dans la scène)
     *
     * @return le sprite du tuyau
     */
    public ImageView getPipeSprite() {
        return pipeSprite;
    }

}
