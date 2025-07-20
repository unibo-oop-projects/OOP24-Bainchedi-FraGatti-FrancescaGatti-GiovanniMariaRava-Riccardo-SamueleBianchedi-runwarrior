package it.unibo.runwarrior.view;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.*;

/**
 * Class used to create and play the music of the game
 */
public class GameMusic {
    
    private Clip clip;
    
    /**
     * Constructor of the music during the game.
     * It takes the music file, creates the audio stream, creates and open the clip and play it once or endlessly.
     *
     * @param musicFile music file wav
     * @param loop boolean to play music costantly if it's true
     */
    public GameMusic(String musicFile, boolean loop) {
        try {
            URL musicURL = getClass().getResource("/Music/" + musicFile);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicURL);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
