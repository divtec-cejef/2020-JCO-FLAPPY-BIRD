package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * @author Louis Bovay
 * @version 3.0 - 04.11.2020
 * FLAPPY BIRD
 * Projet d'atelier 26.10.2020 au 16.12.2020
 * "Création d'un petit jeu en programmation avancée"
 */
public class Main extends Application {

    //Indice de temps
    float t = 0;
    //Si oui ou non la barre espace est appuyée
    boolean isSpacePressed = false;
    //Si oui ou non la touche R est appuyée
    boolean isRPressed = false;
    //Si oui ou non le jeu est en cours
    boolean isGameRunning = false;
    //Si oui ou non le jeu à déjà été lancé
    boolean isGameStarted = false;
    //Si oui ou non la phase d'arrêt est lancée
    boolean isGameAboutToStop = false;
    //Hauteur max de la fenêtre
    final float MAX_HEIGHT = 700;
    //Largeur max de la fenêtre
    final float MAX_WIDTH = 1000;
    //Pane principale
    StackPane root = new StackPane();
    //L'oiseau
    Bird bird = new Bird(-250, 0, 30, 30, Color.TRANSPARENT);
    //LeScore
    Score score = new Score(-400, -320);
    //Text d'information
    Text txtInformation = new Text(0, 0, "Appuyez sur -> SPACE <-");
    //Liste de couple de tuyau
    ArrayList<PipeCouple> couplesList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        //Initialisé la liste de couple de tuyau
        couplesList = createCouplesList(4);

        //Création d'une scène utilisant la pane
        Scene scene = new Scene(createContent());

        //Initialisation du score
        score.write("Score : " + score.getPts());
        score.getText().setStroke(Color.WHITESMOKE);
        score.getText().setFill(Color.TRANSPARENT);
        score.getText().setVisible(false);

        //Initialisation du text d'information
        txtInformation.setFont(Font.font("Berlin Sans FB", FontWeight.BOLD, FontPosture.REGULAR, 50));
        txtInformation.setTextAlignment(TextAlignment.CENTER);
        root.setAlignment(txtInformation, Pos.CENTER);
        txtInformation.setStroke(Color.ORANGE);
        txtInformation.setFill(Color.ORANGERED);
        txtInformation.setTranslateY(200);

