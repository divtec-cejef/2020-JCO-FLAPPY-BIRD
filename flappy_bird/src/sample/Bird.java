package sample;


import javax.swing.*;

import javafx.scene.image.Image;
import java.awt.*;
import java.util.concurrent.TimeUnit;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Classe qui représente un Oiseau
 */
public class Bird extends Sprite {
    private boolean isAlive;
    private ImageView birdSprite;
    private Image birdImage;

    /**
     * Crée et instantie un oiseau
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
        birdImage = new Image("Sprites/flappy.png");
        birdSprite = new ImageView(birdImage);
    }

    /**
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

    /**
     * fait subir à l'oiseau une force de gravité, ce qui le poussera a tomber en continu
     * plus la gravité est élevée, plus il tombera vite
     *
     * @param gravity force de gravité
     */
    public void undergoGravity(int gravity) {
        this.moveDown(gravity);
        refreshBirdSprite();
    }

    /**
     * L'oiseau monte l'axe Y en fonction de sa force
     *
     * @param strengh force de l'oiseau, plus elle est haute, plus il montera haut
     */
    public void flap(int strengh) {
        this.setTranslateY(this.getTranslateY() - strengh);
        refreshBirdSprite();
    }

    public ImageView getBirdSprite() {
        return birdSprite;
    }

    public void refreshBirdSprite(){
        birdSprite.setTranslateX(this.getTranslateX());
        birdSprite.setTranslateY(this.getTranslateY());
    }
}


