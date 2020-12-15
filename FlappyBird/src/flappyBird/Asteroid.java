package flappyBird;


import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static flappyBird.Constant.PATH_DIR_SPRITES;

/**
 * Classe qui représente un astéroïde
 *
 * @author Louis Bovay
 */
public class Asteroid extends Shape {

    //Position maximale et minimales de déplacement vertical de l'astéroïde
    private double maxY = 0.00;
    private double minY = 0.00;

    //Si oui au non l'astéroide va en haut, sinon va en bas
    private boolean isGoingUp = false;
    //Vitesse variable verticale
    private float variableSpeed = 30;
    //Si oui ou non il se déplacera en vertical
    private boolean isMoving;
    //vitesse de l'asteroide
    private int speed = 6;

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

        //Attribuer l'un des 9 sprite aléatoire
        setSprite(pickImage(getRandomNumber(1, 10)));
        //Faire correspondre les dimensions du sprites à la taille de l'objet
        getSprite().setFitHeight(h);
        getSprite().setFitWidth(w);
        //Attribution de la haut max et min de déplacement vertical
        setMaxYAndMinY();
        //Détermine s'il bougera en vertical ou non
        if (getRandomNumber(0, 2) == 0) {
            isMoving = true;
            speed = 3;
        }else{
            //rotation aléatoire
            randomRotate(getRandomNumber(0, 4));
        }
        System.out.println(getRandomNumber(0, 2));

    }

    private String pickImage(int numeroImage) {
        return PATH_DIR_SPRITES + "asteroide" + numeroImage + ".png";
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
        if (!isMoving) {
            getSprite().setRotate(getSprite().getRotate() - 1);
            this.setRotate(this.getRotate() - 1);
        }
    }

    public ImageView getAsteroidSprite() {
        return this.getSprite();
    }

    public boolean isHit(ArrayList<Projectile> projectileArrayList) {
        boolean isTouched = false;
        ArrayList<Projectile> found = new ArrayList<>();

        for (Projectile projectile : projectileArrayList) {
            if (Area.isHit(projectile.getArea(), this.getArea())) {
                found.add(projectile);
                projectile.delete();
                isTouched = true;
            }
        }
        projectileArrayList.removeAll(found);
        return isTouched;
    }

    private void randomRotate(int random) {
        switch (random) {
            case 0:
                getSprite().setRotate(0);
                break;
            case 1:
                getSprite().setRotate(90);
                break;
            case 2:
                getSprite().setRotate(180);
                break;
            case 3:
                getSprite().setRotate(270);
                break;

        }
    }

    private void setMaxYAndMinY() {
        maxY = this.getTranslateY() - 100;
        minY = this.getTranslateY() + 100;
    }

    private void setGoingUp() {
        if (this.getTranslateY() < maxY) {
            isGoingUp = false;
            variableSpeed = 1f;
        }
        if (this.getTranslateY() > minY) {
            isGoingUp = true;
            variableSpeed = 1f;
        }
        if (variableSpeed < 5) {
            variableSpeed += 0.1f;
        } else if (variableSpeed < 10) {
            variableSpeed += 0.1f;
        }
    }

    public void verticalMove() {
        setGoingUp();

        if (isGoingUp) {
            moveUp((int) variableSpeed);
        } else {
            moveDown((int) variableSpeed);
        }
    }

    public boolean isMoving() {
        return isMoving;
    }

    public int getSpeed() {
        return speed;
    }
}
