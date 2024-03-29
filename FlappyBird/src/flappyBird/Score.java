package flappyBird;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Classe qui représente un score
 *
 * @author Louis Bovay
 */
public class Score {
    private int pts = 0;
    private int kills = 0;
    private Text text = new Text();

    /**
     * Constructeur permettant d'assigner directement une position
     *
     * @param x position X
     * @param y position Y
     */
    public Score(double x, double y) {
        this.text.setTranslateX(x);
        this.text.setTranslateY(y);
        text.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, FontPosture.REGULAR, 50));
    }

    /**
     * Renvoie les points
     *
     * @return pts
     */
    public int getPts() {
        return pts;
    }

    /**
     * Renvoie l'objet text
     *
     * @return l'objet text
     */
    public Text getText() {
        return text;
    }

    /**
     * Permet décrire dans l'objet text
     *
     * @param text à écrire
     */
    public void write(String text) {
        this.text.setText(text);
    }

    /**
     * Incrémente le score de 1
     */
    public void incrementScore() {
        this.pts++;
    }

    /**
     * Incrémente les kills de 1
     */
    public void incrementKills() {
        this.kills++;
    }

    /**
     * Remet le score à zéro
     */
    public void resetScore() {
        this.pts = 0;
        this.text.setText("Score : 0");
    }

    /**
     * Remet les kills à zéro
     */
    public void resetKills(){
        this.kills = 0;
    }

    public int getKills() {
        return kills;
    }
}
