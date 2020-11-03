package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    //Indice de temps
    float t = 0;
    //Si oui ou non la barre espace est appuyée
    boolean isSpacePressed = false;
    //Hauteur max de la fenêtre
    final float MAX_HEIGHT = 700;
    //Largeur max de la fenêtre
    final float MAX_WIDTH = 1000;
    //Pane principale
    StackPane root = new StackPane();
    //L'oiseau
    Bird bird = new Bird(-250, -200, 30, 30, Color.TRANSPARENT);
    //LeScore
    Score score = new Score(-400, -320);

    Label myScore = new Label();

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
        score.getText().setFont(Font.font("FlappyBirdy", FontWeight.BOLD, FontPosture.REGULAR, 50));
        score.getText().setStroke(Color.WHITESMOKE);
        score.getText().setFill(Color.TRANSPARENT);

        myScore.setText("COUCOU COUCOU COUCOU COUCOU COUCOU COUCOU COUCOU COUCOU COUCOU COUCOU COUCOU ");
        myScore.setTranslateX(0);
        myScore.setTranslateY(0);
        myScore.setAlignment(Pos.CENTER_LEFT);


        //Si SPACE est appuyé
        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.SPACE)) {
                if (!isSpacePressed) {
                    bird.setFlying(true);
                    bird.setStartAndGoalAreSetup(false);
                    bird.getBirdSprite().setImage(new Image("Sprites/flappyFlap.png"));
                    isSpacePressed = true;
                }
            }
        });

        //Si SPACE est relaché
        scene.setOnKeyReleased(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.SPACE)) {
                isSpacePressed = false;
                bird.getBirdSprite().setImage(new Image("Sprites/flappy.png"));
            }
        });
        //Ajout de tout les couple de tuyaux
        addCouples(root);
        //Ajout de l'oiseau
        root.getChildren().add(bird);
        root.getChildren().add(bird.getBirdSprite());
        //Ajout du score
        root.getChildren().add(score.getText());

        root.getChildren().add(myScore);

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
        }else{
            //FIN DU JEU
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

    public boolean checkBounds() {
        return bird.getTranslateY() > 350 || bird.getTranslateY() < -350;
    }

    public void coutingPipes() {
        for (PipeCouple couple : couplesList) {
            if (couple.pipe1.getTranslateX() < bird.getTranslateX()) {
                if (couple.CanGivePts()) {
                    score.incrementScore();
                    score.write("Score : " + score.getPts());
                    couple.setCanGivePts(false);
                }
            }
        }
    }
}

