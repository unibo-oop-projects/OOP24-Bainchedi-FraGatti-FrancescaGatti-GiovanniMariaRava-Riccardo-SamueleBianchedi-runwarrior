package it.unibo.runwarrior.controller;

import it.unibo.runwarrior.controller.collisions.CollisionDetectionImpl;
import it.unibo.runwarrior.controller.collisions.KillDetectionImpl;

/**
 * Interface that handles player movement.
 */
public interface CharacterMovementHandler {

    /**
     * Set the initial position of the player.
     *
     * @param y y coordinate
     */
    void setStartY(int y);

    /**
     * Sets the position of the new player when he takes powerups or loses them when he's hit by a enemy.
     * This include the change of the skin and life.
     *
     * @param x screen x coordinate in pixel
     * @param y y coordinate in pixel
     * @param realx x coordinate in pixel
     * @param groundX variable to slide map
     * @param lastHit time of the last hit with an enemy
     */
    void setLocationAfterPowerup(int x, int y, int realx, int groundX, long lastHit);

    /**
     * Method that handles every part of player movement:
     * it controls collisions, it calls jump and when it's possibile makes the player go left or right.
     */
    void movePlayer();

    /**
     * When it's called make the player jump or descend if the jump input from keyboard has ended.
     *
     * @param isJump true if the player is jumping
     * @param jumpHeight max height of the jump
     */
    void jump(boolean isJump, int jumpHeight);

    /**
     * Sets the jumpKill to true, used by powerup and kill detection when the player hit a egg or a enemy.
     */
    void setJumpKill();

    /**
     * Makes the player jump after a kill or after breaking a egg.
     */
    void jumpAfterKill();

    /**
     * @return true if the player can attack
     */
    boolean canAttack();

    /**
     * @return map tiles collision object
     */
    CollisionDetectionImpl getCollisionDetection();

    /**
     * @return kill detection object
     */
    KillDetectionImpl getKillDetection();

    /**
     * @return true if the player is going to the right
     */
    boolean isRightDirection();

    /**
     * @return the variable used to make the map slide
     */
    int getGroundX();

    /**
     * @return the real horizontal position
     */
    int getPlX();

    /**
     * @return the vertical player position on both screen and map
     */
    int getPlY();

    /**
     * @return the horizontal player position on the screen
     */
    int getScX();
}
