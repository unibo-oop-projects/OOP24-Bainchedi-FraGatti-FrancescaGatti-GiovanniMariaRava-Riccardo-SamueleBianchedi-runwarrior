package it.unibo.runwarrior.model;

public class Score {
    private GameSaveManager gameSaveManager;

    public Score(final GameSaveManager gameSaveManager) {
       this.gameSaveManager = gameSaveManager;
    }

    public final void incrementCoinScore(final int coinPoints) { 
        gameSaveManager.addCoin(coinPoints);
    }

    public final int getTotalScore() {
        return gameSaveManager.getCoinCollected(); 
    }

    public final int getCoinScore() {
        return gameSaveManager.getCoinCollected();
    }

    public final boolean spendCoins(final int skinPrice) {
        if (getCoinScore() >= skinPrice) {
            gameSaveManager.setCoinCollected(getCoinScore() - skinPrice);
            return true;
        }
        return false;
    }
}
