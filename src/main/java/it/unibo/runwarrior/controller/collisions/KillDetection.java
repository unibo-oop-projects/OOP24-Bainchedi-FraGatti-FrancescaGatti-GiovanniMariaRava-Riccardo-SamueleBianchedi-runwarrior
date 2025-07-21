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
     * Control if the collision is from above the enemy.
     *
     * @param playerArea player collision area
     * @param enemyArea enemy collision area
     * @return true if the player touches the enemy in his head
     */
    public boolean isTouchingUp(Rectangle playerArea, Rectangle enemyArea);

    /**
     * Controls if the given point (x, y) is touching a solid tile.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return true if the point touches a solid tile
     */
    public boolean isBehindTile(int x, int y);

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
