package it.unibo.runwarrior.controller;

import java.awt.Graphics;
import java.util.List;
import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.controller.score.api.ScoreController;
import it.unibo.runwarrior.model.Coin;

/**
 * Coin Controller interface.
 */
public interface CoinController {
    /**
     * it loads the coins from a file.
     *
     * @param pathFile is the path of the file
     *
     * @return a list of coins
     */
    List<int[]> loadCoinFromFile(String pathFile); 


    /**
     * It initializes the coin from the file.
     *
     * @param pathFile the path of the file where the coin coordinates are saved
     */
    void initCoinsFromFile(String pathFile);

    /**
     * it adds one coin to the list.
     *
     * @param row row of the map matrix to positionate the coin
     * @param col col of the map matrix to positionate the coin
     */
    void addCoins(int row, int col);

    /**
     * It draws all coins.
     *
     * @param g graphic
     * @param tileSize tile dimension
     * @param player the player that is playing to obtain the correct position of shift
     */
    void drawAllCoins(Graphics g, int tileSize, Character player);

    /**
     * update the player when it changes his state.
     *
     * @param player the actual player to be update
     */
    void updatePlayer(Character player);

    /**
     * @return total collected coins
     */
    int getCoinsCollected();

    /**
     * Increments the count of collected coin.
     */
    void increaseCoinsCollected();

    /**
     * sets the controller to manages the score.
     *
     * @param scoreController controller of the score
     */
    void setScoreController(ScoreController scoreController);

    /**
     * @return list of coins that are actually in the level
     */
    List<Coin> getCoinList();

    /**
     * @return the current player
     */
    Character getPlayer();

    /**
     * @return the object score controller
     */
    ScoreController getScoreController();
}
