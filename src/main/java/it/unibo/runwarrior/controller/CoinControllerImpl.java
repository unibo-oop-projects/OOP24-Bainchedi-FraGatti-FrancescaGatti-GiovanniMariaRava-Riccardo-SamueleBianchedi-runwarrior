package it.unibo.runwarrior.controller;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.Coin;
import it.unibo.runwarrior.view.GameLoopPanel;

/**
 * implementation of CoinController that manages the loading, the drawing
 * and the collection of coins.
 */
public class CoinControllerImpl implements CoinController {
    private Character player;
    private int groundX;
    private int coinsCollected;
    private List<Coin> coinList;
    private ScoreController scoreController;

    public CoinControllerImpl(){
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
            System.err.println("File non trovato nel classpath: " + pathFile);
            return coinCoordinates;
            }
            String line;
            while ((line = fileReader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && line.contains(",")) {
                    final String[] parts = line.split(",");
                    final int row = Integer.parseInt(parts[0].trim());
                    final int col = Integer.parseInt(parts[1].trim());
                    coinCoordinates.add(new int[]{row, col});
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
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
    public void drawAllCoins(Graphics g, int tileSize, Character player){
        groundX = player.getMovementHandler().getGroundX(); 

        for (final Coin coin : coinList) {
            if (!coin.isCollected()) {
                final int coinX = coin.getCol() * tileSize;
                final int coinY = coin.getRow() * tileSize;
                final int screenX = coinX + groundX;
                if (screenX + tileSize >= 0 && screenX <= GameLoopPanel.WIDTH) {
                    g.drawImage(coin.coinImage, screenX, coinY, tileSize, tileSize, null);
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
    public final void updatePlayer(final Character player) {
        this.player = player;
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
    public void increaseCoinsCollected(){
        coinsCollected++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setScoreController(ScoreController scoreController){
        this.scoreController = scoreController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Coin> getCoinList() {
        return coinList;
    }   
}
