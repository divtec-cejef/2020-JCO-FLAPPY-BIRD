package flappyBird;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.sql.Statement;
import java.util.ArrayList;

import static flappyBird.Constant.PATH_DIR_SPRITES;

public class Asteroid extends Shape {
    boolean isAlive = true;
    double lifeLine = 8;
    ImageView asteroidSprite;

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
    Asteroid(int x, int y, int w, int h, Color color, StackPane stackpane, String spritePath) {
        super(x, y, w, h, color, stackpane, spritePath);

        setSprite(pickImage(getRandomNumber(1,4)));
        getSprite().setFitHeight(h);
        getSprite().setFitWidth(w);
        asteroidSprite = getSprite();
    }

    private String pickImage(int numeroImage) {
        return PATH_DIR_SPRITES + "ennemy" + numeroImage + ".png";
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
     * Observe si l'astéroide à atteint le côté gauche
     *
     * @return true : est arrivé / False : n'est pas arrivé
     */
    private boolean isOut() {
        return this.getTranslateX() < -600;
    }

    public void checkBounds() {
        if (isOut()) {
            getStackpane().getChildren().remove(getAsteroidSprite());
            getStackpane().getChildren().remove(this);
        }
    }

    public static void killThemAll(ArrayList<Asteroid> list, StackPane stackpane) {
        for (Asteroid asteroid : list) {
            stackpane.getChildren().remove(asteroid.getAsteroidSprite());
            stackpane.getChildren().remove(asteroid);
        }
    }

    @Override
    void moveLeft(int speed) {
        super.moveLeft(speed);
        refreshSprite();
        refreshCoord();
    }

    public ImageView getAsteroidSprite() {
        return asteroidSprite;
    }


}
