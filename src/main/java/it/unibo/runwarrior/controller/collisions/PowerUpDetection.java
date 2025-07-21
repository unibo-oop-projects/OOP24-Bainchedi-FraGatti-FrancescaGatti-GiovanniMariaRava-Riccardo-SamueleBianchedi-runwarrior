package it.unibo.runwarrior.controller.collisions;

import java.awt.Rectangle;

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
    public String checkCollisionWithPowers(Character player, CharacterMovementHandler move);

    /**
     * Creates the future area of the falling player
     *
     * @param r1 collision area
     * @param pl player
     * @return the collision area the player will have
     */
    public Rectangle futureArea(Rectangle r1);
}
