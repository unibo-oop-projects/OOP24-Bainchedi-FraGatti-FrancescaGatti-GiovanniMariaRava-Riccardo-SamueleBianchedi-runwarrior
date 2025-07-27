package it.unibo.runwarrior.controller.collisions;

import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.controller.CharacterMovementHandler;

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
