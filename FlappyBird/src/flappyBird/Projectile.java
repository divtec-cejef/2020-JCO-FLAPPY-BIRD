package flappyBird;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import static flappyBird.Constant.*;

public class Projectile extends Shape{

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
    }

    @Override
    void moveRight(int speed) {
        super.moveRight(speed);
        refreshSprite();
        refreshCoord();
    }

    /**
     * La balle se déplace
     * @param currentLifeTime
     * @param speed
     */
    public void travel(int currentLifeTime,int speed){
        if(currentLifeTime < PROJECTILE_LIFE_TIME){
            moveRight(speed);
        }else{
            getStackpane().getChildren().remove(getSprite());
            getStackpane().getChildren().remove(this);
        }
    }
}
