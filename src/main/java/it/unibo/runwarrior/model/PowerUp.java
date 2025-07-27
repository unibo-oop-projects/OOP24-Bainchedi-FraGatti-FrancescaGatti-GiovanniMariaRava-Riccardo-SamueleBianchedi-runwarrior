package it.unibo.runwarrior.model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Powerup entity.
 */
public interface PowerUp {

    /**
     * Loads the powerup images from resources.
     */
    void powerUpImage();

    /**
     * @return the image of the powerup object
     */
    BufferedImage getImage();

    /**
     * @return the image of the egg that keeps the powerup inside
     */
    BufferedImage getEgg();

    /**
     * @return the collision area of the powerup/egg
     */
    Rectangle getTouchArea();

    /**
     * @return true if the powerup is taken by the player
     */
    boolean isPowerTaken();

    /**
     * @return true if the egg is open, so if it was hit by the player
     */
    boolean isEggOpen();

    /**
     * Set the variable of the powerup taken at true.
     */
    void takePower();

    /**
     * Set the variable at true when the egg is open.
     */
    void openTheEgg();
}
