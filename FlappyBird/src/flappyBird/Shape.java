package flappyBird;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Classe qui représente une Shape (forme)
 *
 * @author Louis Bovay
 */
public abstract class Shape extends Rectangle {

    private Area area = new Area();
    private final StackPane stackpane;
    private ImageView sprite;

    /**
     * Crée et instantie une Shape à la position, la taille et la couleur voulue
     *
     * @param x     coordonnée X
     * @param y     coordonnée Y
     * @param w     Largeur
     * @param h     Hauteur
     * @param color Couleur
     * @param stackpane StackPane ou sera stocké l'objet
     * @param spritePath chemin d'accès vers le sprite de l'objet
     */
    Shape(int x, int y, int w, int h, Color color, StackPane stackpane,String spritePath) {
        super(w, h, color);
        this.stackpane = stackpane;
        this.sprite = new ImageView(new Image(spritePath));
        setTranslateX(x);
        setTranslateY(y);
        //Mise à jours des coins
        refreshCoord();
        refreshSprite();
        //L'object créé est directement intégré dans la stackpane avec son sprite
        insertIntoStackPane();
    }

    /**
     * Déplace la Shape vers la gauche
     *
     * @param speed vitesse de déplacement
     */
    void moveLeft(int speed) {
        setTranslateX(getTranslateX() - speed);
        //Mise à jours des coordonnées des quatre coins
        refreshCoord();
        refreshSprite();
    }

    /**
     * Déplace la Shape vers la droite
     *
     * @param speed vitesse de déplacement
     */
    void moveRight(int speed) {
        setTranslateX(getTranslateX() + speed);
        //Mise à jours des coordonnées des quatre coins
        refreshCoord();
        refreshSprite();
    }

    /**
     * Déplace la Shape vers le haut
     *
     * @param speed vitesse de déplacement
     */
    void moveUp(int speed) {
        setTranslateY(getTranslateY() - speed);
        //Mise à jours des coordonnées des quatre coins
        refreshCoord();
        refreshSprite();
    }

    /**
     * Déplace la Shape vers le bas
     *
     * @param speed vitesse de déplacement
     */
    void moveDown(int speed) {
        setTranslateY(getTranslateY() + speed);
        //Mise à jours des coordonnées des quatre coins
        refreshCoord();
        refreshSprite();
    }

    /**
     * met à jours les coordonnée des quatre coins de la Shape
     */
    public void refreshCoord() {
        //Y
        area.getTopLeft().setY((int) getTranslateY() - ((int) this.getHeight() / 2));
        area.getTopRight().setY((int) getTranslateY() - ((int) this.getHeight() / 2));
        area.getDownLeft().setY((int) getTranslateY() + ((int) this.getHeight() / 2));
        area.getDownRight().setY((int) getTranslateY() + ((int) this.getHeight() / 2));

        //X
        area.getTopLeft().setX((int) getTranslateX() - ((int) this.getWidth() / 2));
        area.getTopRight().setX((int) getTranslateX() + ((int) this.getWidth() / 2));
        area.getDownLeft().setX((int) getTranslateX() - ((int) this.getWidth() / 2));
        area.getDownRight().setX((int) getTranslateX() + ((int) this.getWidth() / 2));
    }

    /**
     * Permet d'accéder à area
     *
     * @return area
     */
    public flappyBird.Area getArea() {
        return area;
    }

    private void insertIntoStackPane(){
        stackpane.getChildren().add(this.sprite);
        stackpane.getChildren().add(this);
    }

    /**
     *
     * @param spritePath Chemin vers le sprite qu'on souhaiterais mettre
     */
    public void setSprite(String spritePath) {
        this.getSprite().setImage(new Image(spritePath));
    }

    /**
     * Rafraichit la position du sprite
     */
    public void refreshSprite(){
        this.getSprite().setTranslateX(this.getTranslateX());
        this.getSprite().setTranslateY(this.getTranslateY());
    }

    /**
     *
     * @return le sprite de l'objet
     */
    public ImageView getSprite() {
        return sprite;
    }

    /**
     *
     * @return la stackpane
     */
    public StackPane getStackpane() {
        return stackpane;
    }

    /**
     * Le sprite ainsi que l'objet sont enlevé de la stackpane
     */
    public void delete(){
        stackpane.getChildren().remove(this.getSprite());
        stackpane.getChildren().remove(this);
    }
}