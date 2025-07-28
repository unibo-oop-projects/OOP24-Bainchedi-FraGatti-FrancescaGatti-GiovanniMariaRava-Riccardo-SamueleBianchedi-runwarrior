package it.unibo.runwarrior.controller.score.api;

import it.unibo.runwarrior.model.Score;

/**
 * ScoreController interface.
 */
public interface ScoreController {
    /**
     * Increases the coin score by 1 both in the Score and in GameSaveManager templates.
     */
    void addCoin();

    /**
     * @return score
     */
    Score getScore();
}
