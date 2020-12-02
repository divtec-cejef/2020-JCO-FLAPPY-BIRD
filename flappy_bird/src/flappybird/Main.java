package flappybird;

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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;

import static flappybird.Constant.*;


/**
 * @author Louis Bovay
 * @version 3.0 - 04.11.2020
 * FLAPPY BIRD
 * Projet d'atelier 26.10.2020 au 16.12.2020
 * "Création d'un petit jeu en programmation avancée"
 */
public class Main extends Application {

    //Indice de temps, 1 = 60 frame
    float t = 0;
    //Indice de frame, 60 = 1 seconde
    int frame = 0;
    //Si oui ou non la barre espace est appuyée
    boolean isSpacePressed = false;
    //Si oui ou non la touche R est appuyée
    boolean isRPressed = false;
    //Si oui ou non la touche Q est appuyée
    boolean isQPressed = false;
    //Si oui ou non le jeu est en cours
    boolean isGameRunning = false;
    //Si oui ou non le jeu à déjà été lancé
    boolean isGameStarted = false;
    //Si oui ou non le mode difficile est activé
    boolean isHardMode = false;
    //Si oui ou non le score à été écrit
    boolean scoreHasBeenWrited = false;
    //Gravité appliquée à l'oiseau (8 jeu normal, 10 avec les tuyaux qui bougent)
    int birdGravity = 8;
    //Pane principale
    StackPane root = new StackPane();
    //L'oiseau
    Bird bird = new Bird(-250, 0, 35, 35, Color.TRANSPARENT);
    //LeScore
    Score score = new Score(0, 0);
    //Text d'information
    Text txtInformation = new Text(0, 0, "[SPACE] Commencer");
    //Liste de couple de tuyau
    ArrayList<PipeCouple> couplesList;
    // Background
    ArrayList<ImageView> backgroundList = new ArrayList<>();
    //Image du hardmode
    ImageView skull = new ImageView(new Image(PATH_DIR_SPRITES + "skull.png"));
    //Fichier de score
    File scoreFile = new File(PATH_FILE_SCORES);
    //Fichier de score du hardmode
    File hardModeScoreFile = new File(PATH_FILE_SCORES_HARDMODE);
    //Tableau de score
    Text txtScoreBoard = new Text(0, 0, "");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        //Initialisé la liste de couple de tuyau
        couplesList = createCouplesList(6);

        //Création d'une scène utilisant la pane
        Scene scene = new Scene(createContent());

        //Initialisation du score
        score.write("Score : " + score.getPts());
        score.getText().setStroke(Color.WHITESMOKE);
        score.getText().setFill(Color.DARKBLUE);
        score.getText().setTextAlignment(TextAlignment.LEFT);
        StackPane.setAlignment(score.getText(), Pos.TOP_LEFT);
        score.getText().setVisible(false);

        //Initialisation du text d'information
        txtInformation.setFont(Font.font("Berlin Sans FB", FontWeight.BOLD, FontPosture.REGULAR, 50));
        txtInformation.setTextAlignment(TextAlignment.CENTER);
        StackPane.setAlignment(txtInformation, Pos.CENTER);
        txtInformation.setStroke(Color.ORANGE);
        txtInformation.setFill(Color.ORANGERED);
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
        //initialisation du tableau des score
        txtScoreBoard.setFont(Font.font("Berlin Sans FB", FontWeight.BOLD, FontPosture.REGULAR, 50));
        txtScoreBoard.setFill(Color.GOLD);
        txtScoreBoard.setStroke(Color.DARKCYAN);
        StackPane.setAlignment(txtScoreBoard, Pos.TOP_LEFT);
        txtScoreBoard.setTranslateX(txtScoreBoard.getTranslateX() + 30);
        txtScoreBoard.setTranslateY(txtScoreBoard.getTranslateY() + 30);
        txtScoreBoard.setVisible(false);

