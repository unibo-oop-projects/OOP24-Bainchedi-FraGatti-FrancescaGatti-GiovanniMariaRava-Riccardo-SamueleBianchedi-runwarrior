package it.unibo.runwarrior.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.runwarrior.model.GameMap;
import it.unibo.runwarrior.model.MapElement;

/**
 * TestHandlerMapElement check is the element are setted with the right caratheristics.
 * Check if the list return is correct.
 */
class TestHandlerMapElement {
    private static final int ROW_COLS = 3;
    private static final int SEVEN = 7;
    private static final int SIX = 6;
    private static final int FIVE = 5;
    private HandlerMapElement handlerMapElement;

    /**
     * Create a smaller map for the test and the initialize the map.
     */
    @BeforeEach
    void initMap() {
        final int[][] map = new int[][] {
            {0, 1, 2},
            {1, FIVE, 0},
            {3, 4, SIX},
        };

        final Map<Integer, BufferedImage> testBlockImages = new HashMap<>();
        for (int i = 0; i <= SIX; i++) {
            testBlockImages.put(i, null);
        }
        final GameMap gameMap = new GameMap(map, testBlockImages, ROW_COLS, ROW_COLS);
        handlerMapElement = new HandlerMapElement(gameMap);
    }

    /**
     * Test if the MapElement are initilized in the correct way.
     */
    @Test
    void testMapImageInitialization() {
        final List<MapElement> blocks = handlerMapElement.getBlocks();

        assertEquals(SEVEN, blocks.size());

        assertFalse(blocks.get(0).getCollision());
        assertTrue(blocks.get(1).getCollision()); 
        assertFalse(blocks.get(3).getCollision());
        assertFalse(blocks.get(FIVE).getHarmless());
    }

    /**
     * Test if the list returned contains the right element.
     */
    @Test
    void testGetCollisionRectangles() {
        final List<Rectangle> collisionRects = handlerMapElement.getCollisionRectangles();

        assertEquals(FIVE, collisionRects.size());
        final int tileSize = handlerMapElement.getTileSize();

        final Rectangle rectangleExpected = new Rectangle(1 * tileSize, 0 * tileSize, tileSize, tileSize);
        assertTrue(collisionRects.contains(rectangleExpected));
    }
}
