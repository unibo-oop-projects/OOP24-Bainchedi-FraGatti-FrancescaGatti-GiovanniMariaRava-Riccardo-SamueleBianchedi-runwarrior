package it.unibo.runwarrior.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.runwarrior.controller.collisions.CollisionDetection;
import it.unibo.runwarrior.controller.collisions.CollisionDetectionImpl;
import it.unibo.runwarrior.controller.collisions.KillDetection;
import it.unibo.runwarrior.controller.collisions.KillDetectionImpl;
import it.unibo.runwarrior.controller.collisions.PowerUpDetection;
import it.unibo.runwarrior.controller.collisions.PowerUpDetectionImpl;
import it.unibo.runwarrior.model.GameMap;
import it.unibo.runwarrior.model.enemy.impl.EnemyImpl;
import it.unibo.runwarrior.model.player.ArmourWarrior;
import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.player.AbstractCharacterImpl;
import it.unibo.runwarrior.model.player.NakedWarrior;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.PowerUpManager;

class TestPlayerCollisions {
    private static final int TRY_TYLE = 36;
    private GameLoopPanel glp;
    private CharacterComand cmd;
    private GameMap gameMap1;
    private HandlerMapElement mapHandler1;
    private int tileSize;
    private final String string1Map2 = "Map2/map2.txt";
    private final String string2Map2 = "Map2/forest_theme.txt";

    @BeforeEach
    void initCollisions() {
        glp = new GameLoopPanel("Map2/map2.txt", "Map2/forest_theme.txt", 
        "/Map2/enemiesMap2.txt", "/Coins/CoinCoordinates_map2.txt");
        cmd = new CharacterComand();
        gameMap1 = GameMap.load(string1Map2, string2Map2);
        mapHandler1 = new HandlerMapElement(gameMap1);
        tileSize = TRY_TYLE;
    }

    @Test
    void testCollisionTile() {
        // String string1Map2 = "";
        // String string2Map2 = "";

        final Character player = new NakedWarrior(glp, cmd, mapHandler1, null);
        final CollisionDetection collisionTiles = new CollisionDetectionImpl(gameMap1.getMapData(), mapHandler1.getBlocks(), tileSize, glp);

        collisionTiles.checkCollision(player);
        assertTrue(collisionTiles.touchSolid(8 * tileSize, 16 * tileSize, player, false));
        assertFalse(collisionTiles.touchSolid(39 * tileSize, 14 * tileSize, player, false));

        assertEquals("right", collisionTiles.checkCollisionDirection(12 * tileSize, 550, 
        12, 15, player));
        assertEquals("up", collisionTiles.checkCollisionDirection(605, 12 * tileSize, 
        16, 12, player));
        player.getArea().setLocation(9914, 500);
        assertEquals("down", collisionTiles.checkCollisionDirection(9914, 500, 
        275, 13, player));

        player.getArea().setLocation(56 * tileSize, 14 * tileSize);
        assertTrue(collisionTiles.isInAir(player));
        player.getArea().setLocation(75 * tileSize, (15 * tileSize) + (tileSize / 2) + 
        AbstractCharacterImpl.TO_TOUCH_FLOOR);
        assertFalse(collisionTiles.isInAir(player));
        player.getArea().setLocation(1222, (15 * tileSize) + (tileSize / 2) + 
        AbstractCharacterImpl.TO_TOUCH_FLOOR);
        assertEquals("left", collisionTiles.checkCollision(player));

        // GameMap gameMap2 = GameMap.load(string1Map2, string2Map2);
        // HandlerMapElement mapHandler2 = new HandlerMapElement(gameMap2);
        // collisionTiles = new CollisionDetection(gameMap2.getMapData(), mapHandler2.getBlocks(), mapHandler2.getTileSize());
    }

    @Test
    void testCollisionPowerup() {
        final PowerUpManager pMan = new PowerUpManager(glp, mapHandler1, mapHandler1.getMap());
        final Character player = new ArmourWarrior(glp, cmd, mapHandler1, pMan);
        final PowerUpDetection collisionPowerups = new PowerUpDetectionImpl(glp, pMan);

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
    void testCollisionEnemies() {
        final PowerUpManager pMan = new PowerUpManager(glp, mapHandler1, mapHandler1.getMap());
        final Character player = new NakedWarrior(glp, cmd, mapHandler1, pMan);
        final List<EnemyImpl> enemies = new ArrayList<>();
        enemies.add(new EnemyImpl(1836, 576, 36, 36, true, glp.getEnemyHandler(), glp, 0));
        enemies.add(new EnemyImpl(2664, 576, 36, 36, true, glp.getEnemyHandler(), glp, 0));
        glp.getEnemyHandler().getEnemies().addAll(enemies);
        final KillDetection collisionEnemies = new KillDetectionImpl(glp, mapHandler1);

        assertTrue(collisionEnemies.isBehindTile(3564, 585));
        assertFalse(collisionEnemies.isBehindTile(9360, 585));
        assertEquals(2, glp.getEnemyHandler().getEnemies().size());
        player.getArea().setLocation(1838, 520);
        collisionEnemies.checkCollisionWithEnemeies(player);
        assertTrue(collisionEnemies.isTouchingUp(player.getArea(), enemies.getFirst().getBounds()));
        assertEquals(1, glp.getEnemyHandler().getEnemies().size());

        player.getArea().setLocation(2650, 560);
        assertTrue(player.getArea().intersects(glp.getEnemyHandler().getEnemies().getFirst().getBounds()));
        final int i = glp.getPowersHandler().getPowers();
        collisionEnemies.checkCollisionWithEnemeies(player);
        assertEquals(i - 1, glp.getPowersHandler().getPowers());
    }

    @Test
    void testCoinCollision(){

    }
}
