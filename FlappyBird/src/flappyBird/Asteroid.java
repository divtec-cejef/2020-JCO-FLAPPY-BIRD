package flappyBird;

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
    private boolean hasVerticalsMovements;
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

        //Attribuer l'un des 9 sprites aléatoire
        setSprite(pickImage(getRandomNumber(1, 10)));
        //Faire correspondre les dimensions du sprites à la taille de l'objet
        getSprite().setFitHeight(h);
        getSprite().setFitWidth(w);
        //Attribution de la haut max et min de déplacement vertical
        setMaxYAndMinY();
        //Détermine s'il bougera en vertical ou non
        if (getRandomNumber(0, 2) == 0) {
            hasVerticalsMovements = true;
            speed = 3;
        }else{
            //rotation aléatoire
            randomRotate(getRandomNumber(0, 4));
        }

    }

    /**
     * Choisit une image parmis les 9 disponnible (asteroide(1 à 9).png)
     * @param numeroImage numéro de l'image (de 1 à 9)
     * @return le chemin vers le sprite choisit
     */
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

    /**
     * Observe si l'astéroïde sort de l'écran, si c'est le cas, le supprime
     */
    public void checkBounds() {
        if (isOut()) {
            getStackpane().getChildren().remove(getSprite());
            getStackpane().getChildren().remove(this);
        }
    }

    /**
     * supprime et retire tout les astéroïdes présents en jeu
     * @param list liste d'astéroïdes
     * @param stackpane stackpane où sont présents les astéroïdes
     */
    public static void killThemAll(ArrayList<Asteroid> list, StackPane stackpane) {
        for (Asteroid asteroid : list) {
            stackpane.getChildren().remove(asteroid.getSprite());
            stackpane.getChildren().remove(asteroid);
        }
        list.clear();
    }

    /**
     * l'astéroide se déplace de droite à gauche <br>
     *     s'il ne se déplace pas verticalement, il tourne sur lui-même
     * @param speed vitesse de déplacement
     */
    @Override
    void moveLeft(int speed) {
        super.moveLeft(speed);
        if (!hasVerticalsMovements) {
            getSprite().setRotate(getSprite().getRotate() - 1);
            this.setRotate(this.getRotate() - 1);
        }
    }

    /**
     * Check si un astéroide est touché par un projectile présent dans un liste de projectile
     * @param projectileArrayList liste de projectiles
     * @return true = l'astéroide est touché / false = l'astéroide n'est pas touché
     */
    public boolean isHit(ArrayList<Projectile> projectileArrayList) {
        boolean isTouched = false;
        //Liste qui contiendra tous les prjectil a suprimés
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

    /**
     * Effecture une rotation d'un quart
     * @param randomNumber nombre aléatoire
     */
    private void randomRotate(int randomNumber) {
        switch (randomNumber) {
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

    /**
     * Assigne les valeurs max et min des déplacements verticaux de l'astéroide
     */
    private void setMaxYAndMinY() {
        maxY = this.getTranslateY() - 100;
        minY = this.getTranslateY() + 100;
    }

    /**
     *
     */
    private void setGoingUp() {
        if (this.getTranslateY() < maxY) {
            isGoingUp = false;
            variableSpeed = 1f;
        }
        if (this.getTranslateY() > minY) {
            isGoingUp = true;
            variableSpeed = 1f;
        }
        //lorsqu'il change de sens, va de plus en plus vite
        variableSpeed += 0.1;
    }

    /**
     * Effectue un mouvement vertical de haut en bas
     */
    public void verticalMove() {
        setGoingUp();

        if (isGoingUp) {
            moveUp((int) variableSpeed);
        } else {
            moveDown((int) variableSpeed);
        }
    }

    /**
     * Décrit si oui ou non l'astéroide effectue des mouvements verticaux
     * @return si oui ou non il effectue un mouvement vertical
     */
    public boolean hasVerticalsMovements() {
        return hasVerticalsMovements;
    }

    /**
     *
     * @return la vitesse de déplacement horizontal
     */
    public int getSpeed() {
        return speed;
    }
}
