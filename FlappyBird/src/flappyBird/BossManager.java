package flappyBird;

import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Optional;

import static flappyBird.Constant.*;

/**
 * Représente la partie du jeu où le joueur affronte un boss
 */
public class BossManager {
    enum Phases {
        CREATION,
        ENTRANCE,
        FIGHT,
        END
    }

    private final int BOSS_PLACE = 200;
    public SpaceBird boss;
    private Phases bossPhases = Phases.CREATION;
    private int bossFlyCooldown;
    private int bossHealth;
    private boolean isBossDead = false;
    private int hitmarkerCooldown = 30;
    private boolean effectON = false;
    private boolean isHit = false;

    ColorAdjust baseColorAdjust = new ColorAdjust();
    ColorAdjust colorAdjust = createColorAdjust();

    /**
     * Déroulement de toute la phase du boss, de la création à la fin
     *
     * @param stackPane stackpane où se déroulera le combat
     * @param spaceBird l'oiseau de l'espace contre lequel il se bat
     */
    public void bossUpdate(StackPane stackPane, SpaceBird spaceBird) {
        if (!isBossDead) {
            switch (bossPhases) {
                //Crée le boss
                case CREATION:
                    createBoss(stackPane);
                    bossPhases = Phases.ENTRANCE;
                    break;

                //le boss entre en scène
                case ENTRANCE:
                    if (bossEntrance()) {
                        bossPhases = Phases.FIGHT;
                    }
                    break;

                //Le boss combat
                case FIGHT:
                    bossShoot();
                    bossFly();
                    checkBossHealth(spaceBird);
                    if (bossHealth == 0) {
                        boss.emptyMagazine();
                        bossPhases = Phases.END;
                    }
                    if (isHit) {
                        hitmarker();
                    }
                    isSpaceBirdHit(spaceBird);
                    break;

                //Le boss meurt
                case END:
                    if (bossFalling()) {
                        spaceBird.kill();
                    }
                    break;
            }
            boss.getSprite().toFront();
        }
    }

    /**
     * Le boss entre en scène part la droite de l'écran et s'arrête
     *
     * @return si oui ou non le boss a fini d'entrer
     */
    private boolean bossEntrance() {
        if (boss.getTranslateX() >= BOSS_PLACE) {
            boss.moveLeft(5);
        }
        return boss.getTranslateX() < BOSS_PLACE;
    }

    /**
     * Crée le boss
     *
     * @param stackPane stackpane où se trouvera le boss
     */
    private void createBoss(StackPane stackPane) {
        bossHealth = BOSS_MAX_HEALTH;
        isBossDead = false;
        boss = new SpaceBird(700, 0, 200, 200, Color.TRANSPARENT, stackPane, IMG_BOSS);
        boss.getSprite().setFitHeight(boss.getHeight());
        boss.getSprite().setFitWidth(boss.getWidth());
    }

    private void bossFly() {
        if (bossFlyCooldown == 0 || boss.getTranslateY() > 300) {
            //le boss vole
            boss.setFlying(true);
            //le boss repprend son élan
            boss.setMomentum(25);
            //le cooldown repprend
            if (boss.getTranslateY() > 300) {
                bossFlyCooldown = 30;
            } else {
                bossFlyCooldown = 100;
            }
        }
        //le boss vole
        if (boss.isFlying()) {
            boss.smoothFlap();
        }
        //le boss subit en permanance la gravité quand le jeu est en cours
        boss.undergoGravity(SPACEBIRD_GRAVITY);
        //le cooldown diminue
        bossFlyCooldown--;
    }

    /**
     * Le boss tire lorsque son cooldown est à zéro
     */
    private void bossShoot() {

        boss.reload((int) boss.getTranslateX() - 60, (int) boss.getTranslateY() + getRandomNumber(-100, 100), 60, IMG_PROJECTILE, BOSS_PROJECTILE_LIFETIME, getRandomNumber(10, 60));
        if (!boss.magazine.isEmpty()) {
            boss.shoot(Direction.LEFT);
            boss.decreaseReloadCooldown();
        }
    }

    /**
     * Check si le boss se fait touché par les projectile de l'oiseau de l'espace
     *
     * @param spaceBird l'oiseau de l'espace contre lequel il se bat
     */
    private void checkBossHealth(SpaceBird spaceBird) {
        ArrayList<Projectile> found = new ArrayList<>();

        for (Projectile spaceBirdProjectile : spaceBird.magazine) {
            if (Area.isHit(spaceBirdProjectile.getArea(), boss.getArea())) {
                bossHealth--;
                isHit = true;
                found.add(spaceBirdProjectile);
                spaceBirdProjectile.delete();
            }
        }
        spaceBird.magazine.removeAll(found);
    }

    /**
     * Le boss entâme une animation de mort qui le fait tourner et tomber
     *
     * @return si oui ou non le boss est sortit de l'écran
     */
    private boolean bossFalling() {
        boss.getSprite().setRotate(boss.getSprite().getRotate() - 10);
        boss.moveLeft(10);
        boss.moveUp(5);

        return boss.getTranslateY() < -500;
    }

    /**
     * détruit le boss
     */
    public void destroyBoss() {
        boss.delete();
        boss.emptyMagazine();
        isBossDead = true;
        boss = null;
    }

    /**
     * @return si oui ou non le boss est mort
     */
    public boolean isBossDead() {
        return isBossDead;
    }

    /**
     * permet de choisir la phase de jeu du boss
     *
     * @param bossPhases la phase de jeu que l'on souhaite mettre
     */
    public void setBossPhases(Phases bossPhases) {
        this.bossPhases = bossPhases;
    }

    /**
     * Vérifier si l'oiseau de l'espace est touché
     *
     * @param spaceBird l'oiseau de l'espace
     */
    private void isSpaceBirdHit(SpaceBird spaceBird) {
        for (Projectile bossProjectile : boss.magazine) {
            if (Area.isHit(bossProjectile.getArea(), spaceBird.getArea())) {
                spaceBird.kill();
            }
        }
    }

    /**
     * définir l'était de vie du boss
     *
     * @param bossDead true : mort, false : vivant
     */
    public void setBossDead(boolean bossDead) {
        isBossDead = bossDead;
    }

    /**
     * Effet de clignotement du boss
     *
     * @return un effet de couleur
     */
    private ColorAdjust createColorAdjust() {
        ColorAdjust colorAdjust = new ColorAdjust();

        //Setting the contrast value
        colorAdjust.setContrast(0.4);

        //Setting the hue value
        colorAdjust.setHue(-0.05);

        //Setting the brightness value
        colorAdjust.setBrightness(0.9);

        //Setting the saturation value
        colorAdjust.setSaturation(0.8);

        return colorAdjust;
    }

    /**
     * fait clognoter le boss
     */
    private void hitmarker() {
        if (hitmarkerCooldown <= 0) {
            if (!effectON) {
                boss.getSprite().setEffect(colorAdjust);
            } else {
                boss.getSprite().setEffect(baseColorAdjust);
            }
            effectON = !effectON;
            hitmarkerCooldown = 10;
        }
        hitmarkerCooldown--;
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
}


