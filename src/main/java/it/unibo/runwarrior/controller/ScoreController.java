package it.unibo.runwarrior.controller;

import it.unibo.runwarrior.model.GameSaveManager;
import it.unibo.runwarrior.model.Score;

/**
 * Class that control game score.
 */
public class ScoreController {
    private final Score score; 
    private final GameSaveManager gameSaveManager;

    /**
     * Constructor of ScoreController.
     *
     * @param score object score
     */
    public ScoreController(final Score score) { 
        this.score = score; 
        this.gameSaveManager = GameSaveManager.getInstance();
    }

    /**
     * Increases the coin score by 1 both in the Score and in GameSaveManager templates.
     */
    public final void addCoin() {
        score.incrementCoinScore(1);
        gameSaveManager.addCoin(1);
    }
}
