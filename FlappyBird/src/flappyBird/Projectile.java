package flappyBird;

import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * Classe qui représente un projectile
 *
 * @author Louis Bovay
 */
public class Projectile extends Shape {

    private int lifeTime;
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
     * @param lifeTime durée de vie du projectile
     */
    Projectile(int x, int y, int w, int h, Color color, StackPane stackpane, String spritePath, int lifeTime) {
        super(x, y, w, h, color, stackpane, spritePath);

        currentSize = w;
        getSprite().setFitWidth(currentSize);
        getSprite().setFitHeight(currentSize);
        this.lifeTime = lifeTime;

    }

    /**
     * Le projectile se déplace temps qu'il possède de la durée de vie
     *
     * @param speed vitesse de déplacement du projectile
     * @param direction direction du projectile
     */
    public void travel(int speed, Direction direction) {
        //la durée de vie diminue
        if (lifeTime > 0) {
            lifeTime -= 1;
        } else {
            kill();
        }
        if (direction == Direction.RIGHT) {
            moveRight(speed);
        } else if (direction == Direction.LEFT) {
            moveLeft(speed);
        }
        //Rotation pour donner de l'effet
        getSprite().setRotate(getSprite().getRotate() + 5);
    }

    /**
     * Le projectil meurt, ce qui le fait rapetissir et disparaître
     */
    private void kill() {
        if (currentSize >= 1) {
            shrink();
        } else {
            isDead = true;
            this.delete();
        }
    }

    /**
     * @return si le projectil est mort ou non
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Fait rapetissir le projectil ainsi que son sprite
     */
    private void shrink() {
        getSprite().setFitWidth(currentSize);
        getSprite().setFitHeight(currentSize);
        this.setWidth(currentSize);
        this.setHeight(currentSize);
        currentSize -= 1;
    }
}
