package flappyBird;

import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import static flappyBird.Constant.*;

/**
 * Représente la partie du jeu où le joueur affronte un boss
 */
public class bossManager {
    enum Phases {
        CREATION,
        ENTRANCE,
        FIGHT,
        END
    }

    private final int BOSS_PLACE = 400;
    public SpaceBird boss;
    private Phases bossPhases = Phases.CREATION;
    private int bossFlyCooldown = 0;
    private int bossShootCooldown = 60;
    private int bossHealth = 5;
    private boolean isBossDead = false;

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
                    bossFly();
                    bossShoot();
                    checkHealth(spaceBird);
                    if (bossHealth == 0) {
                        bossPhases = Phases.END;
                    }
                    break;

                //Le boss meurt
                case END:
                    if (bossFalling()) {
                        destroyBoss();
                    }
                    break;
            }
        }
    }

    /**
     * Le boss entre en scène part la droite de l'écran et s'arrête
     *
     * @return si oui ou non le boss a fini d'entrer
     */
    private boolean bossEntrance() {
        if (boss.getTranslateX() > BOSS_PLACE) {
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
        boss = new SpaceBird(700, 0, 200, 200, Color.OLIVEDRAB, stackPane, IMG_FLAPPY);
        boss.getSprite().setFitHeight(boss.getHeight());
        boss.getSprite().setFitWidth(boss.getWidth());
    }

    private void bossFly() {
        if (bossFlyCooldown == 0) {
            //le boss vole
            boss.setFlying(true);
            //le boss repprend son élan
            boss.setMomentum(SPACEBIRD_MOMENTUM);
            //le cooldown repprend
            bossFlyCooldown = 90;
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
        if (bossShootCooldown == 0) {
            boss.reload();
            System.out.println("BOSS : TIRE");
            bossShootCooldown = Constant.getRandomNumber(30, 200);
        }
        if (!boss.magazine.isEmpty()) {
            boss.shoot(Direction.LEFT);
        }
        bossShootCooldown--;
    }

    /**
     * Check si le boss se fait touché par les projectile de l'oiseau de l'espace
     * @param spaceBird l'oiseau de l'espace contre lequel il se bat
     */
    private void checkHealth(SpaceBird spaceBird) {
        for (Projectile spaceBirdProjectile : spaceBird.magazine) {
            if (Area.isHit(boss.getArea(), spaceBirdProjectile.getArea())) {
                bossHealth--;
            }
        }
    }

    /**
     * Le boss entâme une animation de mort qui le fait tourner et tomber
     *
     * @return si oui ou non le boss est sortit de l'écran
     */
    private boolean bossFalling() {
        boss.getSprite().setRotate(boss.getRotate() + 3);
        boss.undergoGravity(SPACEBIRD_GRAVITY);

        return boss.getTranslateY() > 500;
    }

    /**
     * détruit le boss
     */
    private void destroyBoss() {
        boss.delete();
        isBossDead = true;
    }

    public boolean isBossDead() {
        return isBossDead;
    }
}
