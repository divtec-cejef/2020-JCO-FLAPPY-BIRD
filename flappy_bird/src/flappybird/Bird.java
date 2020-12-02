package flappybird;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import static flappybird.Constant.*;

/**
 * Classe qui représente un Oiseau
 *
 * @author Louis Bovay
 */
public class Bird extends Shape {
    //Si l'oiseau est vivant ou mort
    private boolean isAlive;
    //Sprite de la classe Bird
    private ImageView birdSprite;
    private Image birdImage;

    //Définit si l'oiseau est en train de volé ou non
    private boolean flying = false;
    //Elan de l'oiseau (vitesse de pointe de son vole)
    private float momentum = 20;

    /**
     * Crée et instantie un oiseau à la position, la taille et la couleur voulue
     * Un sprite et une vie lui sont automatiquement assigné
     *
     * @param x     coordonnée X
     * @param y     coordonnée Y
     * @param w     Largeur
     * @param h     Hauteur
     * @param color Couleur
     */
    public Bird(int x, int y, int w, int h, Color color) {
        super(x, y, w, h, color);
        this.isAlive = true;
        birdImage = new Image(PATH_DIR_SPRITES + "flappy.png");
        birdSprite = new ImageView(birdImage);
    }

    /**
     * Permet d'accéder à l'état de santé de l'oiseau
     *
     * @return l'état de santé de l'oiseau (true = vivant, false = mort)
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Tue l'oiseau
     */
    public void kill() {
        this.isAlive = false;
    }

    public void revive() {
        this.isAlive = true;
    }

    /**
     * fait subir à l'oiseau une force de gravité, ce qui le poussera a tomber en continu
     * plus la gravité est élevée, plus il tombera vite
     *
     * @param gravity force de gravité
     */
    public void undergoGravity(int gravity) {
        this.moveDown(gravity);
        if (birdSprite.getRotate() < 80) {
            birdSprite.setRotate(birdSprite.getRotate() + 5);
        }
        refreshBirdSprite();
    }

    /**
     * Permet d'accéder au sprite
     *
     * @return birdSprite
     */
    public ImageView getBirdSprite() {
        return birdSprite;
    }

    /**
     * Assigne la position X et Y du sprite à la position X et Y de l'objet
     */
    public void refreshBirdSprite() {
        birdSprite.setTranslateX(this.getTranslateX());
        birdSprite.setTranslateY(this.getTranslateY());
    }

    /**
     * L'oiseau entâme un mouvement souple de saut et subit à nouveau la gravité petit à petit
     */
    public void smoothFlap() {
        if (momentum > 0) {
            this.moveUp((int) momentum);
            momentum -= 0.5;
            birdSprite.setRotate(-20);
            refreshBirdSprite();
        } else {
            flying = false;
        }
    }

    /**
     * Donne accès à la variable flying
     *
     * @return flying
     */
    public boolean isFlying() {
        return flying;
    }

    /**
     * Change l'état de flying
     *
     * @param flying (true = l'oiseau vole, false = l'oiseau tombe)
     */
    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    public void setMomentum(float momentum) {
        this.momentum = momentum;
    }
}


