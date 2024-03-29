package flappyBird;

import com.sun.javafx.scene.traversal.Direction;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Optional;

import static flappyBird.Constant.*;

/**
 * @author Louis Bovay
 * @version 3.0 - 04.11.2020
 * FLAPPY BIRD
 * Projet d'atelier 26.10.2020 au 22.01.2021
 * "Création d'un petit jeu en programmation orientée objet"
 */
public class Main extends Application {

    //Indice de temps, 60 = 1 secondes
    double timeSpend = 0;
    //Si oui ou non la barre espace est appuyée
    boolean isSpacePressed = false;
    //Si oui ou non la touche Q est appuyée
    boolean isQPressed = false;
    //Si oui ou non le jeu est en cours
    boolean isGameRunning = false;
    //Si oui ou non le jeu à déjà été lancé
    boolean isGameStarted = false;
    //Si oui ou non le score à été écrit
    boolean scoreHasBeenWrited = false;
    //Si oui ou non la touche P à été relachée
    boolean pHasBeenPressed = false;
    //Gravité appliquée à l'oiseau de base
    int birdGravity = BIRD_GRAVITY_NORMAL;
    //Fréquence d'apparition des ennemis (60 = 1 seconde)
    int asteroidSpawnRate = ASTEROID_SPAWN_RATE;
    //Pane principale
    StackPane stackPane = new StackPane();
    //L'oiseau
    Bird bird;
    //Spacebird
    SpaceBird spaceBird;
    //LeScore
    Score score = new Score(0, 0);
    //Text d'information
    Text txtInformation = new Text(0, 0, "");
    //Liste de couple de tuyau
    ArrayList<PipeCouple> couplesList;
    // Background
    ArrayList<ImageView> backgroundList = new ArrayList<>();
    //Image du hardmode
    ImageView skull = new ImageView(new Image(PATH_DIR_SPRITES + "skull.png"));
    //logo du 3eme mode de jeu
    ImageView thirdModeLogo = new ImageView(new Image(PATH_DIR_SPRITES + "thirdModeLogo.png"));
    //Fichier de score
    File scoreFile = new File(PATH_FILE_SCORES);
    //Fichier de score du hardmode
    File hardModeScoreFile = new File(PATH_FILE_SCORES_HARDMODE);
    //Fichier de score du thirdMode
    File thirdModeFile = new File(PATH_FILE_SCORE_THIRDMODE);
    //Tableau de score
    Text txtScoreBoard = new Text(0, 0, "");
    //Chrono pour relancer le jeu
    double replayTimer = 3;
    //format des décimal
    DecimalFormat df = new DecimalFormat("#");
    //fichiers musicaux
    Sound flapSound = new Sound(PATH_DIR_FLAP_SOUNDS);
    Sound impactSound = new Sound(PATH_DIR_IMPACT_SOUNDS);
    //Liste d'ennemis
    ArrayList<Asteroid> asteroidArrayList = new ArrayList<>();
    //boss
    BossManager bossManager = new BossManager();
    //vitesse de fond d'écran
    int backgroundSpeed = BACKGROUND_SPEED;
    //Nom du joueur
    String playerName = "";

    //Enumération du mode de jeu
    public enum gameMode {
        NORMAL,
        HARD,
        FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K
    }

    gameMode selectedGameMode = gameMode.NORMAL;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //Création d'une scène utilisant la pane
        Scene scene = new Scene(createContent());
        stage.setTitle("FlappyBird");
        //Ajouter la police d'écriture
        try {
            Font.loadFont(Main.class.getResource(PATH_DIR_FONT + TXT_POLICE_FILE_NAME).toExternalForm(), 100);
        } catch (Exception e) {
            System.out.println("Police non chargée");
        }

