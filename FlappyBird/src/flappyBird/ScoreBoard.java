package flappyBird;

import java.io.*;
import java.util.*;

/**
 * Gère le tableau des scores
 * Crée un fichier text et écris tout les scores dedans
 * Lit le fichier text pour ne garder que les 5 meilleurs
 * Concactène les 5 meilleur score pour l'affichage
 */
public class ScoreBoard{

    ArrayList<PlayerScore> playerScores = new ArrayList<>();

    /**
     * Écrit dans un fichier score
     *
     * @param file        fichier à créer si inexistant et dans lequel écrire
     * @param playerScore Score du joueur a écrire dans le fichier texte
     */
    public static void writeInTxtFile(File file, PlayerScore playerScore) {
        try {
            FileWriter writer = new FileWriter(file, true);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.print(playerScore.getName() + "=" + playerScore.getScore() + ";");
            printWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Trie et retourne une liste de score contenant score et nom
     *
     * @param file Fichier dans lequel écrire
     * @return une liste triée, retourne une liste vide si le fichier est vide
     */
    private static ArrayList<PlayerScore> getScoreList(File file) {
        //Récupère la ligne de text dans le fichier
        String line = getTxtFileLine(file);
        //Crée une Arraylist à partir de cette ligne de text
        ArrayList<PlayerScore> list = createScoreList(line);
        //Trier la liste par ordre décroissant
        list.sort(Collections.reverseOrder());
        return list;
    }

    /**
     * Crée une liste de score non-triée contenant score et nom à partir d'une ligne de texte structurée
     *
     * @param line ligne de texte à transformer
     * @return une liste de score contenant score et nom non-triée
     */
    private static ArrayList<PlayerScore> createScoreList(String line) {

        ArrayList<PlayerScore> listOfScores = new ArrayList<>();
        Boolean nameIsWrited = false;

        StringBuilder currentName = new StringBuilder();
        StringBuilder currentScore = new StringBuilder();

        if (line != null) {
            //la ligne de texte
            for (int i = 0; i < line.length(); i++) {
                //Si le nom n'est pas écrit
                if(!nameIsWrited){
                    //Tant qu'un signe égale n'est pas atteint
                    if(line.charAt(i) != '='){
                        currentName.append(line.charAt(i));
                    }
                    //si le caractère est un signe égale
                    else{
                        nameIsWrited = true;
                    }

                }
                //Le nom est écrit, donc écriture du score
                else{
                    //tant qu'un virgule n'est pas atteinte
                    if(line.charAt(i) != ';'){
                        currentScore.append(line.charAt(i));
                    }
                    //le nom et le score on été lu, donc enregistrement
                    else{
                        nameIsWrited = false;
                        listOfScores.add(new PlayerScore(currentName.toString(),Integer.parseInt(currentScore.toString())));
                        //On vide les variables
                        currentScore.setLength(0);
                        currentName.setLength(0);
                    }
                }
            }
        }

        return listOfScores;
    }

    /**
     * Récupère une ligne de texte dans une fichier texte
     *
     * @param file Chemin vers le fichier texte
     * @return une chaîne de caractère contenant la ligne
     */
    private static String getTxtFileLine(File file) {
        String line = null;
        if (file.exists()) {
            BufferedReader in = null;
            try {
                in = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                line = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return line;
    }

    /**
     * Créer une chaîne de charactère formatée pour afficher un tableau de 5 scores avec le nom des joueurs
     *
     * @param file fichier à ouvrir
     * @return une chaîne de caractère formatée
     */
    public static String getscoreBoard(File file) {
        StringBuilder scoreBoard = new StringBuilder();
        ArrayList<PlayerScore> listScores;
        listScores = getScoreList(file);

        for (int i = 0; i < 5; i++) {
            //Retour de chariot
            if (i != 0) {
                scoreBoard.append("\n");
            }
            scoreBoard.append("   ").append(i + 1).append(".\t  ");
            //Insérer les scores
            if (i < listScores.size()) {
                scoreBoard.append(listScores.get(i).getScore()).append("\t").append(listScores.get(i).getName());
            }
        }
        return scoreBoard.toString();
    }
}
