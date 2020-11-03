package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Classe qui représente un Oiseau
 */
public class Bird extends Sprite {
    //Si l'oiseau est vivant ou mort
    private boolean isAlive;
    //Sprite de la classe Bird
    private ImageView birdSprite;
    private Image birdImage;

    //position d'arrivé après un vole
    private int goal = 0;
    private int hauteurDuVole;
    //position de départ de l'oiseau
    private int start = 0;
    //Savoir si l'oiseau est en train de voler ou s'il tombe
    private boolean flying = false;
    //Permet de refresh Start et Goal uniquement une fois
    private boolean startAndGoalAreSetup = false;

    /**
     * Crée et instantie un oiseau
     *
     * @param x     coordonnée X
     * @param y     coordonnée Y
     * @param w     Largeur
     * @param h     Hauteur
     * @param color Couleur
     */
    public Bird(int x, int y, int w, int h, Color color) {
        super(x, y, w, h, color);
        this.isAlive = true;
        birdImage = new Image("Sprites/flappy.png");
        birdSprite = new ImageView(birdImage);
    }

    /**
     * @return l'état de santé de l'oiseau (true = vivant, false = mort)
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Tue l'oiseau
     */
    public void kill() {
        this.isAlive = false;
    }

    /**
     * fait subir à l'oiseau une force de gravité, ce qui le poussera a tomber en continu
     * plus la gravité est élevée, plus il tombera vite
     *
     * @param gravity force de gravité
     */
    public void undergoGravity(int gravity) {
        this.moveDown(gravity);
        refreshBirdSprite();
    }

    /**
     * L'oiseau monte/"se téléporte" l'axe Y en fonction de sa force
     *
     * @param strengh force de l'oiseau, plus elle est haute, plus il montera haut
     */
    public void flap(int strengh) {
        this.setTranslateY(this.getTranslateY() - strengh);
        refreshBirdSprite();
    }

    /**
     * Permet d'accéder au sprite
     * @return birdSprite
     */
    public ImageView getBirdSprite() {
        return birdSprite;
    }

    /**
     * Assigne la position X et Y du sprite à la position X et Y de l'objet
     */
    public void refreshBirdSprite() {
        birdSprite.setTranslateX(this.getTranslateX());
        birdSprite.setTranslateY(this.getTranslateY());
    }

    /**
     * deuxième version du flap, cette fois l'oiseau montre progressivement vers son point d'arrivée
     */
    public void flap2() {
        //Si start et goal ne sont pas à jours, les mettre à jours
        if (!startAndGoalAreSetup) {
            refreshStart();
            refreshGoal(150);
            startAndGoalAreSetup = !startAndGoalAreSetup;
        }
        //l'oiseau monte
        this.moveUp(36);
        //Si l'oiseau atteint son point d'arrivée, arrête de volé
        if (this.getTranslateY() < this.goal) {
            flying = false;
        }
        //Rafraîchissement du sprite
        refreshBirdSprite();
    }

    /**
     * Met à jours la variable goal
     * @param hauteurDuVole valeur fixe où l'oiseau doit se rendre
     */
    public void refreshGoal(int hauteurDuVole) {
        this.goal = (int) this.getTranslateY() - hauteurDuVole;
    }

    /**
     * Met à jours la variable start, c'est là où se trouve l'oiseau
     */
    public void refreshStart() {
        this.start = (int) this.getTranslateY();
    }

    /**
     * Donne accès à la variable flying
     * @return flying
     */
    public boolean isFlying() {
        return flying;
    }

    /**
     * Change l'état de flying
     * @param flying (true = l'oiseau vole, false = l'oiseau tombe)
     */
    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    /**
     * Permet de changer l'était de startAndGoalAreSetup
     * @param startAndGoalAreSetup
     */
    public void setStartAndGoalAreSetup(boolean startAndGoalAreSetup) {
        this.startAndGoalAreSetup = startAndGoalAreSetup;
    }

}