        //Initialisation du score
        score.write("Score : " + score.getPts());
        score.getText().setStroke(Color.BLACK);
        score.getText().setFill(Color.WHITESMOKE);
        score.getText().setTextAlignment(TextAlignment.LEFT);
        StackPane.setAlignment(score.getText(), Pos.TOP_LEFT);
        score.getText().setTranslateX(score.getText().getTranslateX() + 10);
        score.getText().setTranslateY(score.getText().getTranslateY() + 5);
        score.getText().setFont(Font.font(TXT_POLICE_NAME, 50));
        score.getText().setVisible(false);

        //Initialisation du text d'information
        txtInformation.setFont(Font.font(TXT_POLICE_NAME, 45));
        txtInformation.setTextAlignment(TextAlignment.CENTER);
        StackPane.setAlignment(txtInformation, Pos.CENTER);
        txtInformation.setFill(Color.GOLD);
        txtInformation.setStroke(Color.BLACK);
        txtInformation.setTranslateY(200);
        // Initialisation des background
        backgroundList.add(new ImageView(new Image(IMG_BACKGROUND_PT_1)));
        backgroundList.get(0).setTranslateX(0);
        backgroundList.add(new ImageView(new Image(IMG_BACKGROUND_PT_2)));
        backgroundList.get(1).setTranslateX(MAX_WIDTH);
        //Initialisation de l'image du hardmode
        skull.setVisible(false);
        skull.setTranslateX(450);
        skull.setTranslateY(-280);
        //Initialisation de l'image du mode 3
        thirdModeLogo.setVisible(false);
        thirdModeLogo.setTranslateY(-100);
        //initialisation du tableau des score
        txtScoreBoard.setFill(Color.GOLD);
        txtScoreBoard.setStroke(Color.BLACK);
        txtScoreBoard.setStrokeWidth(2);
        StackPane.setAlignment(txtScoreBoard, Pos.TOP_LEFT);
        txtScoreBoard.setTranslateX(txtScoreBoard.getTranslateX() + 30);
        txtScoreBoard.setTranslateY(txtScoreBoard.getTranslateY() + 30);
        txtScoreBoard.setFont(Font.font(TXT_POLICE_NAME, 40));
        txtScoreBoard.setVisible(false);


