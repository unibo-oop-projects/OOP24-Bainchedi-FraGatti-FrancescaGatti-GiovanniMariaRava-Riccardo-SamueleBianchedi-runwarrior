package it.unibo.runwarrior.controller.collisions;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.player.CharacterImpl;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.model.MapElement;

/**
 * Class that handles the collision between the player and the map tiles.
 */
public class CollisionDetectionImpl implements CollisionDetection {
    public static final int SEC_3 = 3000;
    public static final int FEET_HEAD_TOLL = 5;
    private final int map[][];
    private final List<MapElement> blocks;
    private int tileSize;
    private List<String> directions;
    private Rectangle playerArea;
    private final GameLoopPanel glp;
    private long hitWaitTime;
    private int gameOverY;
    private boolean end;

    /**
     * Constructor of the collision detection.
     *
     * @param map current map
     * @param blocks list of the block types
     * @param tileSize size of the tile
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public CollisionDetectionImpl(final int map[][], final List<MapElement> blocks, final int tileSize, final GameLoopPanel glp) {
        this.map = map;
        this.blocks = blocks;
        this.tileSize = tileSize;
        this.glp = glp;
        this.directions = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String checkCollision(final Character player) {
        playerArea = player.getArea();
        String dir = "";
        this.directions.clear();
        if (touchSolid(playerArea.x + playerArea.width - (FEET_HEAD_TOLL + 1), playerArea.y, player, true) |
            touchSolid(playerArea.x + playerArea.width, playerArea.y + playerArea.height / 2, player, true) |
            touchSolid(playerArea.x + playerArea.width, playerArea.y + playerArea.height, player, true) |
            touchSolid(playerArea.x + (FEET_HEAD_TOLL + 1), playerArea.y, player, true) |
            touchSolid(playerArea.x, playerArea.y + playerArea.height / 2, player, true) |
            touchSolid(playerArea.x, playerArea.y + playerArea.height, player, true)) {
                dir = directions.stream().filter(s -> "right".equals(s) | "left".equals(s))
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
     * {@inheritDoc}
     */
    @Override
    public boolean touchSolid(final int x, final int y, final Character player, final boolean checkDirections) {
        gameOverY = y;
        float indexXtile = x / tileSize;
        float indexYtile = y / tileSize;
        int blockIndex = map[(int) indexYtile][(int) indexXtile];
        end = blocks.get(blockIndex).isPortal();
        if (blocks.get(blockIndex).getCollision() || y <= 0){
            if (checkDirections) {
                this.directions.add(checkCollisionDirection(x, y, indexXtile, indexYtile, player));
            }
            if (!blocks.get(blockIndex).getHarmless() && System.currentTimeMillis() - hitWaitTime > SEC_3){
                hitWaitTime = System.currentTimeMillis();
                glp.getPowersHandler().losePower(false);
            }
            return true;
        } else {
            for (int i = playerArea.width; i >= 0; i = i - playerArea.width) {
                indexXtile = (playerArea.x + i) / tileSize;
                indexYtile = playerArea.y / tileSize;
                blockIndex = map[(int) indexYtile][(int) indexXtile];
                if (blocks.get(blockIndex).getCollision() && checkDirections) {
                    final String tempDir = checkCollisionDirection(x, y, indexXtile, indexYtile, player);
                    if ("right".equals(tempDir) || "left".equals(tempDir)) {
                        this.directions.add(tempDir);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String checkCollisionDirection(final int x, final int y, final float indexXtile, final float indexYtile, final Character player) {
        String direction = "";
        final int tileX = ((int) indexXtile) * tileSize;
        final int tileY = ((int) indexYtile) * tileSize;
        final Rectangle tileRec = new Rectangle(tileX, tileY, tileSize, tileSize);
        if (y == tileRec.y && x >= tileRec.x && x <= tileRec.x + tileRec.width) {
            direction = "up";
        }
        else if (isInAir(player) && y == playerArea.y && 
            (tileRec.y + tileRec.height - y) < it.unibo.runwarrior.controller.CharacterMovementHandlerImpl.SPEED_JUMP_UP &&
            x >= tileRec.x && x <= tileRec.x + tileRec.width || y <= 0) {
            direction = "down";
        }
        else if (x - CharacterImpl.SPEED <= tileRec.x) {
            direction = "right";
        }
        else if (x + CharacterImpl.SPEED >= tileRec.x + tileRec.width) {
            direction = "left";
        }
        //System.out.println("- " + x + " " + y + " - - " + direction + " - " + indexXtile + " " + indexYtile);
        return direction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInAir(final Character player) {
        return !touchSolid(player.getArea().x + FEET_HEAD_TOLL, player.getArea().y + player.getArea().height, player, false) &&
            !touchSolid(player.getArea().x + player.getArea().width - FEET_HEAD_TOLL, player.getArea().y + player.getArea().height, 
            player, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getHitWaitTime() {
        return this.hitWaitTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHitWaitTime(final long lastHit) {
        hitWaitTime = lastHit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean gameOver() {
        return gameOverY >= glp.getHeight() || end;
    }
}