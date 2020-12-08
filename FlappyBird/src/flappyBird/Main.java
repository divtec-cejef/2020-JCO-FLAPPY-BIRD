package flappyBird;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

import static flappyBird.Constant.*;


/**
 * @author Louis Bovay
 * @version 3.0 - 04.11.2020
 * FLAPPY BIRD
 * Projet d'atelier 26.10.2020 au 16.12.2020
 * "Création d'un petit jeu en programmation avancée"
 */
public class Main extends Application {

    //Indice de temps, 1 = 60 frame
    double t = 0;
    //Indice de frame, 60 = 1 seconde
    int frame = 0;
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
    //Gravité appliquée à l'oiseau (8 jeu normal, 10 avec les tuyaux qui bougent)
    int birdGravity = 8;
    //Pane principale
    StackPane root = new StackPane();
    //L'oiseau
    flappyBird.Bird bird = new flappyBird.Bird(-250, 0, 35, 35, Color.TRANSPARENT);
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
    double replayTimer = 6;
    //format des décimal
    DecimalFormat df = new DecimalFormat("#");
    //fichiers musicaux
    Sound flapSound = new Sound(PATH_DIR_FLAP_SOUNDS);
    Sound impactSound = new Sound(PATH_DIR_IMPACT_SOUNDS);
    //Ennemis
    Asteroid ennemi;

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

        //Initialisé la liste de couple de tuyau
        couplesList = createCouplesList(6);

        //Création d'une scène utilisant la pane
        Scene scene = new Scene(createContent());

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
                        //l'oiseau vole
                        bird.setFlying(true);
                        //l'oiseau repprend son élan
                        switch(selectedGameMode){
                            case NORMAL:
                            case HARD:
                                bird.setMomentum(19.5f);
                                break;
                            case FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K:
                                bird.setMomentum(15f);
                                break;
                        }
                        //on change le sprite de l'oiseau
                        bird.getBirdSprite().setImage(new Image(IMG_FLAPPY));
                        //l'Input ne sera fait q'une seule fois, pour le refaire il faut lacher espace, et réappuyer
                        isSpacePressed = true;
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
                        if (selectedGameMode == gameMode.FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K) {
                            thirdModeLogo.setVisible(false);
                        }
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
                //Si G est appuyé, active/désactive le hardmode
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
                    switchBetweenModes(selectedGameMode);
                    skull.setVisible(selectedGameMode == gameMode.HARD);
                    thirdModeLogo.setVisible(selectedGameMode == gameMode.FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K);
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
                isSpacePressed = false;
                bird.getBirdSprite().setImage(new Image(IMG_FLAPPY_FLAP));
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
        root.getChildren().add(backgroundList.get(1));
        root.getChildren().add(backgroundList.get(0));
        //Ajout de tout les couple de tuyaux
        addCouples(root);
        //Ajout de l'oiseau
        root.getChildren().add(bird);
        root.getChildren().add(bird.getBirdSprite());
        //Ajout du score
        root.getChildren().add(score.getText());
        //Ajout du text d'info
        root.getChildren().add(txtInformation);
        //Ajout de l'image du hardmode
        root.getChildren().add(skull);
        //Ajout le logo du 3eme mode
        root.getChildren().add(thirdModeLogo);
        //Ajout du tableau de score
        root.getChildren().add(txtScoreBoard);
        if (scoreFile.exists()) {
            txtScoreBoard.setText(TXT_SCORES_TITLE + ScoreBoard.getscoreBoard(scoreFile));
        }

