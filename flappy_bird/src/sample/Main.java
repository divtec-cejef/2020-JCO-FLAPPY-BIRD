package sample;

import javafx.geometry.Pos;
import javafx.scene.paint.Color;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;
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
    Oiseau oiseau = new Oiseau(-250, -200, 30, 30, Color.DARKVIOLET);

    Label label1 = new Label("");

    //Liste de couple de tuyau
    ArrayList<PipeCouple> listeCouples;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        //Initialisé la liste de couple de tuyau
        listeCouples = createCouplesList(4);

        //Création d'une scène utilisant la pane
        Scene scene = new Scene(createContent());

        //Si SPACE est appuyé
        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.SPACE)) {
                if (!isSpacePressed) {
                    oiseau.flap(150);
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
        root.getChildren().add(oiseau);


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
        t += 0.0016;
        //Le couple 0 bouge
        listeCouples.get(0).move();
        //L'oiseau subit la gravité
        oiseau.undergoGravity(5);
        //Le couple 1 bouge
        if (t > 0.100) {
            listeCouples.get(1).move();
        }
        //Le couple 2 bouge
        if (t > 0.200) {
            listeCouples.get(2).move();
        }
        //Le couple 3 bouge
        if (t > 0.300) {
            listeCouples.get(3).move();
        }

        // tue l'oiseau si trop haut ou trop bas
        if (checkBounds()) {
            oiseau.kill();
        }

        // si l'oiseau touche un tuyau, il meurt
        if (isAPipeTouched(listeCouples, oiseau)) {
            oiseau.kill();
        }

        // si l'oiseau meurt, fin du jeu
        if (!oiseau.isAlive()) {
            System.out.println("IS KILL");
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
     * @param ListeCouple liste de couple de tuyaux
     * @param oiseau      qui peut ou non toucher un tuyau
     * @return ture = touché // False = non touché
     */
    public boolean isAPipeTouched(ArrayList<PipeCouple> ListeCouple, Oiseau oiseau) {
        boolean isTouched = false;
        for (PipeCouple couple : ListeCouple) {
            if (couple.pipe1.isHit(oiseau) || couple.pipe2.isHit(oiseau)) {
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
        for (PipeCouple couple : listeCouples) {
            root.getChildren().add(couple.pipe1);
            root.getChildren().add(couple.pipe2);
        }
    }

    public boolean checkBounds() {
        return oiseau.getTranslateY() > 350 || oiseau.getTranslateY() < -350;
    }
}

