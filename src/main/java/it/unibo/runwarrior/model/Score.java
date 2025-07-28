package it.unibo.runwarrior.model;

/**
 * Class that manages the player's score related to coin collection.
 * It uses Game Save Manager to persist coin data.
 */
public class Score {
    private final GameSaveManager gameSaveManager;

    /**
     * Score Constructor.
     *
     * @param gameSaveManager to take saved data
     */
    public Score(final GameSaveManager gameSaveManager) {
       this.gameSaveManager = gameSaveManager;
    }

    // /**
    // * Increments the coin score by the specific number of coin points.
    // *
    // * @param coinPoints the number of coin points to add
    // */
    // public final void incrementCoinScore(final int coinPoints) { 
    //     gameSaveManager.addCoin(coinPoints);
    // }

    /**
     * @return the total score
     */
    public final int getTotalScore() {
        return gameSaveManager.getCoinCollected(); 
    }

    /**
     * @return the number of coin collected
     */
    public final int getCoinScore() {
        return gameSaveManager.getCoinCollected();
    }

    /**
     * method that allows player to spend coins to buy in the shop.
     *
     * @param skinPrice the cost of the skin
     * @return true if the coin were succesfully spent
     */
    public final boolean spendCoins(final int skinPrice) {
        if (getCoinScore() >= skinPrice) {
            gameSaveManager.setCoinCollected(getCoinScore() - skinPrice);
            return true;
        }
        return false;
    }
}
