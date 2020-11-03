package sample;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
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
    Bird bird = new Bird(-250, -200, 30, 30, Color.DARKVIOLET);

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

        //Si SPACE est appuyé
        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.SPACE)) {
                if (!isSpacePressed) {
                    bird.flap(150);
                    isSpacePressed = true;
                }
            }
        });

        //Si SPACE est relaché
        scene.setOnKeyReleased(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.SPACE)) {
                isSpacePressed = false;
            }
        });
        //Ajout de tout les couple de tuyaux
        addCouples(root);
        //Ajout de l'oiseau
        root.getChildren().add(bird);
        root.getChildren().add(bird.getBirdSprite());

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

        if (t < 1) {
            t += 0.0016;
        }
        //Le couple 0 bouge
        couplesList.get(0).move();
        //L'oiseau subit la gravité
        bird.undergoGravity(5);
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

        // si l'oiseau meurt, fin du jeu
        if (!bird.isAlive()) {
            System.out.println("GAME OVER");
            bird.setFill(Color.BLACK);
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
     * @param nombreCouple nombre de couple voulu
     * @return une liste de couple de tuyaux
     */
    public ArrayList<PipeCouple> createCouplesList(int nombreCouple) {
        ArrayList<PipeCouple> list = new ArrayList<>();
        for (int i = 0; i < nombreCouple; i++) {
            list.add(
                    new PipeCouple(
                            //Les pipes sont formaté à leur création, donc pas besoin de donnée de position
                            //Parcontre la taille est importante vu que l'area d'un sprite se génére lors de la création
                            new Pipe(0, 0, 60, 700, Color.LIGHTGREEN),
                            new Pipe(0, 0, 60, 700, Color.LIGHTGREEN)
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

    public void linkImageToBird(ImageView image, Bird bird){
        image.setX(bird.getTranslateX());
        image.setY(bird.getTranslateX());
    }
}