        /*
        TOUCHE APPUYÉE
         */
        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            //Si SPACE est appuyé
            if (keyCode.equals(KeyCode.SPACE)) {
                if (!isSpacePressed) {
                    //Durant la partie
                    if (isGameRunning) {
                        //Un son est joué
                        flapSound.play();
                        switch (selectedGameMode) {
                            case NORMAL:
                            case HARD:
                                //on change le sprite de l'oiseau
                                bird.getSprite().setImage(new Image(IMG_FLAPPY));
                                //l'oiseau vole
                                bird.setFlying(true);
                                //L'oiseau repprend son élan
                                bird.setMomentum(BIRD_MOMENTUM_NORMAL_HARD);
                                break;
                            case FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K:
                                spaceBird.setFlying(true);
                                spaceBird.setMomentum(SPACEBIRD_MOMENTUM);
                                spaceBird.setSprite(IMG_SPACE_FLAPPY);
                                break;
                        }
                    }
                    //Relancer une partie
                    else {
                        if (replayTimer < 0 && !isQPressed) {
                            restartGame();
                        }
                    }
                    //Lancer la partie
                    if (!isGameStarted) {
                        startGame();
                        txtInformation.setText("");
                        switch (selectedGameMode) {
                            case NORMAL:
                            case HARD:
                                bird = new Bird(-250, 0, 35, 35, Color.TRANSPARENT, stackPane, IMG_FLAPPY);
                                break;
                            case FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K:
                                spaceBird = new SpaceBird(-250, 0, 35, 35, Color.TRANSPARENT, stackPane, IMG_SPACE_FLAPPY);
                                break;
                        }
                        refreshSmoothFlapVariable();

                        if (selectedGameMode == gameMode.FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K) {
                            thirdModeLogo.setVisible(false);
                        }
                    }
                    //l'Input ne sera fait q'une seule fois, pour le refaire il faut lacher espace, et réappuyer
                    isSpacePressed = true;
                }
            }
            if (isGameRunning) {
                if (keyCode.equals(KeyCode.P)) {
                    if (selectedGameMode == gameMode.FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K && spaceBird.getReloadCooldown() <= 0 && !pHasBeenPressed) {
                        spaceBird.reload((int) spaceBird.getTranslateX() + 40, (int) spaceBird.getTranslateY(), PROJECTILE_SIZE, IMG_PROJECTILE, SPACEBIRD_PROJECTILE_LIFETIME, SPACEBIRD_RELOAD_COOLDOWN);
                        spaceBird.setSprite(IMG_SPACE_FLAPPY_SHOOT);
                        pHasBeenPressed = !pHasBeenPressed;
                    }
                }
            }
            //N'est pas disponible en cours de jeu
            if (!isGameRunning) {
                if (isGameStarted) {
                    // Si Q est appuyé, ouvrir le menu de confirmation
                    if (keyCode.equals(KeyCode.Q)) {
                        isQPressed = true;
                        txtInformation.setText(TXT_ALERT_MESSAGE);
                    }
                    // Si Y est appuyé, fermer l'application
                    if (keyCode.equals(KeyCode.Y)) {
                        if (isQPressed) {
                            stage.close();
                        }
                    }
                    // Si N est appuyé, relancer l'instance de fin de jeu
                    if (keyCode.equals(KeyCode.N)) {
                        if (isQPressed) {
                            endGame();
                            isQPressed = false;
                        }
                    }
                }
                //Si G est appuyé, switch entre le mode normal, hard et thirdmode
                if (keyCode.equals(KeyCode.G)) {
                    switch (selectedGameMode) {
                        case NORMAL:
                            selectedGameMode = gameMode.HARD;
                            break;
                        case HARD:
                            selectedGameMode = gameMode.FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K;
                            break;
                        case FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K:
                            selectedGameMode = gameMode.NORMAL;
                            break;
                    }
                    skull.setVisible(selectedGameMode == gameMode.HARD);
                    thirdModeLogo.setVisible(selectedGameMode == gameMode.FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K);
                    switchBetweenModesDisplay(selectedGameMode);
                }
                //Si TAB est appuyé, active/désactive le hardmode
                if (keyCode.equals(KeyCode.TAB)) {
                    txtScoreBoard.setVisible(true);
                }
            }
        });

        /*
         TOUCHE RELACHÉE
         */
        scene.setOnKeyReleased(event -> {
            KeyCode keyCode = event.getCode();
            //Si SPACE est relâché
            if (keyCode.equals(KeyCode.SPACE)) {
                if (isGameRunning) {
                    if (selectedGameMode == gameMode.NORMAL || selectedGameMode == gameMode.HARD) {
                        bird.setSprite(IMG_FLAPPY_FLAP);
                    } else if (selectedGameMode == gameMode.FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K) {
                        spaceBird.setSprite(IMG_SPACE_FLAPPY_FLAP);
                    }
                }
                isSpacePressed = false;
            }
            //Si P est relaché
            if (keyCode.equals(KeyCode.P)) {
                if (pHasBeenPressed) {
                    spaceBird.setSprite(IMG_SPACE_FLAPPY);
                    pHasBeenPressed = !pHasBeenPressed;
                }
            }
            //N'est pas disponnible en cours de jeu
            if (!isGameRunning) {
                //Si TAB est relâché
                if (keyCode.equals(KeyCode.TAB)) {
                    txtScoreBoard.setVisible(false);
                }
            }
        });

        /*
        AJOUT DANS LA STACKPANE
         */
        // Ajout des backgrounds en premier pour qu'ils soient automatiquement en arrière plan
        stackPane.getChildren().add(backgroundList.get(1));
        stackPane.getChildren().add(backgroundList.get(0));
        //Initialisé la liste de couple de tuyau
        couplesList = createCouplesList(6);
        //Ajout du score
        stackPane.getChildren().add(score.getText());
        //Ajout du text d'info
        stackPane.getChildren().add(txtInformation);
        //Ajout de l'image du hardmode
        stackPane.getChildren().add(skull);
        //Ajout le logo du 3eme mode
        stackPane.getChildren().add(thirdModeLogo);
        //Ajout du tableau de score
        stackPane.getChildren().add(txtScoreBoard);
        if (scoreFile.exists()) {
            txtScoreBoard.setText(TXT_SCORES_TITLE + ScoreBoard.getscoreBoard(scoreFile));
        }

        /*
        GESTION ET LANCEMENT DE LA SCÈNE
         */
        // Sélectionner la scene
        stage.setScene(scene);
        //Ajouter une icon à l'application
        stage.getIcons().add(new Image(IMG_ICON));
        // Donner une titre à la scène
        stage.setTitle(TXT_GAME_TITLE);
        //rendre la fenetre non redimentionnable
        stage.setResizable(false);
        //taille max
        stage.setHeight(MAX_HEIGHT);
        stage.setWidth(MAX_WIDTH);

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nom du joueur");
        dialog.setHeaderText("Veuillez entrer votre nom");
        dialog.setContentText("votre nom:");
        dialog.setResizable(false);

        //Attends de recevoir un nom
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            //Si aucun nom n'est saisit, le nom sera un nom par défaut
            if (result.get().equals("")) {
                playerName = "deflautName";
            }
            //On nettoie le nom pour éviter l'injection de score frauduleux
            else {
                for (int i = 0; i < result.get().length(); i++) {
                    if (result.get().charAt(i) != '=' && result.get().charAt(i) != ';') {
                        playerName += result.get().charAt(i);
                    }
                }
            }
        }
        // Lancer la scène
        stage.show();
    }

    /**
     * Initialisation de la pane
     *
     * @return stackPane La Stackpane à utiliser dans la scene
     */
    private Parent createContent() {
        //ID de la pane
        stackPane.setId("pane");
        //Timer basé sur 1 seconde
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> update()));
        //nombbre de fois qu'elle se répète
        timeline.setCycleCount(Animation.INDEFINITE);
        //lancer la timeline
        timeline.play();
        // vitesse 60 = 60 images par seconde
        timeline.setRate(FPS);

        return stackPane;
    }

    /**
     * UPDATE
     * s'éxécute 60 fois par secondes
     */
    private void update() {
        //Si une partie à été lancée
        if (isGameStarted) {
            //Le jeu est en cours
            if (isGameRunning) {
                switch (selectedGameMode) {
                    case NORMAL:
                    case HARD:
                        //Le couple 0 bouge
                        couplesList.get(0).move(PIPE_SPEED);
                        //Le couple 1 bouge
                        if (timeSpend > 114) {
                            couplesList.get(1).move(PIPE_SPEED);
                        }
                        //le couple 2 bouge
                        if (timeSpend > 228) {
                            couplesList.get(2).move(PIPE_SPEED);
                        }
                        //le couple 3 bouge
                        if (timeSpend > 342) {
                            couplesList.get(3).move(PIPE_SPEED);
                        }
                        //le couple 4 bouge
                        if (timeSpend > 456) {
                            couplesList.get(4).move(PIPE_SPEED);
                        }
                        //l'oiseau vole
                        if (bird.isFlying()) {
                            bird.smoothFlap();
                        }
                        // L'oiseau subit en permanance la gravité quand le jeu est en cours
                        bird.undergoGravity(birdGravity);
                        // tue l'oiseau si trop haut ou trop bas
                        if (checkBounds(bird)) {
                            bird.kill();
                        }
                        // si l'oiseau touche un tuyau, il meurt
                        if (isAPipeTouched(couplesList, bird)) {
                            bird.kill();
                        }
                        //Tant que l'oiseau est en vie, compte les couples de tuyau
                        if (bird.isAlive()) {
                            coutingPipes();
                        } else {
                            endGame();
                            impactSound.play();
                        }
                        break;
                    case FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K:
                        if (score.getKills() <= 15) {
                            //Spawn d'asteroide
                            if (timeSpend % asteroidSpawnRate == 0) {
                                int randomSize = getRandomNumber(40, 150);
                                Asteroid asteroid = new Asteroid(getRandomNumber(700, 900), getRandomNumber(-300, 300), randomSize, randomSize, Color.TRANSPARENT, stackPane, IMG_DEFAULT_ASTEROID);
                                asteroidArrayList.add(asteroid);
                            }
                        } else {

                            bossManager.bossUpdate(stackPane, spaceBird);
                            Asteroid.killThemAll(asteroidArrayList, stackPane);
                            backgroundSpeed = BACKGROUND_BOSS_SPEED;
                        }

                        //Augmente la cadence d'apparition des astéroides
                        if (asteroidSpawnRate > 50) {
                            //toutes les 10 secondes
                            if (timeSpend % 600 == 0) {
                                asteroidSpawnRate -= 10;
                            }
                        }
                        //Bouger et vérifier si les astéroide sortent de l'écran
                        if (asteroidArrayList != null) {
                            for (Asteroid asteroid : asteroidArrayList) {
                                asteroid.moveLeft(asteroid.getSpeed());
                                if (asteroid.hasVerticalsMovements()) {
                                    asteroid.verticalMove();
                                }
                                asteroid.checkBounds();
                            }
                            //l'oiseau vole
                            if (spaceBird.isFlying()) {
                                spaceBird.smoothFlap();
                            }
                            // L'oiseau subit en permanance la gravité quand le jeu est en cours
                            spaceBird.undergoGravity(birdGravity);
                            // tue l'oiseau si trop haut ou trop bas
                            if (checkBounds(spaceBird)) {
                                spaceBird.kill();
                            }
                            //Tue l'oiseau s'il touche une astéroide
                            if (isAsteroidTouchedByBird(asteroidArrayList, spaceBird)) {
                                spaceBird.kill();
                            }
                            //Supprimer les astéroide
                            checkKills();
                            //Fin de partie
                            if (!spaceBird.isAlive()) {
                                endGame();
                                impactSound.play();
                            }
                        }
                        //Le cooldown se met à zéro
                        if (spaceBird.getReloadCooldown() > 0) {
                            spaceBird.decreaseReloadCooldown();
                        }

                        //Tire s'il y a des balle de le chargeur
                        spaceBird.shoot(Direction.RIGHT);
                        spaceBird.getSprite().toFront();
                        score.getText().toFront();
                        txtInformation.toFront();

                        break;
                }
                if (selectedGameMode == gameMode.HARD) {
                    couplesList.get(0).verticalPipeMove();
                    couplesList.get(1).verticalPipeMove();
                    couplesList.get(2).verticalPipeMove();
                    couplesList.get(3).verticalPipeMove();
                    couplesList.get(4).verticalPipeMove();
                }
                //Le fond d'écran bouge
                moveBackground(backgroundSpeed);

                //Incrément du temps (60 = 1 secondes)
                timeSpend += 1;
            }
            //Fin de partie
            else {
                //Timer sur le bouton SPACE
                replayTimer -= 1 / 60f;
                if (!isQPressed) {
                    if (replayTimer > 0) {
                        txtInformation.setText("[" + df.format(replayTimer) + "]" + TXT_END_GAME_MESSAGE);
                    } else {
                        txtInformation.setText("[SPACE] Rejouer" + TXT_END_GAME_MESSAGE);
                    }
                }
            }

        }
        //Une partie n'a pas été encore lancée
        else {
            txtInformation.setText(TXT_START_MESSAGE);
        }

    }

    /**
     * Créer une liste de couple de tuyaux
     *
     * @param couplesNumber nombre de couple voulu
     * @return une liste de couple de tuyaux
     */
    public ArrayList<PipeCouple> createCouplesList(int couplesNumber) {
        ArrayList<PipeCouple> list = new ArrayList<>();
        for (int i = 0; i < couplesNumber; i++) {
            list.add(
                    new PipeCouple(
                            //Les pipes sont formaté à leur création, donc pas besoin de donnée de position
                            //Parcontre la taille est importante vu que l'area d'un sprite se génére lors de la création
                            new Pipe(0, 0, 60, 700, Color.TRANSPARENT, stackPane, IMG_PIPE),
                            new Pipe(0, 0, 60, 700, Color.TRANSPARENT, stackPane, IMG_PIPE)
                    ));
        }
        return list;
    }

    /**
     * Parcours chaque tuyau de chaque couple de tuyaux présent dans la liste et vérifie s'il touche l'oiseau
     *
     * @param coupleList liste de couple de tuyaux
     * @param bird       qui peut ou non toucher un tuyau
     * @return true = touché // False = non touché
     */
    public boolean isAPipeTouched(ArrayList<PipeCouple> coupleList, Bird bird) {
        boolean isTouched = false;
        for (PipeCouple couple : coupleList) {
            if (Area.isHit(bird.getArea(), couple.topPipe.getArea()) || Area.isHit(bird.getArea(), couple.bottomPipe.getArea())) {
                isTouched = true;
            }
        }
        return isTouched;
    }

    /**
     * Vérifie si une astéroide est heurté par l'oiseau
     *
     * @param list      liste d'astéroide présents en jeu
     * @param spaceBird l'oiseau de l'espace
     * @return true = touché / false = pas touché
     */
    public boolean isAsteroidTouchedByBird(ArrayList<Asteroid> list, SpaceBird spaceBird) {
        boolean isTouched = false;
        for (Asteroid asteroid : list) {
            if (Area.isHit(spaceBird.getArea(), asteroid.getArea())) {
                isTouched = true;
            }
        }
        return isTouched;
    }

    /**
     * Vérifie si un astéroide à été touché par un projectile<br>
     * supprime l'astéroide touché<br>
     * incrémente le score
     */
    public void checkKills() {
        ArrayList<Asteroid> found = new ArrayList<>();

        for (Asteroid asteroid : asteroidArrayList) {
            if (asteroid.isHit(spaceBird.magazine)) {
                found.add(asteroid);
                score.incrementKills();
                asteroid.delete();
            }
        }
        asteroidArrayList.removeAll(found);
    }

    /**
     * Regarde si l'oiseau ne sort pas des limites vertical de la fenêtre
     *
     * @param bird Oiseau
     * @return true = l'oiseau sort de l'écran, false = il est toujours dedans
     */
    public boolean checkBounds(Bird bird) {
        return bird.getTranslateY() > MAX_HEIGHT / 2 + 100 || bird.getTranslateY() < -MAX_HEIGHT / 2 - 100;
    }

    /**
     * Compte chaque couple de pipe qui passe l'oiseau
     * si le couple passe par le Y de l'oiseau, il donne un point et passe en état "ne peuveunt plus donner de points"
     * dès qu'il reviennent à leur position de départ, ils peuvent a nouveau donner des points.
     * on en profite pour que tous les 10 points, le score score s'allume en jaune
     */
    public void coutingPipes() {
        for (PipeCouple couple : couplesList) {
            if (couple.topPipe.getTranslateX() < bird.getTranslateX()) {
                if (couple.CanGivePts()) {
                    if ((score.getPts() + 1) % 10 == 0 && score.getPts() > 1) {
                        score.getText().setFill(Color.GOLD);
                    } else {
                        score.getText().setFill(Color.WHITESMOKE);
                    }
                    incrementScore();
                    couple.setCanGivePts(false);
                }
            }
        }
    }

    /**
     * Incrémente et écrit le score
     */
    public void incrementScore() {
        score.incrementScore();
        score.write("Score : " + score.getPts());
    }

    /**
     * Replace les couples de tuyau, leur sprite ainsi que leur zone de collision à leur point de départ
     */
    public void resetPipe() {
        for (PipeCouple couple : couplesList) {
            couple.formatCouples();
            couple.topPipe.refreshCoord();
            couple.bottomPipe.refreshCoord();
        }
    }

    /**
     * Lance le jeu pour la première fois
     */
    public void startGame() {
        //déclare le jeu comme en cours
        isGameRunning = true;
        //déclare le jeu comme lancé
        isGameStarted = true;
        //Enlève le text d'info
        txtInformation.setVisible(false);
        //Affiche le score
        switch (selectedGameMode) {
            case NORMAL:
            case HARD:
                score.getText().setVisible(true);
                break;
            case FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K:
                score.getText().setVisible(false);
                break;
        }
    }

    /**
     * Fin du jeu
     * L'oiseau est réanimé et arrête de voler
     * L'oiseau, son sprite ainsi que sa zone de collision sont remis à leur position de départ
     * Le score et un message d'info pour rejouer s'affiche au milleu de l'écran
     */
    public void endGame() {
        //remet les compteurs à 0
        timeSpend = 0;

        //Si le score n'a pas été encore inscrit
        if (!scoreHasBeenWrited) {
            //Écrit le score dans le fichier du mode jouer et met à jour le tablau des scores
            switch (selectedGameMode) {
                case NORMAL:
                    ScoreBoard.writeInTxtFile(scoreFile, new PlayerScore(playerName, score.getPts()));
                    txtScoreBoard.setText(TXT_SCORES_TITLE + ScoreBoard.getscoreBoard(scoreFile));
                    //Affichage du score
                    StackPane.setAlignment(score.getText(), Pos.CENTER);
                    break;
                case HARD:
                    ScoreBoard.writeInTxtFile(hardModeScoreFile, new PlayerScore(playerName, score.getPts()));
                    txtScoreBoard.setText(TXT_HARDMODE_SCORES_TITLE + ScoreBoard.getscoreBoard(hardModeScoreFile));
                    //Affichage du score
                    StackPane.setAlignment(score.getText(), Pos.CENTER);
                    break;
                case FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K:
                    //ScoreBoard.writeInTxtFile(thirdModeFile, new PlayerScore(playerName, score.getPts()));
                    //txtScoreBoard.setText(TXT_THIRDMODE_SCORES_TITLE + ScoreBoard.getscoreBoard(thirdModeFile));
                    txtScoreBoard.setText(" ");
                    //Faire disparâitre tout les astéroïdes
                    Asteroid.killThemAll(asteroidArrayList, stackPane);
                    //Vider le chargeur
                    spaceBird.emptyMagazine();
                    if (bossManager.boss != null) {
                        bossManager.destroyBoss();
                        bossManager.setBossDead(false);
                    }
                    //Remettre la fréquence d'aparition des astéroide à la normale
                    asteroidSpawnRate = ASTEROID_SPAWN_RATE;
                    bossManager.setBossPhases(BossManager.Phases.CREATION);
                    break;

            }
            asteroidArrayList = new ArrayList<>();
            isGameRunning = false;
            replayTimer = 3;
        }
        //Affichage du score
        score.getText().setFill(Color.WHITESMOKE);
        score.getText().setStroke(Color.BLACK);
        //Les score sont déclaré comme écrit et ne seront pas réécrit
        scoreHasBeenWrited = true;
        //Affichage d'information
        txtInformation.setVisible(true);
        backgroundSpeed = BACKGROUND_SPEED;
    }


    /**
     * Redémarre le jeu
     */
    public void restartGame() {
        resetScore();
        //Reinitialisation des tuyaux
        resetPipe();

        //Supprimer les anciens oiseaux s'ils existent
        if (stackPane.getChildren().contains(bird)) {
            bird.delete();
        }
        if (stackPane.getChildren().contains(spaceBird)) {
            spaceBird.delete();
        }

        switch (selectedGameMode) {
            case NORMAL:
            case HARD:
                bird = new Bird(-250, 0, 35, 35, Color.TRANSPARENT, stackPane, IMG_FLAPPY);
                break;
            case FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K:
                spaceBird = new SpaceBird(-250, 0, 35, 35, Color.TRANSPARENT, stackPane, IMG_SPACE_FLAPPY);
                thirdModeLogo.setVisible(false);
                break;
        }
        refreshSmoothFlapVariable();
        //Le jeu est à nouveau déclaré comme "en cours"
        isGameRunning = true;
        scoreHasBeenWrited = false;
    }

    /**
     * Replace le score au bon endroit et le remet à zéro
     */
    public void resetScore() {
        score.resetScore();
        score.resetKills();
        score.getText().setStroke(Color.BLACK);
        score.getText().setFill(Color.WHITESMOKE);
        StackPane.setAlignment(score.getText(), Pos.TOP_LEFT);
        txtInformation.setVisible(false);
    }

    /**
     * Bouge une liste de backgrounds de droit à gauche, s'ils sortent totalement de la fenêtre, ils sont replacés
     * à droite
     *
     * @param speed vitesse de défilement du background
     */
    public void moveBackground(int speed) {
        // Les backgrounds bougent
        backgroundList.get(1).setTranslateX(backgroundList.get(1).getTranslateX() - speed);
        backgroundList.get(0).setTranslateX(backgroundList.get(0).getTranslateX() - speed);
        //Replace les background s'il sort de la fenêtre
        for (ImageView background : backgroundList) {
            if (background.getTranslateX() <= -MAX_WIDTH) {
                background.setTranslateX(MAX_WIDTH);
            }
        }
    }

    /**
     * Effectue un changement entre chacun des modes
     *
     * @param selectedGameMode mode de jeu sélectionné
     */
    public void switchBetweenModesDisplay(gameMode selectedGameMode) {
        switch (selectedGameMode) {
            case NORMAL:
                backgroundList.get(0).setImage(new Image(PATH_DIR_SPRITES + "cloudbg1.png"));
                backgroundList.get(1).setImage(new Image(PATH_DIR_SPRITES + "cloudbg2.png"));

                if (scoreFile.exists()) {
                    txtScoreBoard.setText(TXT_SCORES_TITLE + ScoreBoard.getscoreBoard(scoreFile));
                } else {
                    txtScoreBoard.setText("");
                }

                score.getText().setVisible(true);
                break;

            case HARD:
                if (hardModeScoreFile.exists()) {
                    txtScoreBoard.setText(TXT_HARDMODE_SCORES_TITLE + ScoreBoard.getscoreBoard(hardModeScoreFile));
                } else {
                    txtScoreBoard.setText("");
                }
                break;

            case FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K:
                backgroundList.get(0).setImage(new Image(PATH_DIR_SPRITES + "spacebg1.png"));
                backgroundList.get(1).setImage(new Image(PATH_DIR_SPRITES + "spacebg2.png"));

                if (thirdModeFile.exists()) {
                    //txtScoreBoard.setText(TXT_THIRDMODE_SCORES_TITLE + ScoreBoard.getscoreBoard(thirdModeFile));
                    txtScoreBoard.setText(" ");
                } else {
                    txtScoreBoard.setText("");
                }

                score.getText().setVisible(false);
                break;
        }
    }

    /**
     * Pour chaque mode de jeu, change les variables concernant le bon déroulement du SmoothJump
     */
    public void refreshSmoothFlapVariable() {
        switch (selectedGameMode) {
            case NORMAL:
                birdGravity = BIRD_GRAVITY_NORMAL;
                bird.setMomentum(BIRD_MOMENTUM_NORMAL_HARD);
                bird.setLossMomentum(BIRD_LOSSMOMENTUM_NORMAL_HARD);
                break;
            case HARD:
                birdGravity = BIRD_GRAVITY_HARD;
                bird.setMomentum(BIRD_MOMENTUM_NORMAL_HARD);
                bird.setLossMomentum(BIRD_LOSSMOMENTUM_NORMAL_HARD);
                break;
            case FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K:
                birdGravity = SPACEBIRD_GRAVITY;
                spaceBird.setMomentum(SPACEBIRD_MOMENTUM);
                spaceBird.setLossMomentum(SPACEBIRD_LOSSMOMENTUM);
                break;
        }
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