        //Si SPACE est appuyé
        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.SPACE)) {
                if (!isSpacePressed) {
                    if (isGameRunning) {
                        //l'oiseau vole
                        bird.setFlying(true);
                        //point de départ et d'arrivé doivent être mis en place
                        bird.setStartAndGoalAreSetup(false);
                        //on change le sprite de l'oiseau
                        bird.getBirdSprite().setImage(new Image("Sprites/flappy.png"));
                        //l'Input ne sera fait quune seule fois, pour le refaire il faut lacher espace, et réappuyer
                        isSpacePressed = true;
                    }
                    if (!isGameStarted) {
                        startGame();
                    }
                }
            }
            //Si R est appuyé
            if (keyCode.equals(KeyCode.R)) {
                //N'est pas disponnbile lorsque le jeu est en cours
                if (!isGameRunning) {
                    if (!isRPressed) {
                        isRPressed = true;
                    }
                }
            }
            if (keyCode.equals(KeyCode.Q)) {
                if (!isGameRunning) {
                    txtInformation.setText("Voulez-vous vraiment quitter ?\n-> Y <- OUI\n-> N <- NON");
                    //stage.close();
                }
            }
        });

        //Si SPACE est relâché
        scene.setOnKeyReleased(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.SPACE)) {
                isSpacePressed = false;
                bird.getBirdSprite().setImage(new Image("Sprites/flappyFlap.png"));
            }
            //Si R est relâché
            if (keyCode.equals(KeyCode.R)) {
                //N'est pas disponnble lorsque le joue est en cours
                if (!isGameRunning) {
                    restartGame();
                }
            }
        });


        //Ajout de tout les couple de tuyaux
        addCouples(root);
        //Ajout de l'oiseau
        root.getChildren().add(bird);
        root.getChildren().add(bird.getBirdSprite());
        //Ajout du score
        root.getChildren().add(score.getText());
        //Ajout du text d'info
        root.getChildren().add(txtInformation);

        //Récupération de la feuille de style css
        scene.getStylesheets().add("css/style.css");
        // Sélectionner la scene
        stage.setScene(scene);
        // Donner une titre à la scène
        stage.setTitle("FlappyBird");
        //rendre la fenetre non redimentionnable
        stage.setResizable(false);
        // Lancer la scène
        stage.show();
    }

    private void update() {
        if (!isGameRunning) {
            //De base, place l'oiseau au milleur gauche de l'écran
            bird.refreshBirdSprite();
        } else {

            //timer de poche
            if (t < 1) {
                t += 0.0016;
            }

            //Le couple 0 bouge
            couplesList.get(0).move();

            //L'oiseau subit la gravité s'il n'est pas en train de volé
            if (!bird.isFlying()) {
                bird.undergoGravity(5);
            } else {
                bird.flap2();
            }

            //Le couple 1 bouge
            if (t > 0.100) {
                couplesList.get(1).move();
            }

            //Le couple 2 bouge
            if (t > 0.200) {
                couplesList.get(2).move();
            }

            //Le couple 3 bouge
            if (t > 0.300) {
                couplesList.get(3).move();
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
        }
    }

    //Initialisation de la pane
    private Parent createContent() {
        root.setPrefSize(MAX_WIDTH, MAX_HEIGHT);
        root.setId("pane");

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();

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
     * @return ture = touché // False = non touché
     */
    public boolean isAPipeTouched(ArrayList<PipeCouple> coupleList, Bird bird) {
        boolean isTouched = false;
        for (PipeCouple couple : coupleList) {
            if (couple.pipe1.isHit(bird) || couple.pipe2.isHit(bird)) {
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
            root.getChildren().add(couple.pipe1);
            root.getChildren().add(couple.pipe1.getPipeSprite());
            root.getChildren().add(couple.pipe2);
            root.getChildren().add(couple.pipe2.getPipeSprite());
        }
    }

    /**
     * Regarde si l'oiseau ne sort pas des limites vertical de la fenêtre
     *
     * @return true = l'oiseau sort de l'écran, false = il est toujours dedans
     */
    public boolean checkBounds() {
        return bird.getTranslateY() > 350 || bird.getTranslateY() < -350;
    }

    /**
     * Compte chaque couple de pipe qui passe l'oiseau
     * si le couple passe par le Y de l'oiseau, il donne un point et passe en état "ne peuveunt plus donner de points"
     * dès qu'il reviennent à leur position de départ, ils peuvent a nouveau donner des points.
     */
    public void coutingPipes() {
        for (PipeCouple couple : couplesList) {
            if (couple.pipe1.getTranslateX() < bird.getTranslateX()) {
                if (couple.CanGivePts()) {
                    //Réajustement du l'alignement du text, pas encore trouvé comment faire autrement
                    if (score.getPts() == 9) {
                        score.getText().setTranslateX(score.getText().getTranslateX() + 8);
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
            couple.pipe1.refreshCoord();
            couple.pipe2.refreshCoord();
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
        //Affichage du score
        score.getText().setTranslateX(0);
        score.getText().setTranslateY(0);
        score.getText().setFill(Color.WHITESMOKE);
        score.getText().setStroke(Color.BLACK);

        //Affichage d'information
        txtInformation.setText("Rejouer -> R <-\nQuitter -> Q <-");
        txtInformation.setVisible(true);
        //Le jeu est déclarer comme arrêté
        isGameRunning = false;
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
    }

    /**
     * Replace le score au bonne endroit et le remet à zéro
     */
    public void resetScore() {
        score.resetScore();
        score.getText().setStroke(Color.WHITESMOKE);
        score.getText().setFill(Color.TRANSPARENT);
        score.getText().setTranslateX(-400);
        score.getText().setTranslateY(-320);
        txtInformation.setVisible(false);
    }
}

