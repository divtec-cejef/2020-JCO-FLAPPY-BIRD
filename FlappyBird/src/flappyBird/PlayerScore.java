package flappyBird;

/**
 * Représente le score d'un joueur
 */
public class PlayerScore implements Comparable<PlayerScore> {

    private String name;
    private Integer score;

    /**
     * Constructeur du playerScore
     *
     * @param name  nom du joueur
     * @param score score du joueur
     */
    PlayerScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public Integer getScore() {
        return score;
    }

    /**
     * compare deux scores
     *
     * @param autreScore autre score à comparer
     * @return 0 = les score sont égaux, -1 l'autre score est plus grand, 1 le score est plus grand
     */
    @Override
    public int compareTo(PlayerScore autreScore) {
        int resultat;

        if (this.score > autreScore.getScore()) {
            resultat = 1;
        } else if (this.score < autreScore.getScore()) {
            resultat = -1;
        }else{
            resultat = this.name.compareTo(autreScore.getName()) * -1;
        }
        return resultat;
    }
}
