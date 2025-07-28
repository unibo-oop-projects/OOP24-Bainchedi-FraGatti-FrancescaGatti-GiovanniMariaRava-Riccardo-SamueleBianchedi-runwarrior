package it.unibo.runwarrior.controller;

import it.unibo.runwarrior.model.GameSaveManager;
import it.unibo.runwarrior.model.Score;

/**
 * Class that control game score.
 */
public class ScoreControllerImpl implements ScoreController {
    private final Score score; 
    private final GameSaveManager gameSaveManager;

    /**
     * Constructor of ScoreControllerImpl.
     *
     *@param score object score
     */
    public ScoreControllerImpl(final Score score) { 
        this.score = score; 
        this.gameSaveManager = GameSaveManager.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void addCoin() {
        //score.incrementCoinScore(1);
        gameSaveManager.setCoinCollected(score.getCoinScore());
        gameSaveManager.saveGame();
        //gameSaveManager.addCoin(1);
    }

    @Override
    public final Score getScore() {
        return this.score;
    }
}
