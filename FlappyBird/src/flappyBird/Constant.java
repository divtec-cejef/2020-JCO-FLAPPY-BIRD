package flappyBird;

import java.nio.file.Paths;

public abstract class Constant {

    /************************
     PATH
     */
    //Chemin vert le dossier Sprites
    public final static String PATH_DIR_SPRITES = "/sprites/";
    //Chemin vers le dossier police
    public final static String PATH_DIR_FONT = "/font/";
    //Chemin vers le fichier son du flap
    public final static String PATH_DIR_FLAP_SOUNDS = Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\sounds\\flapSound.mp3";
    //Chemin vers le fichier son du flap
    public final static String PATH_DIR_IMPACT_SOUNDS = Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\sounds\\impactSound.mp3";
    //Chemin vers le fichier Scores.txt
    public final static String PATH_FILE_SCORES = Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\scores.txt";
    //Chemin vers le fichier ScoresHardMode.txt
    public final static String PATH_FILE_SCORES_HARDMODE = Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\scoresHardMode.txt";
    //Chemin vers le fichier ThirdMode.txt
    public final static String PATH_FILE_SCORE_THIRDMODE = Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\scoreThirdMode.txt";

    /************************
     NUMERIC
     */
    //Hauteur max de la fenêtre
    public final static float MAX_HEIGHT = 700;
    //Largeur max de la fenêtre
    public final static float MAX_WIDTH = 1000;
    //Vitesse des tuyaux
    public final static int PIPE_SPEED = 2;
    //Nombre d'images par seconde
    public final static int FPS = 60;
    //Vitesse des projectiles
    public final static int PROJECTILE_SPEED = 5;
    //Elan de l'oiseau en mode normal et hard
    public final static float BIRD_MOMENTUM_NORMAL_HARD = 19.5f;
    //Elan de l'oiseau de l'espace
    public final static float SPACEBIRD_MOMENTUM = 15f;
    //Gravité de l'oiseau en mode normal
    public final static int BIRD_GRAVITY_NORMAL = 8;
    //Gravité de l'oiseau en mode hard
    public final static int BIRD_GRAVITY_HARD = 9;
    //Gravité de l'oiseau en thirdMode
    public final static int SPACEBIRD_GRAVITY = 7;
    //Perte d'élan de l'oiseau en mode normal et hard
    public final static float BIRD_LOSSMOMENTUM_NORMAL_HARD = 0.5f;
    //Perte d'élan de l'oiseau en thirdMode
    public final static float SPACEBIRD_LOSSMOMENTUM = 0.2f;
    //Temps de recharge de l'oiseau de l'espace
    public final static int SPACEBIRD_RELOAD_COOLDOWN = 30;
    //Temps de trajet de projectil du spacebird
    public final static int SPACEBIRD_PROJECTILE_LIFETIME = 60;
    //taille de projectile
    public final static int PROJECTILE_SIZE = 35;
    //fréquence d'aparition des astéroides
    public final static int ASTEROID_SPAWN_RATE = 120;
    //Temps de recharge du tire du boss
    public final static int BOSS_SHOOT_COOLDOWN = 50;
    //Vie du boss
    public final static int BOSS_MAX_HEALTH = 50;
    //temps de trajet de projectile du boss
    public final static int BOSS_PROJECTILE_LIFETIME = 90;
    //vitesse de féliement du background classique
    public final static int BACKGROUND_SPEED = 1;
    //vitesse de féliement du background lorsque le boss arrive
    public final static int BACKGROUND_BOSS_SPEED = 10;

    /************************
     TEXT
     */
    //Titre du jeu
    public final static String TXT_GAME_TITLE = "FLAPPY BIRD";
    //Message d'info de début de partie
    public final static String TXT_START_MESSAGE = "[SPACE] Commencer";
    //Message d'alerte avant la fermeture de l'application
    public final static String TXT_ALERT_MESSAGE = "Voulez-vous vraiment quitter ?\n[Y] OUI\n[N] NON";
    //Titre du tableau des scores
    public final static String TXT_SCORES_TITLE = "Scores :\n";
    //Titre du tableau des score du hardmode
    public final static String TXT_HARDMODE_SCORES_TITLE = "Scores [HM] :\n";
    //Titre du tableau des score du hardmode
    public final static String TXT_THIRDMODE_SCORES_TITLE = "Ennemis tués :\n";
    //Message de fin de partie
    public final static String TXT_END_GAME_MESSAGE = "\n[Q] Quitter";
    //Nom du fichier de police d'écriture
    public final static String TXT_POLICE_FILE_NAME = "KGHAPPY.ttf";
    //Nom Windows de la police d'écriture
    public final static String TXT_POLICE_NAME = "KG HAPPY";

    /************************
     IMAGES
     */
    //Première partie de l'arrière plan
    public final static String IMG_BACKGROUND_PT_1 = PATH_DIR_SPRITES + "cloudbg1.png";
    //Deuxième partie de l'arrière plan
    public final static String IMG_BACKGROUND_PT_2 = PATH_DIR_SPRITES + "cloudbg2.png";
    //Sprite de FlappyBird
    public final static String IMG_FLAPPY = PATH_DIR_SPRITES + "flappy.png";
    //Sprite de FlappyBird qui bat des ailes
    public final static String IMG_FLAPPY_FLAP = PATH_DIR_SPRITES + "flappyFlap.png";
    //Icon de l'application
    public final static String IMG_ICON = PATH_DIR_SPRITES + "icon.png";
    //Sprite de SpaceFlappyBird
    public final static String IMG_SPACE_FLAPPY = PATH_DIR_SPRITES + "spaceFlappy.png";
    //Sprite de SpaceFlappyBird qui bat des ailes
    public final static String IMG_SPACE_FLAPPY_FLAP = PATH_DIR_SPRITES + "spaceFlappyFlap.png";
    //Sprite de SpaceFlappyBird qui tire
    public final static String IMG_SPACE_FLAPPY_SHOOT = PATH_DIR_SPRITES + "spaceFlappyShoot.png";
    //Sprite de projectile
    public final static String IMG_PROJECTILE = PATH_DIR_SPRITES + "ammo.png";
    //Sprite du tuyau
    public final static String IMG_PIPE = PATH_DIR_SPRITES + "longpipe.png";
    //Sprite par défaut des astéroïdes
    public final static String IMG_DEFAULT_ASTEROID = PATH_DIR_SPRITES + "ennemy1.png";
    //Sprite du boss
    public final static String IMG_BOSS = PATH_DIR_SPRITES + "boss.png";

    /**
     * Génère un nombre aléatoire entre une range donnée
     *
     * @param min range inférieure
     * @param max range supérieure
     * @return un nombre aléatoire
     */
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
