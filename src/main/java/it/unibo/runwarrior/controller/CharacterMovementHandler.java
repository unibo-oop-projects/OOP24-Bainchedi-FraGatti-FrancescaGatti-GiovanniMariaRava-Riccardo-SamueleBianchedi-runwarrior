package it.unibo.runwarrior.controller;

import it.unibo.runwarrior.controller.collisions.CollisionDetectionImpl;
import it.unibo.runwarrior.controller.collisions.KillDetectionImpl;

public interface CharacterMovementHandler {
    
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
    public void setLocationAfterPowerup(int x, int y, int realx, int groundX, long lastHit);

    /**
     * Method that handles every part of player movement:
     * it controls collisions, it calls jump and when it's possibile makes the player go left or right.
     */
    public void movePlayer();

    /**
     * When it's called make the player jump or descend if the jump input from keyboard has ended.
     *
     * @param isJump true if the player is jumping
     * @param jumpHeight max height of the jump
     */
    public void jump(boolean isJump, int jumpHeight);

    /**
     * Sets the jumpKill to true, used by powerup and kill detection when the player hit a egg or a enemy.
     */
    public void setJumpKill();

    /**
     * Makes the player jump after a kill or after breaking a egg.
     */
    public void jumpAfterKill();

    /**
     * Updates the values of max and mid jump based on where the player is.
     */
    public void updateJumpVariable();

    /**
     * @return true if the player can attack
     */
    public boolean canAttack();

     /**
     * @return map tiles collision object
     */
    public CollisionDetectionImpl getCollisionDetection();

    /**
     * @return kill detection object
     */
    public KillDetectionImpl getKillDetection();

    /**
     * @return true if the player is going to the right
     */
    public boolean getRightDirection();

    /**
     * @return the variable used to make the map slide
     */
    public int getGroundX();

    /**
     * @return the real horizontal position
     */
    public int getPlX();

    /**
     * @return the vertical player position on both screen and map
     */
    public int getPlY();

    /**
     * @return the horizontal player position on the screen
     */
    public int getScX();
}
