package flappyBird;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import static flappyBird.Constant.*;

/**
 * Classe qui représente un projectil
 *
 * @author Louis Bovay
 */
public class Projectile extends Shape {

    private int lifeTime = 60;
    private boolean isDead = false;
    private double currentSize;

    /**
     * Crée et instantie une Shape à la position, la taille et la couleur voulue
     *
     * @param x          coordonnée X
     * @param y          coordonnée Y
     * @param w          Largeur
     * @param h          Hauteur
     * @param color      Couleur
     * @param stackpane  StackPane ou sera stocké l'objet
     * @param spritePath chemin d'accès vers le sprite de l'objet
     */
    Projectile(int x, int y, int w, int h, Color color, StackPane stackpane, String spritePath) {
        super(x, y, w, h, color, stackpane, spritePath);

        currentSize = PROJECTILE_SIZE;
        getSprite().setFitWidth(currentSize);
        getSprite().setFitHeight(currentSize);

    }

    @Override
    void moveRight(int speed) {
        super.moveRight(speed);
        refreshSprite();
        refreshCoord();
    }

    /**
     * La balle se déplace
     *
     * @param speed vitesse de déplacement du projectile
     */
    public void travel(int speed) {
        if (lifeTime > 0) {
            lifeTime -=1;
        } else {
            kill();
        }
        moveRight(speed);
    }

    private void kill() {
        if(currentSize < 1){
            isDead = true;
            this.delete();
        }else{
            shrink();
        }
    }

    public boolean isDead() {
        return isDead;
    }

    //Fait rapetissir le projectil, puis disparaît
    private void shrink(){
        getSprite().setFitWidth(currentSize);
        getSprite().setFitHeight(currentSize);
        this.setWidth(currentSize);
        this.setHeight(currentSize);
        currentSize -= 1;
    }
}
