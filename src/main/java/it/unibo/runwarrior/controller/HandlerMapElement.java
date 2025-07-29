package it.unibo.runwarrior.controller;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import it.unibo.runwarrior.model.GameMap;
import it.unibo.runwarrior.model.MapElement;
import it.unibo.runwarrior.model.player.api.Character;
import it.unibo.runwarrior.view.GameLoopPanel;

/**
 * The class uses the gamemap to render the map and set collision and damages.
 */
public class HandlerMapElement {
    private static final int OBSTACLE_BLOCK = 5;
    private static final int ANOTHER_OBSTACLE_BLOCK = 6;

    private final List<MapElement> blocks;
    private final int[][] map;
    private final Map<Integer, BufferedImage> mapBlock;
    private int shift;
    private boolean setStart;
    private int firstY;

    /**
     * Constructor of the class HandlerMapElement.
     *
     * @param gamemap the game map object containing map data and images.
     */
    public HandlerMapElement(final GameMap gamemap) {
        this.blocks = new ArrayList<>();
        this.mapBlock = gamemap.getBlockImages();
        this.map = gamemap.getMapData();
        mapImage();
    }

    /**
     * This method create a new MapElement using the map created in GameMap.
     */
    private void mapImage() {
        for (final var entry : mapBlock.entrySet()) {
            final MapElement newElement = new MapElement();
            newElement.setImage(entry.getValue());
            switch (entry.getKey()) {
                case 0, 4:
                    newElement.setHarmless(true);
                    newElement.setCollision(false);
                    break;
                case 1, 2:
                    newElement.setHarmless(true);
                    newElement.setCollision(true);
                    break;
                case 3:
                    newElement.setCollision(false);
                    newElement.setHarmless(true);
                    newElement.setPortal(true);
                    break;
                case OBSTACLE_BLOCK, ANOTHER_OBSTACLE_BLOCK:
                    newElement.setHarmless(false);
                    newElement.setCollision(true);
                    break;
                default:
                    break;
            }
            blocks.add(newElement);
        }
    }

    /**
     * Renders all the blocks of the map.
     *
     * @param gr     the graphics context.
     * @param player the player character.
     */
    public void printBlocks(final Graphics2D gr, final Character player) {
        final int rows = map.length;
        final int cols = map[0].length;
        shift = player.getMovementHandler().getGroundX();
        final int tileHeight = GameLoopPanel.HEIGHT / rows;
        final int tileSize = tileHeight;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                final BufferedImage img = this.blocks.get(map[y][x]).getImage();
                gr.drawImage(img, x * tileSize + shift, y * tileSize, tileSize, tileSize, null);
            }
        }
    }

    /**
     * Gets an unmodifiable list of map elements (blocks).
     *
     * @return an unmodifiable list of blocks.
     */
    public List<MapElement> getBlocks() {
        return Collections.unmodifiableList(this.blocks);
    }

    /**
     * Sets the horizontal shift for rendering.
     *
     * @param slide the shift value.
     */
    public void setShift(final int slide) {
        shift = slide;
    }

    /**
     * Gets the size of a single tile.
     *
     * @return the dimension of the tile.
     */
    public int getTileSize() {
        final int rows = map.length;
        return GameLoopPanel.HEIGHT / rows;
    }

    /**
     * Calculates the starting Y position for the player.
     *
     * @return the starting Y coordinate.
     */
    public int getFirstY() {
        final int rows = map.length;
        final int cols = map[0].length;
        final int tileSize = GameLoopPanel.HEIGHT / rows;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (x == 0 && map[y][x] == 1 && !setStart) {
                    firstY = (y * tileSize) - (tileSize * 2);
                    setStart = true;
                }
            }
        }
        return firstY;
    }

    /**
     * Gets a list of all rectangles with collision enabled.
     *
     * @return the list with all the obstacle's rectangles.
     */
    public List<Rectangle> getCollisionRectangles() {
        final List<Rectangle> collisionRects = new ArrayList<>();
        final int rows = map.length;
        final int cols = map[0].length;
        final int tileHeight = GameLoopPanel.HEIGHT / rows;
        final int currentTileSize = tileHeight;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (blocks.get(map[y][x]).isCollision()) {
                    final Rectangle r = new Rectangle(x * currentTileSize, y * currentTileSize,
                                                      currentTileSize, currentTileSize);
                    collisionRects.add(r);
                }
            }
        }
        return collisionRects;
    }

    /**
     * Gets the current horizontal shift value.
     *
     * @return the shift value.
     */
    public int getShift() {
        return this.shift;
    }

    /**
     * Gets a deep copy of the raw map data array.
     *
     * @return a deep copy of the 2D map array.
     */
    public int[][] getMap() {
        final int[][] deepCopy = new int[this.map.length][];
        for (int i = 0; i < this.map.length; i++) {
            deepCopy[i] = this.map[i].clone();
        }
        return deepCopy;
    }
}
