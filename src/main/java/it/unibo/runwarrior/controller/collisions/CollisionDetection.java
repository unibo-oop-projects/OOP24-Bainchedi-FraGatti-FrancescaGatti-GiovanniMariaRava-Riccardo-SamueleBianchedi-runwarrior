package it.unibo.runwarrior.controller.collisions;

import it.unibo.runwarrior.model.player.Character; 

/**
 * Interface that handles collision with map tiles.
 */
public interface CollisionDetection {

    /**
     * Controls the collision of the player with the map tiles.
     * To do so it calls touchSolid for 6 points of the player area:
     * top left, top right, mid left, mid right, bottom left, bottom right.
     *
     * @param player the player in the game panel
     * @return the decisive direction of the collision 
     */
    String checkCollision(Character player);

    /**
     * Controls if the player touches a solid tile in his position.
     * The second part controls that the player doesn't go through tiles while jumping.
     *
     * @param x x coordinate in pixel of the player
     * @param y y coordinate in pixel of the player
     * @param checkDirections boolean to decide if the check of the direction is useful or not
     * @return true if the player touches a solid tile
     */
    boolean touchSolid(int x, int y, boolean checkDirections);

    /**
     * Controls in which direction the player collides with a tile.
     *
     * @param x x coordinate in pixel of the player
     * @param y y coordinate in pixel of the player
     * @param indexXtile row of the tile
     * @param indexYtile column of the tile
     * @return the string that specifies the direction of the collision
     */
    String checkCollisionDirection(int x, int y, int indexXtile, int indexYtile);

    /**
     * Controls if the given player is in air, so if he doesn't touch the ground.
     *
     * @param player the player in the game panel
     * @return true if the player doesn't touch the ground
     */
    boolean isInAir(Character player);

    /**
     * @return last time the player hit an obstacle
     */
    long getHitWaitTime();

    /**
     * Set hitWaitTime.
     *
     * @param lastHit last time of the player hitting an obstacle
     */
    void setHitWaitTime(long lastHit);

    /**
     * @return true if the game has to be stopped
     */
    boolean gameOver();
}
