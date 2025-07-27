package it.unibo.runwarrior.controller;

import java.awt.Graphics;
import java.util.List;
import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.Coin;

public interface CoinController {
    /**
     * it loads the coins from a file 
     *
     * @param pathFile is the path of the file
     *
     * @return a list of coins
     */
    List<int[]> loadCoinFromFile(final String pathFile); 

    /**
     *it initializes coins from the file.
     * 
     * @param pathFile is the path of the file
     */
    void initCoinsFromFile(final String pathFile);

    /**
     * it adds one coin to the list.
     *
     * @param row row of the map matrix to positionate the coin
     * @param col col of the map matrix to positionate the coin
     */
    void addCoins(final int row, final int col);

    /**
     *It draws all coins
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
    void updatePlayer(final Character player);

    // public void controlCoinCollision(int tileSize){
    //     Rectangle playerRectangle = player.getArea();
    //     Iterator<Coin> it = coinList.iterator();
    //     while(it.hasNext()){
    //         Coin coin = it.next();
    //         if (!coin.isCollected()){
    //             Rectangle coinRectangle = coin.getRectangle(tileSize);
    //             if (playerRectangle.intersects(coinRectangle)){
    //                 coin.collect();
    //                 coinsCollected++;
    //                 if(scoreController != null){
    //                     scoreController.addCoin();
    //                 }
    //                 it.remove();
    //             }
    //         }
    //     }
    // }

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
}
