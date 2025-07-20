package it.unibo.runwarrior.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.runwarrior.model.GameMap;
import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.player.CharacterImpl;
import it.unibo.runwarrior.model.player.NakedWarrior;
import it.unibo.runwarrior.view.GameLoopPanel;

public class TestPlayerCollisions {
    private CollisionDetection collisionTiles;
    private PowerUpDetection collisionPowerups;
    private KillDetection collisionEnemies;
    private GameLoopPanel glp;

    @BeforeEach
    public void initCollisions() {
        glp = new GameLoopPanel();
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
        collisionTiles = new CollisionDetection(gameMap1.getMapData(), mapHandler1.getBlocks(), mapHandler1.getTileSize(), glp);
        Character player = new NakedWarrior(glp, null, collisionTiles, mapHandler1, null);
        collisionTiles.checkCollision(player);
        assertTrue(collisionTiles.touchSolid((8 * mapHandler1.getTileSize()), (16 * mapHandler1.getTileSize()), player, false));
        assertFalse(collisionTiles.touchSolid((39 * mapHandler1.getTileSize()), (14 * mapHandler1.getTileSize()), player, false));

        assertEquals("right", collisionTiles.checkCollisionDirection(435, 550, 
        12, 15, player));
        assertEquals("up", collisionTiles.checkCollisionDirection(605, (12 * mapHandler1.getTileSize()), 
        16, 12, player));
        player.getArea().setLocation(9914, 500);
        assertEquals("down", collisionTiles.checkCollisionDirection(9914, 500, 
        275, 13, player));

        player.getArea().setLocation(56 * mapHandler1.getTileSize(), 14 * mapHandler1.getTileSize());
        assertTrue(collisionTiles.isInAir(player));
        player.getArea().setLocation(75 * mapHandler1.getTileSize(), (15 * mapHandler1.getTileSize()) + (mapHandler1.getTileSize() / 2) + 
        CharacterImpl.TO_TOUCH_FLOOR);
        assertFalse(collisionTiles.isInAir(player));
        player.getArea().setLocation(1222, (15 * mapHandler1.getTileSize()) + (mapHandler1.getTileSize() / 2) + 
        CharacterImpl.TO_TOUCH_FLOOR);
        assertEquals("left", collisionTiles.checkCollision(player));

        // GameMap gameMap2 = GameMap.load(string1Map2, string2Map2);
        // HandlerMapElement mapHandler2 = new HandlerMapElement(gameMap2);
        // collisionTiles = new CollisionDetection(gameMap2.getMapData(), mapHandler2.getBlocks(), mapHandler2.getTileSize());
    }

    @Test
    public void testCollisionPowerup() {

    }

    @Test
    public void testCollisionEnemies() {

    }
}
