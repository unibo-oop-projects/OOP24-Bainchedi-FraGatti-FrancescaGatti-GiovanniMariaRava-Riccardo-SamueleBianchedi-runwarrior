package it.unibo.runwarrior.controller.collisions;

import it.unibo.runwarrior.model.player.Character; 

public interface CollisionDetection {

    /**
     * Controls the collision of the player with the map tiles.
     * To do so it calls touchSolid for 6 points of the player area:
     * top left, top right, mid left, mid right, bottom left, bottom right.
     *
     * @param player the player in the game panel
     * @return the decisive direction of the collision 
     */
    public String checkCollision(Character player);

    /**
     * Controls if the player touches a solid tile in his position.
     *
     * @param x x coordinate in pixel of the player
     * @param y y coordinate in pixel of the player
     * @param player the player in the game panel
     * @param checkDirections boolean to decide if the check of the direction is useful or not
     * @return true if the player touches a solid tile
     */
    public boolean touchSolid(int x, int y, Character player, boolean checkDirections);

    /**
     * Controls in which direction the player collides with a tile.
     *
     * @param x x coordinate in pixel of the player
     * @param y y coordinate in pixel of the player
     * @param indexXtile row of the tile
     * @param indexYtile column of the tile
     * @param player the player in the game panel
     * @return the string that specifies the direction of the collision
     */
    public String checkCollisionDirection(int x, int y, float indexXtile, float indexYtile, Character player);

    /**
     * Controls if the given player is in air, so if he doesn't touch the ground.
     *
     * @param player the player in the game panel
     * @return true if the player doesn't touch the ground
     */
    public boolean isInAir(Character player);

    /**
     * @return last time the player hit an obstacle
     */
    public long getHitWaitTime();

    /**
     * Set hitWaitTime
     *
     * @param lastHit last time of the player hitting an obstacle
     */
    public void setHitWaitTime(long lastHit);
}
