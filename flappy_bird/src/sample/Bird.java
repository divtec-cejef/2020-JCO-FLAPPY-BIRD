package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Classe qui représente un Oiseau
 *
 * @author Louis Bovay
 */
public class Bird extends Shape {
    //Si l'oiseau est vivant ou mort
    private boolean isAlive;
    //Sprite de la classe Bird
    private ImageView birdSprite;
    private Image birdImage;

    //position d'arrivé après un vole
    private int goal = 0;
    //définit à quel hauteur suplémentaire de l'oiseau le point d'arrivée va être
    private int hauteurDuVole;
    //position de départ de l'oiseau
    private int start = 0;
    //Définit si l'oiseau est en train de volé ou non
    private boolean flying = false;
    //Définit si le point start et goal ont été mis en place
    private boolean startAndGoalAreSetup = false;
    //Elan de l'oiseau (vitesse de pointe de son vole)
    private float momentum = 20;

    /**
     * Crée et instantie un oiseau à la position, la taille et la couleur voulue
     * Un sprite et une vie lui sont automatiquement assigné
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
        birdImage = new Image("Sprites/flappyFlap.png");
        birdSprite = new ImageView(birdImage);
    }

    /**
     * Permet d'accéder à l'état de santé de l'oiseau
     *
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

    public void revive() {
        this.isAlive = true;
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
     * Permet d'accéder au sprite
     *
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
     * l'oiseau monte progressivement à un point donner
     */
    public void flap() {
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
     * L'oiseau entâme un mouvement souple de saut et subit à nouveau la gravité petit à petit
     */
    public void smoothFlap(){
        if(momentum > 0){
            this.moveUp((int)momentum);
            momentum -= 0.4;
        }else{
            flying = false;
        }
    }

    /**
     * Met à jours la variable goal
     *
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
     *
     * @return flying
     */
    public boolean isFlying() {
        return flying;
    }

    /**
     * Change l'état de flying
     *
     * @param flying (true = l'oiseau vole, false = l'oiseau tombe)
     */
    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    /**
     * Permet de changer l'état de startAndGoalAreSetup
     *
     * @param startAndGoalAreSetup , true = les deux points start et goal sont placés, false = ils ne le sont pas
     */
    public void setStartAndGoalAreSetup(boolean startAndGoalAreSetup) {
        this.startAndGoalAreSetup = startAndGoalAreSetup;
    }

    public void setMomentum(float momentum) {
        this.momentum = momentum;
    }
}


