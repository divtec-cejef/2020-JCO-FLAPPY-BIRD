package sample;


import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

import javafx.scene.paint.Color;

/**
 * Classe qui représente un Oiseau
 */
public class Oiseau extends Sprite {
    private boolean isAlive;
    public boolean isFlap = false;

    public Oiseau(int x, int y, int w, int h, Color color) {
        super(x, y, w, h, color);
        this.isAlive = true;
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
     */
    public void undergoGravity(int gravity) {
            this.moveDown(gravity);
    }

    public void flap(int strengh) {
        this.setTranslateY(this.getTranslateY() - strengh);
    }
}


