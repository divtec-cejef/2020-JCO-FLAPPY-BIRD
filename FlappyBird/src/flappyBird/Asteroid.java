package flappyBird;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import static flappyBird.Constant.PATH_DIR_SPRITES;

public class Asteroid extends Shape{
    //sprite du tuyau
    private final ImageView asteroidSprite;
    boolean isAlive = true;
    double lifeLine = 8;
    private StackPane root;

    /**
     * Crée et instantie un tuyau à la position, la taille et la couleur voulue
     * Un sprite lui est automatiquement assigné
     *
     * @param x     coordonée X
     * @param y     coordonnée Y
     * @param w     Largeur
     * @param h     Hauteur
     * @param color Couleur
     */
    Asteroid(int x, int y, int w, int h, Color color, StackPane root) {
        super(x, y, w, h, color);
        this.root = root;
        Image asteroidImage = pickImage(getRandomNumber(1,4));
        asteroidSprite = new ImageView(asteroidImage);
        addInRoot();
        refreshAsteroidSprite();
    }

    private Image pickImage(int numeroImage){
        Image image = new Image(PATH_DIR_SPRITES + "ennemy" + numeroImage + ".png");
        System.out.println(PATH_DIR_SPRITES + "ennemy" + numeroImage + ".png");
        return image;
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

    public StackPane getRoot() {
        return root;
    }
    private void addInRoot(){
        root.getChildren().add(this);
        root.getChildren().add(this.asteroidSprite);
    }

    public static void checkBounds(Asteroid ennemy){
        if(ennemy.isOut()){
            ennemy.getRoot().getChildren().remove(ennemy);
        }
    }
    /**
     * Replace le spritre du tyau sur le tuyau
     */
    public void refreshAsteroidSprite() {
        asteroidSprite.setTranslateX(this.getTranslateX());
        asteroidSprite.setTranslateY(this.getTranslateY());
    }

    public void Rotate(){
        this.setRotate(this.getRotate() + 1);
        this.asteroidSprite.setRotate(this.asteroidSprite.getRotate() + 1);
    }

    @Override
    void moveLeft(int speed) {
        super.moveLeft(speed);
        Rotate();
        refreshAsteroidSprite();
        refreshCoord();
    }
}
