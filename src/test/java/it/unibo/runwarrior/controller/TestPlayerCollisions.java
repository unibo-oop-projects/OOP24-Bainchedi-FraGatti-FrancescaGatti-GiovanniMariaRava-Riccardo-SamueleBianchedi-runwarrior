package it.unibo.runwarrior.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.runwarrior.model.GameMap;
import it.unibo.runwarrior.view.GameLoopPanel;

public class TestPlayerCollisions {
    private CollisionDetection collisionTiles;
    private PowerUpDetection collisionPowerups;
    private KillDetection collisionEnemies;

    @BeforeEach
    public void initCollisions() {
        final GameLoopPanel glp = new GameLoopPanel();
        collisionPowerups = new PowerUpDetection(glp, null);
        collisionEnemies = new KillDetection(glp, null);
    }

    @Test
    public void testCollisionTile() {
        String string1Map1 = "Map_1/map_1.txt";
        String string2Map1 = "Map_1/forest_theme.txt";
        String string1Map2 = "";
        String string2Map2 = "";

        GameMap gameMap1 = GameMap.load(string1Map1, string2Map1);
        HandlerMapElement mapHandler1 = new HandlerMapElement(gameMap1);
        collisionTiles = new CollisionDetection(gameMap1.getMapData(), mapHandler1.getBlocks(), mapHandler1.getTileSize());

        GameMap gameMap2 = GameMap.load(string1Map2, string2Map2);
        HandlerMapElement mapHandler2 = new HandlerMapElement(gameMap2);
        collisionTiles = new CollisionDetection(gameMap2.getMapData(), mapHandler2.getBlocks(), mapHandler2.getTileSize());
    }

    @Test
    public void testCollisionPowerup() {

    }

    @Test
    public void testCollisionEnemies() {

    }
}
