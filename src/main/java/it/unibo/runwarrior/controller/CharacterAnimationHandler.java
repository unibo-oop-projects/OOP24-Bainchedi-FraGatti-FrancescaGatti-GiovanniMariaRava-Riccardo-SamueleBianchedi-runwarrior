package it.unibo.runwarrior.controller;

import java.awt.image.BufferedImage;

public interface CharacterAnimationHandler {
    
    /**
     * Controls and change frame during the player movement
     */
    public void frameChanger();

    /**
     * @param rightDirection true if the player is going to the right
     * @return the player image based on the current frame the player has
     */
    public BufferedImage imagePlayer(boolean rightDirection);

    /**
     * @return true if the player is attacking, i.e. if is active the input from keyboard
     */
    public boolean isAttacking();

    /**
     * @param rightDirection true if the player is going to the right
     * @return the image of the tip (warrior) or stick (wizard) of the player
     */
    public BufferedImage getTip(boolean rightDirection);
}
