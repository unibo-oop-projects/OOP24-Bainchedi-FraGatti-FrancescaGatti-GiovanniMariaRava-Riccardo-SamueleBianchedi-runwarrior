package it.unibo.runwarrior.controller;
import it.unibo.runwarrior.model.GameSaveManager;
import it.unibo.runwarrior.model.Score;

public class ScoreController {
    private final Score score; 
    private final GameSaveManager gameSaveManager;

    public ScoreController(final Score score) { 
        this.score = score; 
        this.gameSaveManager = GameSaveManager.getInstance();
    }

    public final void addCoin() {
        score.incrementCoinScore(1);
        gameSaveManager.addCoin(1);
    }
}