        //Si SPACE est appuyé
        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.SPACE)) {
                if (!isSpacePressed) {
                    if (isGameRunning) {
                        //l'oiseau vole
                        bird.setFlying(true);
                        //l'oiseau repprend son élan
                        bird.setMomentum(BIRD_MOMENTUM);
                        //on change le sprite de l'oiseau
                        bird.getBirdSprite().setImage(new Image(IMG_FLAPPY));
                        //l'Input ne sera fait q'une seule fois, pour le refaire il faut lacher espace, et réappuyer
                        isSpacePressed = true;
                    }
                    if (!isGameStarted) {
                        startGame();
                    }
                }
            }
            //N'est pas disponnible en cours de jeu
            if (!isGameRunning) {
                //Si R est appuyé
                if (keyCode.equals(KeyCode.R)) {
                    if (!isRPressed) {
                        isRPressed = true;
                    }
                }
                // Si Q est appuyé, ouvrir le menu de confirmation
                if (keyCode.equals(KeyCode.Q)) {
                    isQPressed = true;
                    txtInformation.setText(TXT_ALERT_MESSAGE);
                }
                // Si Y est appuyé, fermer l'application
                if (keyCode.equals(KeyCode.Y)) {
                    stage.close();
                }
                // Si N est appuyé, relancer l'instance de fin de jeu
                if (keyCode.equals(KeyCode.N)) {
                    if (isQPressed) {
                        isQPressed = false;
                        endGame();
                    }
                }
                //Si G est appuyé, active/désactive le hardmode
                if (keyCode.equals(KeyCode.G)) {
                    isHardMode = !isHardMode;
                    skull.setVisible(isHardMode);
                    if (isHardMode) {
                        if (hardModeScoreFile.exists()) {
                            txtScoreBoard.setText(TXT_HARDMODE_SCORES_TITLE + ScoreBoard.getscoreBoard(hardModeScoreFile));
                        }else{
                            txtScoreBoard.setText("");
                        }
                    } else {
                        txtScoreBoard.setText(TXT_SCORES_TITLE + ScoreBoard.getscoreBoard(scoreFile));
                    }
                }
                //Si TAB est appuyé, active/désactive le hardmode
                if (keyCode.equals(KeyCode.TAB)) {
                    txtScoreBoard.setVisible(true);
                }
            }
        });


        //Si SPACE est relâché
        scene.setOnKeyReleased(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.SPACE)) {
                isSpacePressed = false;
                bird.getBirdSprite().setImage(new Image(IMG_FLAPPY_FLAP));
            }
            //N'est pas disponnible en cours de jeu
            if (!isGameRunning) {
                //Si R est relâché
                if (keyCode.equals(KeyCode.R)) {
                    restartGame();
                }
                //Si TAB est relâché
                if (keyCode.equals(KeyCode.TAB)) {
                    txtScoreBoard.setVisible(false);
                }
            }
        });

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
        //Ajout du tableau de score
        root.getChildren().add(txtScoreBoard);
        if (scoreFile.exists()) {
            txtScoreBoard.setText(TXT_SCORES_TITLE + ScoreBoard.getscoreBoard(scoreFile));
        }
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
     * Update
     * s'éxécute 60 fois par secondes
     */
    private void update() {
        if (!isGameRunning) {
            //De base, place l'oiseau au milleu gauche de l'écran
            bird.refreshBirdSprite();
        }
        //Le jeu est en cours
        else {
            moveBackground();

            //l'oiseau vole
            if (bird.isFlying()) {
                bird.smoothFlap();
            }
            // L'oiseau subit en permanance la gravité quand le jeu est en cours
            bird.undergoGravity(birdGravity);

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

            if (isHardMode) {
                birdGravity = 9;
                couplesList.get(0).verticalPipeMove();
                couplesList.get(1).verticalPipeMove();
                couplesList.get(2).verticalPipeMove();
                couplesList.get(3).verticalPipeMove();
                couplesList.get(4).verticalPipeMove();
            } else {
                birdGravity = 8;
            }

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
                coutingPipes();
            } else {
                endGame();
            }

            //Incrémentation des indice de frame et de temps
            //Quand t atteint 1, une seconde sera écoulée
            //Quand frame atteint 60, une seconde sera écoulée
            t += 0.01666;
            frame++;
        }
    }

    //Initialisation de la pane
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
    public boolean isAPipeTouched(ArrayList<PipeCouple> coupleList, Bird bird) {
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
                        score.getText().setFill(Color.DARKBLUE);
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
        //remet le timer de poche à 0
        t = 0;
        frame = 0;
        //Affichage du score
        StackPane.setAlignment(score.getText(), Pos.CENTER);
        score.getText().setFill(Color.WHITESMOKE);
        score.getText().setStroke(Color.BLACK);

        //Affichage d'information
        txtInformation.setText(TXT_END_GAME_MESSAGE);
        txtInformation.setVisible(true);
        //Le jeu est déclarer comme arrêté
        isGameRunning = false;

        if (!scoreHasBeenWrited) {
            if (isHardMode) {
                ScoreBoard.writeInTxtFile(hardModeScoreFile, Integer.toString(score.getPts()));
                txtScoreBoard.setText(TXT_HARDMODE_SCORES_TITLE + ScoreBoard.getscoreBoard(hardModeScoreFile));
            }else {
                ScoreBoard.writeInTxtFile(scoreFile, Integer.toString(score.getPts()));
                txtScoreBoard.setText(TXT_SCORES_TITLE + ScoreBoard.getscoreBoard(scoreFile));
            }
        }
        scoreHasBeenWrited = true;

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
        bird.setTranslateY(0);
        bird.refreshBirdSprite();
        bird.refreshCoord();
        //Le jeu est à nouveau déclarer comme "en cours"
        isGameRunning = true;

        scoreHasBeenWrited = false;
    }

    /**
     * Replace le score au bonne endroit et le remet à zéro
     */
    public void resetScore() {
        score.resetScore();
        score.getText().setStroke(Color.WHITESMOKE);
        score.getText().setFill(Color.DARKBLUE);
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

}