        ennemi = new Asteroid(600,0,100,100,Color.RED,root);



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
        // Lancer la scène
        stage.show();
    }

    /**
     * Initialisation de la pane
     *
     * @return root La Stackpane à utiliser dans la scene
     */
    private Parent createContent() {
        //ID de la pane
        root.setId("pane");
        //Timer basé sur 1 seconde
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> update()));
        //nombbre de fois qu'elle se répète
        timeline.setCycleCount(Animation.INDEFINITE);
        //lancer la timeline
        timeline.play();
        // vitesse 60 = 60 images par seconde
        timeline.setRate(FPS);

        return root;
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
                        break;

                    case HARD:
                        couplesList.get(0).verticalPipeMove();
                        couplesList.get(1).verticalPipeMove();
                        couplesList.get(2).verticalPipeMove();
                        couplesList.get(3).verticalPipeMove();
                        couplesList.get(4).verticalPipeMove();
                        break;

                    case FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K:
                        ennemi.moveLeft(3);
                        break;
                }
                if(selectedGameMode == gameMode.NORMAL || selectedGameMode == gameMode.HARD){
                    //Le couple 0 bouge
                    couplesList.get(0).move(PIPE_SPEED);
                    //Le couple 1 bouge
                    if (t > 1.9) {
                        couplesList.get(1).move(PIPE_SPEED);
                    }
                    //le couple 2 bouge
                    if (t > 3.8) {
                        couplesList.get(2).move(PIPE_SPEED);
                    }
                    //le couple 3 bouge
                    if (t > 5.7) {
                        couplesList.get(3).move(PIPE_SPEED);
                    }
                    //le couple 4 bouge
                    if (t > 7.6) {
                        couplesList.get(4).move(PIPE_SPEED);
                    }
                }
                //Le fond d'écran bouge
                moveBackground();

                //l'oiseau vole
                if (bird.isFlying()) {
                    bird.smoothFlap();
                }
                // L'oiseau subit en permanance la gravité quand le jeu est en cours
                bird.undergoGravity(birdGravity);


                // tue l'oiseau si trop haut ou trop bas
                if (checkBounds()) {
                    bird.kill();
                }

                // si l'oiseau touche un tuyau, il meurt
                if (isAPipeTouched(couplesList, bird)) {
                    bird.kill();
                }

                //Tant que l'oiseau est en vie, compte les couples de tuyau
                if (bird.isAlive()) {
                    switch (selectedGameMode){
                        case NORMAL:
                        case HARD:
                            coutingPipes();
                            break;
                        case FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K:
                            break;
                    }
                } else {
                    endGame();
                    impactSound.play();
                }

                //Incrémentation des indice de frame et de temps
                //Quand t atteint 1, une seconde sera écoulée
                //Quand frame atteint 60, une seconde sera écoulée
                t += 1 / 60f;
                frame += 1;
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
            //De base, place l'oiseau au milleu gauche de l'écran
            bird.refreshBirdSprite();
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
                            new Pipe(0, 0, 60, 700, Color.TRANSPARENT),
                            new Pipe(0, 0, 60, 700, Color.TRANSPARENT)
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
    public boolean isAPipeTouched(ArrayList<PipeCouple> coupleList, flappyBird.Bird bird) {
        boolean isTouched = false;
        for (PipeCouple couple : coupleList) {
            if (couple.topPipe.isHit(bird) || couple.bottomPipe.isHit(bird)) {
                isTouched = true;
            }
        }
        return isTouched;
    }

    /**
     * Ajout chaque tuyaux de chaque couple à la pane
     *
     * @param root la pane
     */
    public void addCouples(StackPane root) {
        for (PipeCouple couple : couplesList) {
            root.getChildren().add(couple.topPipe);
            root.getChildren().add(couple.topPipe.getPipeSprite());
            root.getChildren().add(couple.bottomPipe);
            root.getChildren().add(couple.bottomPipe.getPipeSprite());
        }
    }

    /**
     * Regarde si l'oiseau ne sort pas des limites vertical de la fenêtre
     *
     * @return true = l'oiseau sort de l'écran, false = il est toujours dedans
     */
    public boolean checkBounds() {
        return bird.getTranslateY() > MAX_HEIGHT / 2 || bird.getTranslateY() < -MAX_HEIGHT / 2;
    }

    /**
     * Compte chaque couple de pipe qui passe l'oiseau
     * si le couple passe par le Y de l'oiseau, il donne un point et passe en état "ne peuveunt plus donner de points"
     * dès qu'il reviennent à leur position de départ, ils peuvent a nouveau donner des points.
     * on en profite pour que tous les 10 points, le score score s'allume en jaune
     * IncreaseSpeed augmenet la vitesse des tuyaux de 1 tout les 10 points
     */
    public void coutingPipes() {
        for (PipeCouple couple : couplesList) {
            if (couple.topPipe.getTranslateX() < bird.getTranslateX()) {
                if (couple.CanGivePts()) {
                    if ((score.getPts() + 1) % 10 == 0 && score.getPts() > 1) {
                        score.getText().setFill(Color.GOLD);
                        //increaseSpeed();
                    } else {
                        score.getText().setFill(Color.WHITESMOKE);
                    }
                    score.incrementScore();
                    score.write("Score : " + score.getPts());
                    couple.setCanGivePts(false);
                }
            }
        }
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
        score.getText().setVisible(true);
    }

    /**
     * Fin du jeu
     * L'oiseau est réanimé et arrête de voler
     * L'oiseau, son sprite ainsi que sa zone de collision sont remis à leur position de départ
     * Le score et un message d'info pour rejouer s'affiche au milleu de l'écran
     */
    public void endGame() {
        //remet les compteurs à 0
        t = 0;
        frame = 0;

        //Si le score n'a pas été encore inscrit
        if (!scoreHasBeenWrited) {
            //Écrit le score dans le fichier du mode jouer et met à jour le tablau des scores
            switch (selectedGameMode) {
                case NORMAL:
                    ScoreBoard.writeInTxtFile(scoreFile, Integer.toString(score.getPts()));
                    txtScoreBoard.setText(TXT_SCORES_TITLE + ScoreBoard.getscoreBoard(scoreFile));
                    break;
                case HARD:
                    ScoreBoard.writeInTxtFile(hardModeScoreFile, Integer.toString(score.getPts()));
                    txtScoreBoard.setText(TXT_HARDMODE_SCORES_TITLE + ScoreBoard.getscoreBoard(hardModeScoreFile));
                    break;
                case FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K:
                    ScoreBoard.writeInTxtFile(thirdModeFile, Integer.toString(score.getPts()));
                    txtScoreBoard.setText(TXT_THIRDMODE_SCORES_TITLE + ScoreBoard.getscoreBoard(thirdModeFile));
                    break;

            }
            isGameRunning = false;
            replayTimer = 3;
        }
        //Affichage du score
        StackPane.setAlignment(score.getText(), Pos.CENTER);
        score.getText().setFill(Color.WHITESMOKE);
        score.getText().setStroke(Color.BLACK);
        //Les score sont déclaré comme écrit et ne seront pas réécrit
        scoreHasBeenWrited = true;
        //Affichage d'information
        txtInformation.setVisible(true);
    }

    /**
     * Redémarre le jeu
     */
    public void restartGame() {
        resetScore();
        //Reinitialisation des tuyaux
        resetPipe();
        //Réssucite l'oiseau
        bird.revive();
        //Si l'oiseau était en vole, le stop
        bird.setFlying(false);
        //Replacer l'oiseau
        bird.setTranslateY(-100);
        bird.refreshBirdSprite();
        bird.refreshCoord();
        bird.getBirdSprite().setRotate(0);
        if (selectedGameMode == gameMode.FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K) {
            thirdModeLogo.setVisible(false);
        }
        //Le jeu est à nouveau déclaré comme "en cours"
        isGameRunning = true;
        scoreHasBeenWrited = false;
    }

    /**
     * Replace le score au bon endroit et le remet à zéro
     */
    public void resetScore() {
        score.resetScore();
        score.getText().setStroke(Color.BLACK);
        score.getText().setFill(Color.WHITESMOKE);
        StackPane.setAlignment(score.getText(), Pos.TOP_LEFT);
        txtInformation.setVisible(false);
    }

    /**
     * Bouge une liste de backgrounds de droit à gauche, s'ils sortent totalement de la fenêtre, ils sont replacés
     * à droite
     */
    public void moveBackground() {
        // Les backgrounds bougent
        backgroundList.get(0).setTranslateX(backgroundList.get(0).getTranslateX() - 1);
        backgroundList.get(1).setTranslateX(backgroundList.get(1).getTranslateX() - 1);
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
    public void switchBetweenModes(gameMode selectedGameMode) {
        switch (selectedGameMode) {

            case NORMAL:
                backgroundList.get(0).setImage(new Image(PATH_DIR_SPRITES + "cloudbg1.png"));
                backgroundList.get(1).setImage(new Image(PATH_DIR_SPRITES + "cloudbg2.png"));

                if (scoreFile.exists()) {
                    txtScoreBoard.setText(TXT_SCORES_TITLE + ScoreBoard.getscoreBoard(scoreFile));
                } else {
                    txtScoreBoard.setText("");
                }
                birdGravity = 8;
                break;

            case HARD:
                if (hardModeScoreFile.exists()) {
                    txtScoreBoard.setText(TXT_HARDMODE_SCORES_TITLE + ScoreBoard.getscoreBoard(hardModeScoreFile));
                } else {
                    txtScoreBoard.setText("");
                }
                birdGravity = 9;
                break;

            case FLAPPY_BIRD_AGAINST_SPACE_VILLAINS_II_4K:
                backgroundList.get(0).setImage(new Image(PATH_DIR_SPRITES + "spacebg1.png"));
                backgroundList.get(1).setImage(new Image(PATH_DIR_SPRITES + "spacebg2.png"));

                if (thirdModeFile.exists()) {
                    txtScoreBoard.setText(TXT_THIRDMODE_SCORES_TITLE + ScoreBoard.getscoreBoard(thirdModeFile));
                } else {
                    txtScoreBoard.setText("");
                }
                birdGravity = 7;
                bird.setLossMomentum(0.2f);
                break;
        }
    }
}

