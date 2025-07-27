package it.unibo.runwarrior.model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Implementation of a coin.
 * It represents a coin placed in the game world, which the player can collect.
 */
public class Coin { 
    private int row; 
    private int col; 
    private boolean collected; 
    public BufferedImage coinImage;

    /**
     * Coin Constructor
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
                    System.out.println("Immagine moneta non trovata (coinImage è null)");
                } else {
                    System.out.println("Immagine moneta caricata correttamente");
                }
            }catch(final IOException e) {
                e.printStackTrace();
            }
    }

    /**
     * Returns a rectangle representing the coin's bounding box on the screen.
     * Used for collision detection with the player.
     */
    public Rectangle getRectangle(int tileSize) {
        return new Rectangle(col*tileSize, row*tileSize, tileSize, tileSize);
    }

    /**
     * @return the coin's row
     */
    public int getRow() {
        return row; 
    };

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
