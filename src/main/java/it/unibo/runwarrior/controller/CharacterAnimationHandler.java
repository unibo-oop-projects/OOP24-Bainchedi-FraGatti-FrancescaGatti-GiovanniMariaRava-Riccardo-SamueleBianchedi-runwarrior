package it.unibo.runwarrior.controller;

import java.awt.image.BufferedImage;

import it.unibo.runwarrior.model.player.PlayerFrame;

public interface CharacterAnimationHandler {
    
    /**
     * Set the current player images.
     *
     * @param im current player images
     */
    void setImages(BufferedImage... im);

    /**
     * Controls and change frame during the player movement.
     */
    void frameChanger();

    /**
     * @param rightDirection true if the player is going to the right
     * @return the player image based on the current frame the player has
     */
    BufferedImage imagePlayer(boolean rightDirection);

    /**
     * @return the current frame of the player
     */
    PlayerFrame getFrame();

    /**
     * @return true if the player is attacking, i.e. if is active the input from keyboard
     */
    boolean isAttacking();

    /**
     * @param rightDirection true if the player is going to the right
     * @return the image of the tip (warrior) or stick (wizard) of the player
     */
    BufferedImage getTip(boolean rightDirection);
}
