package it.unibo.runwarrior.controller;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.player.CharacterImpl;
import it.unibo.runwarrior.model.MapElement;

/**
 * Class that handles the collision between the player and the map tiles
 */
public class CollisionDetection {
    public static final int FEET_HEAD_TOLL = 5;
    private int map[][];
    private List<MapElement> blocks = new ArrayList<>();
    private int tileSize;
    private ArrayList<String> directions = new ArrayList<>();
    private Rectangle playerArea;

    /**
     * Constructor of the collision detection.
     *
     * @param map current map
     * @param blocks list of the block types
     * @param tileSize size of the tile
     */
    public CollisionDetection(final int map[][], final List<MapElement> blocks, final int tileSize) {
        this.map = map;
        this.blocks = blocks;
        this.tileSize = tileSize;
    }

    /**
     * Controls the collision of the player with the map tiles.
     * To do so it calls touchSolid for 6 points of the player area:
     * top left, top right, mid left, mid right, bottom left, bottom right.
     *
     * @param player the player in the game panel
     * @return the decisive direction of the collision 
     */
    public String checkCollision(Character player) {
        playerArea = player.getArea();
        String dir = "";
        this.directions.clear();
        if (touchSolid(playerArea.x + playerArea.width - (FEET_HEAD_TOLL + 1), playerArea.y, player, true) |
            touchSolid(playerArea.x + playerArea.width, playerArea.y + playerArea.height / 2, player, true) |
            touchSolid(playerArea.x + playerArea.width, playerArea.y + playerArea.height, player, true) |
            touchSolid(playerArea.x + (FEET_HEAD_TOLL + 1), playerArea.y, player, true) |
            touchSolid(playerArea.x, playerArea.y + playerArea.height / 2, player, true) |
            touchSolid(playerArea.x, playerArea.y + playerArea.height, player, true)) {
                dir = directions.stream().filter(s -> s.equals("right") | s.equals("left"))
                        .distinct().findFirst().orElse("");
        }
        if (dir.isEmpty() && directions.contains("up")) {
            dir = "up";
        }
        if (directions.contains("down")) {
            dir = "down";
        }
        return dir;
    }

    /**
     * Controls if the player touches a solid tile in his position.
     *
     * @param x x coordinate in pixel of the player
     * @param y y coordinate in pixel of the player
     * @param player the player in the game panel
     * @param checkDirections boolean to decide if the check of the direction is useful or not
     * @return true if the player touches a solid tile
     */
    public boolean touchSolid(int x, int y, Character player, boolean checkDirections) {
        int[][] blockIndexes = map;
        float indexXtile = x / tileSize;
        float indexYtile = y / tileSize;
        int blockIndex = blockIndexes[(int) indexYtile][(int) indexXtile];
        if (blocks.get(blockIndex).getCollision() || y <= 0){
            if (checkDirections) {
                this.directions.add(checkCollisionDirection(x, y, indexXtile, indexYtile, player));
            }
            return true;
        } else {
            for (int i = playerArea.width; i >= 0; i = i - playerArea.width) {
                indexXtile = (playerArea.x + i) / tileSize;
                indexYtile = playerArea.y / tileSize;
                blockIndex = blockIndexes[(int) indexYtile][(int) indexXtile];
                if (blocks.get(blockIndex).getCollision()) {
                    if (checkDirections) {
                        String tempDir = checkCollisionDirection(x, y, indexXtile, indexYtile, player);
                        if (tempDir.equals("right") || tempDir.equals("left")) {
                            this.directions.add(tempDir);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

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
    public String checkCollisionDirection(int x, int y, float indexXtile, float indexYtile, Character player){
        String direction = "";
        int tileX = ((int) indexXtile) * tileSize;
        int tileY = ((int) indexYtile) * tileSize;
        Rectangle tileRec = new Rectangle(tileX, tileY, tileSize, tileSize);
        if (y == tileRec.y && (x >= tileRec.x && x <= tileRec.x + tileRec.width)) {
            direction = "up";
        }
        else if (isInAir(player) && y == playerArea.y && (tileRec.y + tileRec.height - y) < CharacterMovementHandlerImpl.SPEED_JUMP_UP &&
                (x >= tileRec.x && x <= tileRec.x + tileRec.width) || y <= 0) {
                direction = "down";
        }
        else if (x - CharacterImpl.SPEED <= tileRec.x) {
            direction = "right";
        }
        else if (x + CharacterImpl.SPEED >= tileRec.x + tileRec.width) {
            direction = "left";
        }
        System.out.println("- " + x + " " + y + " - - " + direction + " - " + indexXtile + " " + indexYtile);
        return direction;
    }

    /**
     * Controls if the given player is in air, so if he doesn't touch the ground.
     *
     * @param player the player in the game panel
     * @return true if the player doesn't touch the ground
     */
    public boolean isInAir(Character player) {
        if (!touchSolid(player.getArea().x + FEET_HEAD_TOLL, player.getArea().y + player.getArea().height, player, false) &&
            !touchSolid(player.getArea().x + player.getArea().width - FEET_HEAD_TOLL, player.getArea().y + player.getArea().height, player, false)){
                return true;
        }
        return false;
    }
}