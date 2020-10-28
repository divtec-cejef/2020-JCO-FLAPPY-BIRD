package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Sprite extends Rectangle {

    Sprite(int x, int y, int w, int h, Color color) {
        super(w, h, color);

        setTranslateX(x);
        setTranslateY(y);
    }

    void moveLeft(int speed) {
        setTranslateX(getTranslateX() - speed);
    }

    void moveRight(int speed) {
        setTranslateX(getTranslateX() + speed);
    }

    void moveUp(int speed) {
        setTranslateY(getTranslateY() - speed);
    }

    void moveDown(int speed) {
        setTranslateY(getTranslateY() + speed);
    }
}