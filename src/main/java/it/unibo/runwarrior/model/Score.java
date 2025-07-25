package it.unibo.runwarrior.model;


public class Score {
    private final GameSaveManager gameSaveManager;
    
    
    public Score(GameSaveManager gameSaveManager){
       this.gameSaveManager = gameSaveManager; 
    }

    public void incrementCoinScore(int coinPoints){ 
        gameSaveManager.addCoin(coinPoints);
    }

    public int getTotalScore(){
        return gameSaveManager.getCoinCollected(); 
    }

    public int getCoinScore(){
        return gameSaveManager.getCoinCollected();
    }

    public void reset(){ //da chiamare quando il giocatore non vuole salvare i dati della partita
        gameSaveManager.setCoinCollected(0);
    }

    public boolean spendCoins(int skinPrice){
        if(getCoinScore() >= skinPrice){
           gameSaveManager.setCoinCollected(getCoinScore()-skinPrice);
            return true;
        }
        return false;
    }
}