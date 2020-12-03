package flappybird;

import java.nio.file.Paths;
import java.text.DecimalFormat;

public class Constant {

    /************************
     PATH
     */
    //Chemin vert le dossier Sprites
    public final static String PATH_DIR_SPRITES = "Sprites/";
    //Chemin vers le dossier police
    public final static String PATH_DIR_FONT = "police/";
    //Chemin vers le fichier Scores.txt
    public final static String PATH_FILE_SCORES = Paths.get(".").toAbsolutePath().normalize().toString() + "\\flappy_bird\\src\\scores.txt";
    //Chemin vers le fichier ScoresHardMode.txt
    public final static String PATH_FILE_SCORES_HARDMODE = Paths.get(".").toAbsolutePath().normalize().toString() + "\\flappy_bird\\src\\scoresHardMode.txt";

    /************************
     NUMERIC
     */
    //Hauteur max de la fenêtre
    public final static float MAX_HEIGHT = 700;
    //Largeur max de la fenêtre
    public final static float MAX_WIDTH = 1000;
    //Elan de l'oiseau (vitesse de pointe)
    public final static float BIRD_MOMENTUM = 19.5f;
    //Perte d'élan de l'oiseau
    public final static float BIRD_MOMENTUM_LOSS = 0.5f;
    //Vitesse des tuyaux
    public final static int PIPE_SPEED = 2;
    //Nombre d'images par seconde
    public final static int FPS = 60;

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
    //Message de fin de partie
    public final static String TXT_END_GAME_MESSAGE = "Rejouer\n[Q] Quitter";

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
}
