package it.unibo.runwarrior.controller.collisions;

import it.unibo.runwarrior.model.player.Character;

/**
 * Interface that handles collision between player and coins
 */
public interface CoinDetection {
    
    /**
     * Check if the player takes coins.
     *
     * @param player current player
     */
    void controlCoinCollision(Character player);
}
