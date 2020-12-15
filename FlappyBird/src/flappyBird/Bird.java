package flappyBird;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


/**
 * Classe qui représente un Oiseau
 *
 * @author Louis Bovay
 */
public class Bird extends Shape {
    //Si l'oiseau est vivant ou mort
    private boolean isAlive = true;

    //Définit si l'oiseau est en train de volé ou non
    private boolean flying = false;
    //Elan de l'oiseau (vitesse de pointe de son vole)
    private float momentum = 19.5f;
    private float lossMomentum = 0.5f;

    /**
     * Crée et instantie une Shape à la position, la taille et la couleur voulue
     *
     * @param x          coordonnée X
     * @param y          coordonnée Y
     * @param w          Largeur
     * @param h          Hauteur
     * @param color      Couleur
     * @param stackpane  StackPane
     * @param spritePath Chemin du sprite
     */
    Bird(int x, int y, int w, int h, Color color, StackPane stackpane, String spritePath) {
        super(x, y, w, h, color, stackpane, spritePath);
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
        if (getSprite().getRotate() < 80) {
            getSprite().setRotate(getSprite().getRotate() + 5);
        }
    }

    /**
     * L'oiseau entâme un mouvement souple de saut et subit à nouveau la gravité petit à petit
     */
    public void smoothFlap() {
        if (momentum > 0) {
            this.moveUp((int) momentum);
            momentum -= lossMomentum;
            getSprite().setRotate(-20);
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

    public void setLossMomentum(float lossMomentum) {
        this.lossMomentum = lossMomentum;
    }
}


