package it.unibo.runwarrior.controller;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.Coin;
import it.unibo.runwarrior.view.GameLoopPanel;

/**
 * implementation of CoinController that manages the loading, the drawing
 * and the collection of coins.
 */
public class CoinControllerImpl implements CoinController {
    /**
     * Logger used for exceptions and error messages.
     */
    protected static final Logger LOGGER = Logger.getLogger(CoinControllerImpl.class.getName());
    private Character player;
    private int coinsCollected;
    private final List<Coin> coinList;
    private ScoreController scoreController;

    /**
     * Coin Controller constructor.
     */
    public CoinControllerImpl() {
        coinList = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<int[]> loadCoinFromFile(final String pathFile) {
        final List<int[]> coinCoordinates = new ArrayList<>();
        try (InputStream is = getClass().getResourceAsStream(pathFile);
         BufferedReader fileReader = new BufferedReader(new InputStreamReader(is))) {
            if (is == null) {
            return coinCoordinates;
            }
            String line = fileReader.readLine();
            while (line != null) {
                line = line.trim();
                if (!line.isEmpty() && line.contains(",")) {
                    final String[] parts = line.split(",");
                    final int row = Integer.parseInt(parts[0].trim());
                    final int col = Integer.parseInt(parts[1].trim());
                    coinCoordinates.add(new int[]{row, col});
                }
                line = fileReader.readLine();
            }
        } catch (IOException | NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Cannot load coin from file");
        }
        return coinCoordinates;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void initCoinsFromFile(final String pathFile) {
        final List<int[]> coords = loadCoinFromFile(pathFile);
        for (final int[] coord : coords) {
            final int row = coord[0];
            final int col = coord[1];
            addCoins(row, col);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void addCoins(final int row, final int col) {
        final Coin coin = new Coin(row, col);
        coin.loadCoinImage();
        coinList.add(coin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawAllCoins(final Graphics g, final int tileSize, final Character currentPlayer) {
        final int groundX = currentPlayer.getMovementHandler().getGroundX(); 

        for (final Coin coin : coinList) {
            if (!coin.isCollected()) {
                final int coinX = coin.getCol() * tileSize;
                final int coinY = coin.getRow() * tileSize;
                final int screenX = coinX + groundX;
                if (screenX + tileSize >= 0 && screenX <= GameLoopPanel.WIDTH) {
                    g.drawImage(coin.getCoinImage(), screenX, coinY, tileSize, tileSize, null);
                    g.setColor(Color.RED);
                    g.drawRect(screenX, coinY, tileSize, tileSize);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void updatePlayer(final Character currentPlayer) {
        this.player = currentPlayer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getCoinsCollected() {
        return coinsCollected;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseCoinsCollected() {
        coinsCollected++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setScoreController(final ScoreController scoreController) {
        this.scoreController = scoreController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Coin> getCoinList() {
        return coinList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Character getPlayer() {
        return player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScoreController getScoreController() {
        return scoreController;
    }
}
