package it.unibo.runwarrior.controller.collisions;

import it.unibo.runwarrior.model.player.Character;

public interface CoinDetection {
    
    /**
     * Check if the player takes coins.
     *
     * @param player current player
     */
    void controlCoinCollision(final Character player);
}
