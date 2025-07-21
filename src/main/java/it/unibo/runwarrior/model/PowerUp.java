package it.unibo.runwarrior.model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public interface PowerUp {

    /**
     * Loads the powerup images from resources
     */
    public void powerUpImage();

    /**
     * @return the image of the powerup object
     */
    public BufferedImage getImage();

    /**
     * @return the image of the egg that keeps the powerup inside
     */
    public BufferedImage getEgg();

    /**
     * @param deathPosition
     * Set the area of the powerup if it comes from a dead enemy
     */
    public void setTouchArea(Rectangle deathPosition);

    /**
     * @return the collision area of the powerup/egg
     */
    public Rectangle getTouchArea();

    /**
     * @return true if the powerup is taken by the player
     */
    public boolean isPowerTaken();

    /**
     * @return true if the egg is open,
     * so it was hit by the player
     */
    public boolean isEggOpen();

    /**
     * Set the variable of the powerup taken at true
     */
    public void takePower();

    /**
     * Set the variable at true when the egg is open
     */
    public void openTheEgg();
}
