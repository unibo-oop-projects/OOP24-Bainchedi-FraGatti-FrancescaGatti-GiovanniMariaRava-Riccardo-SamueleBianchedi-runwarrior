package it.unibo.runwarrior.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.runwarrior.model.GameMap;
import it.unibo.runwarrior.model.enemy.EnemyImpl;
import it.unibo.runwarrior.model.player.ArmourWarrior;
import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.player.CharacterImpl;
import it.unibo.runwarrior.model.player.NakedWarrior;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.PowerUpFactoryImpl;

public class TestPlayerCollisions {
    private CollisionDetection collisionTiles;
    private PowerUpDetection collisionPowerups;
    private KillDetection collisionEnemies;
    private GameLoopPanel glp;
    private GameMap gameMap1;
    private HandlerMapElement mapHandler1;
    private String string1Map1 = "Map_1/map_1.txt";
    private String string2Map1 = "Map_1/forest_theme.txt";

    @BeforeEach
    public void initCollisions() {
        glp = new GameLoopPanel();
        gameMap1 = GameMap.load(string1Map1, string2Map1);
        mapHandler1 = new HandlerMapElement(gameMap1);
    }

    @Test
    public void testCollisionTile() {
        // String string1Map2 = "";
        // String string2Map2 = "";

        Character player = new NakedWarrior(glp, null, mapHandler1, null);
        collisionTiles = new CollisionDetection(gameMap1.getMapData(), mapHandler1.getBlocks(), mapHandler1.getTileSize(), glp);

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
        PowerUpFactoryImpl pFact = new PowerUpFactoryImpl(glp, mapHandler1, mapHandler1.getMap());
        CharacterComand cmd = new CharacterComand();
        Character player = new ArmourWarrior(glp, cmd, mapHandler1, pFact);
        collisionPowerups = new PowerUpDetection(glp, pFact);

        assertTrue(collisionPowerups.isTouchingUp(new Rectangle(50, 50, 50, 50), 
        new Rectangle(70, 100, 50, 50)));
        assertFalse(collisionPowerups.isTouchingUp(new Rectangle(50, 50, 50, 50), 
        new Rectangle(70, 80, 50, 50)));

        player.getArea().setLocation(1960, 524);
        assertEquals("up", collisionPowerups.checkCollisionWithPowers(player, player.getMovementHandler()));
        player.getArea().setLocation(4610, 524);
        assertEquals("right", collisionPowerups.checkCollisionWithPowers(player, player.getMovementHandler()));
        player.getArea().setLocation(7343, 505);
        assertEquals("left", collisionPowerups.checkCollisionWithPowers(player, player.getMovementHandler()));
    }

    @Test
    public void testCollisionEnemies() {
        PowerUpFactoryImpl pFact = new PowerUpFactoryImpl(glp, mapHandler1, mapHandler1.getMap());
        CharacterComand cmd = new CharacterComand();
        Character player = new NakedWarrior(glp, cmd, mapHandler1, pFact);
        List<EnemyImpl> enemies = new ArrayList<>();
        enemies.add(new EnemyImpl(1836, 576, 36, 36, true, glp.getEnemyHandler(), glp, 0));
        enemies.add(new EnemyImpl(2664, 576, 36, 36, true, glp.getEnemyHandler(), glp, 0));
        glp.getEnemyHandler().getEnemies().addAll(enemies);
        collisionEnemies = new KillDetection(glp, mapHandler1);

        assertTrue(collisionEnemies.isBehindTile(11090, 585));
        assertFalse(collisionEnemies.isBehindTile(9360, 585));
        assertEquals(2, glp.getEnemyHandler().getEnemies().size());
        player.getArea().setLocation(1838, 520);
        collisionEnemies.checkCollisionWithEnemeies(player);
        assertTrue(collisionEnemies.isTouchingUp(player.getArea(), enemies.getFirst().getBounds()));
        assertEquals(1, glp.getEnemyHandler().getEnemies().size());

        player.getArea().setLocation(2650, 560);
        assertTrue(player.getArea().intersects(glp.getEnemyHandler().getEnemies().getFirst().getBounds()));
        int i = glp.getPowersHandler().getPowers();
        collisionEnemies.checkCollisionWithEnemeies(player);
        assertEquals((i - 1), glp.getPowersHandler().getPowers());
    }
}
