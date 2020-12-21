package flappyBird;

import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import static flappyBird.Constant.*;

/**
 * Classe qui représente un oiseau de l'espace pouvant tirer
 *
 * @author Louis Bovay
 */
public class SpaceBird extends Bird {

    ArrayList<Projectile> magazine = new ArrayList<>();
    private int reloadCooldown = 0;

    /**
     * Crée et instantie une Shape à la position, la taille et la couleur voulue
     *
     * @param x          coordonnée X
     * @param y          coordonnée Y
     * @param w          Largeur
     * @param h          Hauteur
     * @param color      Couleur
     * @param stackpane StackPane
     * @param spritePath    Chemin du sprite
     */
    SpaceBird(int x, int y, int w, int h, Color color, StackPane stackpane, String spritePath) {
        super(x, y, w, h, color, stackpane, spritePath);
        revive();
    }


    /**
     * Ajoute un nouveau projectile au chargeur
     */
    private void getAmmo(int x, int y, int size, String sprite,int lifeTime){
        magazine.add(new Projectile(x,y,size,size,Color.TRANSPARENT,getStackpane(),sprite,lifeTime));
    }

    /**
     * Tire un projectile du chargeur
     * Supprime tous les projectiles perdus de la liste
     */
    public void shoot(Direction direction){
        //Liste qui contiendra tous les projectiles perdus
        ArrayList<Projectile> found = new ArrayList<>();

        if(magazine !=null) {
            if (!magazine.isEmpty()) {
                for (Projectile ammo : magazine) {
                    ammo.travel(PROJECTILE_SPEED, direction);
                    if(ammo.isDead()){
                        found.add(ammo);
                    }
                }
            }
        }
        //Suppresson des projectiles perdus
        if(!found.isEmpty()) {
            magazine.removeAll(found);
        }
    }

    /**
     * Met un projectile dans le chargeur et lance le temps de recharge
     */
    public void reload(int x, int y, int size, String sprite,int lifetime,int reloadCooldown){
        if(this.reloadCooldown == 0){
            getAmmo(x,y,size,sprite,lifetime);
            this.reloadCooldown = reloadCooldown;
        }
    }

    /**
     * Fait diminuer le temps de recharge
     */
    public void decreaseReloadCooldown(){
        this.reloadCooldown -=1;
    }

    /**
     * renvoie le temps de recharge
     * @return le temps de recharge
     */
    public int getReloadCooldown() {
        return reloadCooldown;
    }

    /**
     * Vide et supprimer les projectiles du chargeur
     */
    public void emptyMagazine(){
        for(Projectile ammo : magazine){
            ammo.delete();
        }
        magazine.clear();
    }
}


