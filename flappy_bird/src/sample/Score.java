package sample;


import javafx.scene.text.Text;

/**
 * Classe qui représente un score
 */
public class Score {
    private int pts = 0;
    private Text text = new Text();

    public Score(double x, double y){
        this.text.setTranslateX(x);
        this.text.setTranslateY(y);
    }

    /**
     * Renvoie les points
     * @return pts
     */
    public int getPts() {
        return pts;
    }

    /**
     * Renvoie l'objet text
     * @return l'objet text
     */
    public Text getText() {
        return text;
    }

    /**
     * Permet décrire dans l'objet text
     * @param text à écrire
     */
    public void write(String text) {
        this.text.setText(text);
    }

    /**
     * Incrémente le score de 1
     */
    public void incrementScore(){
        this.pts++;
    }

    public void resetScore(){
        this.pts = 0;
    }
}
