package it.unibo.runwarrior.model;

import java.awt.Rectangle;

public interface Coin { 
    /**
     * Loads the coin image from the resources.
     */
    void loadCoinImage();

    /**
     * Returns a rectangle representing the coin's bounding box on the screen.
     * Used for collision detection with the player.
     */
    Rectangle getRectangle(int tileSize);

    /**
     * @return the coin's row
     */
    int getRow();

    /**
     * @return the coin's column
     */
    int getCol();

    /**
     * @return true if the coin is already collected
     */
    boolean isCollected();

    /**
     * It marks the coin as collected. 
     * Once called, the methos isCollected return true.
     */
    void collect();
}
