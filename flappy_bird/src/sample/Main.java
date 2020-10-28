package sample;

import javafx.scene.paint.Color;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    float t = 0;
    boolean isSpacePressed = false;
    final float MAX_HEIGHT = 700;
    final float MAX_WIDTH = 1000;
    StackPane root = new StackPane();
    Oiseau oiseau = new Oiseau(-250,-200,50,50,Color.RED);
    Pipe pipe = new Pipe(500,250,50,300,Color.LIGHTGREEN);
    ArrayList<PipeCouple> listeCouples;

    public static void main(String[] args) {
        launch(args);
    }

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

    @Override
    public void start(Stage stage) throws Exception {

        listeCouples = createCouplesList(10);
        Scene scene = new Scene(createContent());
        //Si SPACE est appuyé
        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if(keyCode.equals(KeyCode.SPACE)){
                if(!isSpacePressed) {
                   oiseau.flap(150);
                   isSpacePressed = true;
                }
            }
        });

        //Si SPACE est relaché
        scene.setOnKeyReleased(event -> {
            KeyCode keyCode = event.getCode();
            if(keyCode.equals(KeyCode.SPACE)){
                System.out.println("SPACE RELEASE");
                isSpacePressed = false;
            }
        });
        addCouples(root);
        root.getChildren().add(oiseau);

        //Récupération de la feuille de style css
        scene.getStylesheets().add("css/style.css");
        // Sélectionner la scene
        stage.setScene(scene);
        // Donne rune titre à la scène
        stage.setTitle("FlappyBird");
        //rendre la fenetre non redimentionnable
        stage.setResizable(false);
        // Lancer la scène
        stage.show();
    }

    private void update() {
        t += 0.0016;
        oiseau.undergoGravity(4);
        if(t > 0.100){
            listeCouples.get(1).move();
        }
        if(t>0.200){
            listeCouples.get(0).move();
        }
        if(t>0.300){
            listeCouples.get(3).move();
        }
        listeCouples.get(2).move();
        System.out.println(listeCouples.get(2).pipe1.getTranslateY());

    }

    public ArrayList<PipeCouple> createCouplesList(int nombreCouple){
        ArrayList<PipeCouple> list = new ArrayList<>();
        for(int i = 0; i < nombreCouple;i++){
            list.add(
                    new PipeCouple(
                            //Les pipes sont formaté à leur création, donc pas besoin de donnée de paramètres
                            new Pipe(0,0,0,0,Color.GREEN),
                            new Pipe(0,0,0,0,Color.BLUE)
                    ));
        }
        return list;

    }
    
    public void addCouples(StackPane root){
        for (PipeCouple couple: listeCouples) {
            root.getChildren().add(couple.pipe1);
            root.getChildren().add(couple.pipe2);
        }
    }



}

