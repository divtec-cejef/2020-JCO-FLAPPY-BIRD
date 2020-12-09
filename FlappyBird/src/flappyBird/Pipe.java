package flappyBird;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import static flappyBird.Constant.*;

/**
 * Classe qui représente un tuyau
 *
 * @author Louis Bovay
 */
public class Pipe extends Shape {

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
    Pipe(int x, int y, int w, int h, Color color, StackPane stackpane, String spritePath) {
        super(x, y, w, h, color, stackpane, spritePath);
    }

}
