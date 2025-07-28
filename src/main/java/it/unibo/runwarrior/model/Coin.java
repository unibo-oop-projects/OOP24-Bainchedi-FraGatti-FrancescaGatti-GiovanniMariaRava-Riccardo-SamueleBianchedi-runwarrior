package it.unibo.runwarrior.model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;



/**
 * Implementation of a coin.
 * It represents a coin placed in the game world, which the player can collect.
 */
public class Coin {
    protected static final Logger LOGGER = Logger.getLogger(Coin.class.getName());
    public BufferedImage coinImage;
    private int row; 
    private int col; 
    private boolean collected; 

    /**
     * Coin Constructor.
     *
     * @param row row position of the coin in the map grid
     * @param col column position of the coin in the map grid
     */
    public Coin(int row, int col) {
        this.row = row; 
        this.col = col; 
        this.collected = false; 
    }

    /**
     * Loads the coin image from the resources.
     */
    public void loadCoinImage() {
        try {
            coinImage = ImageIO.read(getClass().getResourceAsStream("/Coins/CoinSmall.png"));
            if (coinImage == null) {
                LOGGER.log(Level.SEVERE, "Cannot load coin image");
            } else {
                LOGGER.log(Level.SEVERE, "Coin Image loaded");
            }
        } catch(final IOException e) {
            LOGGER.log(Level.SEVERE, "Cannot load coin images");
        }
    }

    /**
     * Returns a rectangle representing the coin's bounding box on the screen.
     * Used for collision detection with the player.
     *
     * @return
     */
    public Rectangle getRectangle(int tileSize) {
        return new Rectangle(col * tileSize, row * tileSize, tileSize, tileSize);
    }

    /**
     * @return the coin's row
     */
    public int getRow() {
        return row; 
    }

    /**
     * @return the coin's column
     */
    public int getCol() {
        return col; 
    }

    /**
     * @return true if the coin is already collected
     */
    public boolean isCollected() {
        return collected; 
    }

    /**
     * It marks the coin as collected. 
     * Once called, the methos isCollected return true.
     */
    public void collect() {
        collected = true; 
    }
}
