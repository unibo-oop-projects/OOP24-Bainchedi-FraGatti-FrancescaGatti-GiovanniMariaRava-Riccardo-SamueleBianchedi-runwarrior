package it.unibo.runwarrior.controller.collisions;

import java.awt.Rectangle;
import it.unibo.runwarrior.model.player.Character;

public interface KillDetection {
    /**
     * Checks the collision with every enemies present in the map.
     * If the player jump on their head, they die. If the touch the player from left or right, the player loses a life.
     * If the player has the sword, he can kill them with it.
     *
     * @param player current player
     */
    public void checkCollisionWithEnemeies(Character player);

    /**
     * Creates the future area of the falling player
     *
     * @param r1 collision area
     * @param pl player
     * @return the collision area the player will have
     */
    public Rectangle futureArea(Rectangle r1);

    /**
     * @return the moment when the player was hit.
     */
    public long getHitWaitTime();

    /**
     * Set the last time the player was hit.
     *
     * @param lastHit time of the last hit
     */
    public void setHitWaitTime(long lastHit);
}
