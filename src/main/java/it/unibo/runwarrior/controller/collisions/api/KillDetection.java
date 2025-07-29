package it.unibo.runwarrior.controller.collisions.api;

import it.unibo.runwarrior.model.player.Character;

/**
 * Interfaces that handles collision between player and enemies.
 */
public interface KillDetection {
    /**
     * Checks the collision with every enemies present in the map.
     * If the player jump on their head, they die. If the touch the player from left or right, the player loses a life.
     * If the player has the sword, he can kill them with it.
     *
     * @param player current player
     */
    void checkCollisionWithEnemeies(Character player);

    /**
     * @return the moment when the player was hit.
     */
    long getHitWaitTime();

    /**
     * Set the last time the player was hit.
     *
     * @param lastHit time of the last hit
     */
    void setHitWaitTime(long lastHit);
}
