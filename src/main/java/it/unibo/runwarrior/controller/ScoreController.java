package it.unibo.runwarrior.controller;
import it.unibo.runwarrior.model.GameSaveManager;
import it.unibo.runwarrior.model.Score;
//import it.unibo.runwarrior.model.Coin;


public class ScoreController {
    private final Score score; 
    private final GameSaveManager gameSaveManager;

    public ScoreController(Score score){ 
        this.score = score; 
        this.gameSaveManager = GameSaveManager.getInstance();
    }

    public void addCoin(){
        score.incrementCoinScore(1);
        gameSaveManager.addCoin(1);
    }
}
