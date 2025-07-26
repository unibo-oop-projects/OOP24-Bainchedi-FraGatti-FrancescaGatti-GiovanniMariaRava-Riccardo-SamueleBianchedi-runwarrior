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
    String checkCollisionWithPowers(Character player, CharacterMovementHandler move);

    /**
     * Creates the future area of the falling player
     *
     * @param r1 collision area
     * @param pl player
     * @return the collision area the player will have
     */
    Rectangle futureArea(Rectangle r1);

    /**
     * Control if the collision is from above the powerup.
     *
     * @param playerArea player collision area
     * @param pUpArea powerup collision area
     * @return true if the player touches the powerup with his feet
     */
    boolean isTouchingUp(Rectangle playerArea, Rectangle pUpArea);
}
