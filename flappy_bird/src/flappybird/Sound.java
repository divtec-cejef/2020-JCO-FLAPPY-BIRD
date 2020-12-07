package flappybird;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;

//Représente un lecteur de son
public class Sound {

    Media sound;
    MediaPlayer mediaPlayer;

    /**
     * Créer un lecteur de son avec un son spécifique
     * @param soundFile son à lire
     */
    public Sound(String soundFile){
        sound = new Media(new File(soundFile).toURI().toString());
    }

    /**
     * Joue le son
     */
    public void play() {
        mediaPlayer = new MediaPlayer(sound);
        this.mediaPlayer.play();
    }
}
