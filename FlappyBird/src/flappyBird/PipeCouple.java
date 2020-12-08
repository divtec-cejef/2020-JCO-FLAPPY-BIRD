package flappyBird;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * Classe qui représente un couple de tuyau
 *
 * @author Louis Bovay
 */
public class PipeCouple {
    public flappyBird.Pipe topPipe;
    public flappyBird.Pipe bottomPipe;
    private boolean canGivePts = true;
    private final double VERTICAL_RANGE_EXPAND = 100;
    private final double VERTICAL_RANGE_SHRINK = 20;
    private final double MAX_HEIGHT = 700;

    private boolean goDown = false;
    private boolean goUp = true;
    private boolean areMoving = false;

    /**
     * Crée et instantie un couple de tuyau
     *
     * @param topPipe    tuyau du haut
     * @param bottomPipe tuyau du bas
     */
    public PipeCouple(flappyBird.Pipe topPipe, flappyBird.Pipe bottomPipe) {
        this.topPipe = topPipe;
        this.bottomPipe = bottomPipe;

        this.topPipe.setWidth(60);
        this.topPipe.setHeight(700);

        this.bottomPipe.setWidth(60);
        this.bottomPipe.setHeight(700);

        formatCouples();
    }

    /**
     * Génère un nombre aléatoire entre une range donnée
     *
     * @param min range inférieure
     * @param max range supérieure
     * @return un nombre aléatoire
     */
    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * Crée un espace égale à la valeur de spaceBetween entre deux tuyaux
     */
    private void createSpace() {
        //Espace fixe entre le couple de tuyaux
        int spaceBetween = 200;
        // décalage automatique des tuyaux
        int rndNum = getRandomNumber(-210, 210);
        //Décale le tuyau du haut
        this.topPipe.setTranslateY(this.topPipe.getTranslateY() + rndNum - spaceBetween / 2);
        //Décale le tuyau du bas
        this.bottomPipe.setTranslateY(this.bottomPipe.getTranslateY() + rndNum + spaceBetween / 2);
        //Replacer les sprites sur les tuyaux
        this.topPipe.refreshPipeSprite();
        this.bottomPipe.refreshPipeSprite();
    }

    /**
     * Fait bouger le couple de droite à gauche, si arrivé a gauche, il se réinitialise
     *
     * @param speed Vitesse des tuyaux
     */
    public void move(int speed) {
        //Arrivé
        if (isOut()) {
            //Couple réinitialisé
            formatCouples();
        }
        //Pas arrivé
        else {
            this.topPipe.moveLeft(speed);
            this.topPipe.refreshPipeSprite();
            this.bottomPipe.moveLeft(speed);
            this.bottomPipe.refreshPipeSprite();
        }
    }

    /**
     * Observe si le couple à atteint de côté gauche
     *
     * @return true : est arrivé / False : n'est pas arrivé
     */
    private boolean isOut() {
        return this.topPipe.getTranslateX() < -600;
    }

    /**
     * Réinitialise le couple, le replaçant à droit de l'écran et en recréant un espace entre les deux tuyaux
     */
    public void formatCouples() {
        //tuyau du haut
        this.topPipe.setTranslateX(550);
        this.topPipe.setTranslateY(-350);

        //tuyau du bas
        this.bottomPipe.setTranslateX(550);
        this.bottomPipe.setTranslateY(350);

        //Ils peuvent à nouveau donner des points
        this.canGivePts = true;

        // Générer l'espace aléatoire entre les deux tuyaux
        createSpace();
        areMoving = setAreMoving();
    }

    /**
     * Permet de connaître si oui on non un couple de tuyau peuvent donner des points
     *
     * @return true = le couple peut donner des points, false = ne peuvent pas donner de points
     */
    public boolean CanGivePts() {
        return canGivePts;
    }

    /**
     * Définir si oui ou non un couple peut donner des points
     *
     * @param canGivePts true = le couple peut donner des points, false = il ne peut pas
     */
    public void setCanGivePts(boolean canGivePts) {
        this.canGivePts = canGivePts;
    }


    /**
     * Les tuyaux font des aller-retour verticaux
     */
    public void verticalPipeMove() {
        if (this.topPipe.getArea().getDownLeft().getY() < -280) {
            goDown = true;
            goUp = false;
        }
        if (this.bottomPipe.getArea().getTopLeft().getY() > 280) {
            goDown = false;
            goUp = true;
        }

        if (goDown) {
            this.topPipe.moveDown(1);
            this.bottomPipe.moveDown(1);
        }
        if (goUp) {
            this.topPipe.moveUp(1);
            this.bottomPipe.moveUp(1);
        }
    }

    /**
     * Décide si oui ou non les tuyaux bougeront
     * @return 2 = ils bougeront
     */
    public boolean setAreMoving(){
        return getRandomNumber(1,4) == 2;
    }

    /**
     *
     * @return si le couple bouge ou non
     */
    public boolean AreMoving() {
        return areMoving;
    }
}
