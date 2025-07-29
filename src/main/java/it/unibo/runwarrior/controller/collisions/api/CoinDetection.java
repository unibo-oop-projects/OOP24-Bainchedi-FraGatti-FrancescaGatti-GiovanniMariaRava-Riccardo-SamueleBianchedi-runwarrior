package it.unibo.runwarrior.controller.collisions.api;

import it.unibo.runwarrior.model.player.Character;

/**
 * Interface that handles collision between player and coins.
 */
@SuppressWarnings("PMD.ImplicitFunctionalInterface")
public interface CoinDetection {

    /**
     * Check if the player takes coins.
     *
     * @param player current player
     */
    void controlCoinCollision(Character player);
}
