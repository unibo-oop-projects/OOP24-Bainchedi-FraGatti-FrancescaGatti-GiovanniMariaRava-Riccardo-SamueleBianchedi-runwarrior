package it.unibo.runwarrior.model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Implementation of coin interface. 
 * It represents a coin placed in the game world, which the player can collect.
 */
public class CoinImpl implements Coin {
    private int row; 
    private int col; 
    private boolean collected; 
    public BufferedImage coinImage; 

    /**
     * Constructor of coin.
     *
     * @param row row where coin is set in the map
     * @param col col where coin is set in the map
     */
    public CoinImpl(int row, int col) {
        this.row = row; 
        this.col = col; 
        this.collected = false; 
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
    public Rectangle getRectangle(int tileSize) {
        return new Rectangle(col*tileSize, row*tileSize, tileSize, tileSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRow() {
        return row; 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCol() {
        return col; 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCollected() {
        return collected; 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collect() {
        collected = true; 
    }
}
