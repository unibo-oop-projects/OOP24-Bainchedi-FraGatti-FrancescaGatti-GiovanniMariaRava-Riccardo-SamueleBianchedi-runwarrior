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

public class TestHandlerMapElement {
    private static final int ROW_COLS = 3;
    private HandlerMapElement handlerMapElement;
    private GameMap gameMap;
    private int [] [] map;
    //per ora testo solo il primo metood. gli altri capisco se ha veramente senso 
    /**
     * Creo una mappa più piccola e cono solo 0 e 1 così poi controllo che setti bene collisioni e danni
    */
    @BeforeEach
    public void initMap(){
        map = new int[][] {
            {0, 1, 2},
            {1, 5, 0},
            {3, 4, 6}
        };

        Map<Integer, BufferedImage> testBlockImages = new HashMap<>();
        for (int i = 0; i <= 6; i++) {
            testBlockImages.put(i, null);
        }
        gameMap = new GameMap (map, testBlockImages, ROW_COLS, ROW_COLS);
        handlerMapElement = new HandlerMapElement(gameMap);
    }
    @Test
    void testMapImageInitialization() {
        List<MapElement> blocks = handlerMapElement.getBlocks();

        assertEquals(7, blocks.size());

        assertFalse(blocks.get(0).getCollision());
        assertTrue(blocks.get(1).getCollision()); 
        assertFalse(blocks.get(3).getCollision());
        assertFalse(blocks.get(5).getHarmless());
    }
    @Test
    public void testGetCollisionRectangles(){
        List<Rectangle> collisionRects = handlerMapElement.getCollisionRectangles();

        assertEquals(5, collisionRects.size());
        final int tileSize = handlerMapElement.getTileSize();

        Rectangle rectangleExpected = new Rectangle(1*tileSize, 0*tileSize, tileSize, tileSize);

        assertTrue(collisionRects.contains(rectangleExpected));
    }
}
