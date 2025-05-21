package it.unibo.runwarrior.view;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.*;

public class GameMusic {
    
    private Clip clip;
    
    public GameMusic(String musicFile, boolean loop){
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
