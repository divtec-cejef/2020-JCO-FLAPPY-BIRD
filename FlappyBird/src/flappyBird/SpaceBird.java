package flappyBird;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static flappyBird.Constant.*;

public class SpaceBird extends Bird {

    ArrayList<Projectile> ammo = new ArrayList<>();

    /**
     * Crée et instantie une Shape à la position, la taille et la couleur voulue
     *
     * @param x          coordonnée X
     * @param y          coordonnée Y
     * @param w          Largeur
     * @param h          Hauteur
     * @param color      Couleur
     * @param stackpane
     * @param spritePath
     */
    SpaceBird(int x, int y, int w, int h, Color color, StackPane stackpane, String spritePath) {
        super(x, y, w, h, color, stackpane, spritePath);
        revive();
    }


    public void getAmmo(){
        ammo.add(new Projectile((int)this.getTranslateX(),(int)this.getTranslateY(),68,13,Color.RED,getStackpane(),IMG_PROJECTILE));
    }

    public void shoot(){
        if(ammo!=null) {
            for (Projectile ammo : ammo) {
                ammo.travel(PROJECTILE_LIFE_TIME, PROJECTILE_SPEED);
            }
        }
    }
}
