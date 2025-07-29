package it.unibo.runwarrior.controller.collisions.api;

import it.unibo.runwarrior.model.player.api.Character;
import it.unibo.runwarrior.controller.player.api.CharacterMovementHandler;

/**
 * Interface that handles collision between player and powerups.
 */
@SuppressWarnings("PMD.ImplicitFunctionalInterface")
public interface PowerUpDetection {

    /**
     * Checks when the player hit the egg, or take powerups.
     *
     * @param player current player
     * @param move player movement handler
     * @return the String of the collision direction
     */
    String checkCollisionWithPowers(Character player, CharacterMovementHandler move);
}
