package flappyBird;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Gère le tableau des scores
 * Crée un fichier text et écris tout les scores dedans
 * Lit le fichier text pour ne garder que les 5 meilleurs
 * Concactène les 5 meilleur score pour l'affichage
 */
public class ScoreBoard {

    /**
     * Écrit dans un fichier text
     *
     * @param file fichier à créer si inexistant et dans lequel écrire
     * @param text Text à écrir dans le fichier de text
     */
    public static void writeInTxtFile(File file, String text) {
        try {
            FileWriter writer = new FileWriter(file, true);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.print(text + ";");
            printWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Convertit la chaîne de caractère en Integer et met le tout dans une liste triée décroissante
     *
     * @param file Fichier dans lequel écrire
     * @return une liste d'Integer triée, retourne une liste vide si le fichier est vide
     */
    private static ArrayList<Integer> getScoreList(File file) {
        //Récupère la ligne de text dans le fichier
        String line = getTxtFileLine(file);
        //Crée une Arraylist à partir de cette ligne de text
        ArrayList<Integer> list = createScoreList(line);
        //Trier la liste par ordre décroissant
        list.sort(Collections.reverseOrder());
        return list;
    }

    /**
     * Crée une liste de score non-triée à partir du ligne de texte structurée
     *
     * @param line ligne de texte à transformer
     * @return une liste de score non-triée
     */
    private static ArrayList<Integer> createScoreList(String line) {
        ArrayList<Integer> list = new ArrayList<>();

        StringBuilder currentWord = new StringBuilder();
        //Si le fichier est vide, la liste sera vide
        if (line != null) {
            //Parcourir tout les caractère de la ligne de texte
            for (int i = 0; i < line.length(); i++) {
                //Tant qu'un point virgule n'est pas atteint
                if (line.charAt(i) != ';') {
                    //Assemblage du nombre
                    currentWord.append(line.charAt(i));
                }
                //Si un point virgule est atteint
                else {
                    //Vérifier que le mot ne soit pas vide
                    if (!currentWord.toString().equals("")) {
                        //Ajout du mot transformer en Integer dans la liste
                        list.add(Integer.parseInt(currentWord.toString()));
                    }
                    //Nettoyer le mot
                    currentWord = new StringBuilder();
                }
            }
        }
        return list;
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
     * Créer une chaîne de charactère formatée pour afficher un tableau de 5 scores
     *
     * @param file fichier à ouvrir
     * @return une chaîne de caractère formatée
     */
    public static String getscoreBoard(File file) {
        StringBuilder scoreBoard = new StringBuilder();
        ArrayList<Integer> listScores;
        listScores = getScoreList(file);

        for (int i = 0; i < 5; i++) {
            //Retour de chariot
            if (i != 0) {
                scoreBoard.append("\n");
            }
            scoreBoard.append("   ").append(i + 1).append(".\t  ");
            //Insérer les scores
            if (i < listScores.size()) {
                scoreBoard.append(listScores.get(i).toString());
            }
        }
        return scoreBoard.toString();
    }
}
