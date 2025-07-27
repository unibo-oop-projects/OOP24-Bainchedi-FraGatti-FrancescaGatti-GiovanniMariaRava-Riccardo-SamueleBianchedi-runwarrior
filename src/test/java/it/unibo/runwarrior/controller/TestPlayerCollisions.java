package it.unibo.runwarrior.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.runwarrior.controller.collisions.CoinDetectionImpl;
import it.unibo.runwarrior.controller.collisions.CollisionDetectionImpl;
import it.unibo.runwarrior.controller.collisions.KillDetectionImpl;
import it.unibo.runwarrior.controller.collisions.PowerUpDetectionImpl;
import it.unibo.runwarrior.model.GameMap;
import it.unibo.runwarrior.model.GameSaveManager;
import it.unibo.runwarrior.model.Score;
import it.unibo.runwarrior.model.enemy.impl.EnemyImpl;
import it.unibo.runwarrior.model.player.ArmourWarrior;
import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.player.AbstractCharacterImpl;
import it.unibo.runwarrior.model.player.NakedWarrior;

class TestPlayerCollisions {
    private static final int TRY_TYLE = 36;
    private static final int TOLL = 5;
    private GameLoopController glc;
    private CharacterComand cmd;
    private GameMap gameMap1;
    private HandlerMapElement mapHandler1;
    private int tileSize;
    private final String string1Map2 = "tryMap.txt";
    private final String string2Map2 = "Map2/forest_theme.txt";

    @BeforeEach
    void initCollisions() {
        gameMap1 = GameMap.load(string1Map2, string2Map2);
        mapHandler1 = new HandlerMapElement(gameMap1);
        cmd = new CharacterComand();
        glc = new GameLoopController("tryMap.txt", "Map2/forest_theme.txt", 
        "/Map2/enemiesMap2.txt", "/Coins/CoinCoordinates_map2.txt");
        tileSize = TRY_TYLE;
    }

    private boolean isTouchingUp(final Rectangle playerArea, final Rectangle enemyArea){
        return playerArea.y + playerArea.height <= enemyArea.y && 
        ((playerArea.x + TOLL >= enemyArea.x && playerArea.x + TOLL <= enemyArea.x + enemyArea.width) ||
        (playerArea.x + playerArea.width - TOLL >= enemyArea.x && playerArea.x + playerArea.width - TOLL <= enemyArea.x + enemyArea.width));
    }

    private boolean isBehindTile(final int x, final int y, final HandlerMapElement hM) {
        final float indexXtile = x / hM.getTileSize();
        final float indexYtile = y / hM.getTileSize();
        final int blockIndex = hM.getMap()[(int) indexYtile][(int) indexXtile];
        return hM.getBlocks().get(blockIndex).getCollision();
    }

    @Test
    void testCollisionTile() {
        final Character player = new NakedWarrior(glc, cmd, mapHandler1, null);
        final CollisionDetectionImpl collisionTiles = new CollisionDetectionImpl(gameMap1.getMapData(), mapHandler1.getBlocks(), tileSize, glc);

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
    }

    @Test
    void testCollisionPowerup() {
        final PowerUpController pCon = new PowerUpController(glc, mapHandler1, mapHandler1.getMap());
        final Character player = new ArmourWarrior(glc, cmd, mapHandler1, pCon);
        final PowerUpDetectionImpl collisionPowerups = new PowerUpDetectionImpl(glc, pCon);

        assertTrue(isTouchingUp(new Rectangle(50, 50, 50, 50), 
        new Rectangle(70, 100, 50, 50)));
        assertFalse(isTouchingUp(new Rectangle(50, 50, 50, 50), 
        new Rectangle(70, 80, 50, 50)));

        player.getArea().setLocation(1984, 533);
        assertEquals("up", collisionPowerups.checkCollisionWithPowers(player, player.getMovementHandler()));
        player.getArea().setLocation(3290, 560);
        assertEquals("right", collisionPowerups.checkCollisionWithPowers(player, player.getMovementHandler()));
        player.getArea().setLocation(7343, 560);
        assertEquals("left", collisionPowerups.checkCollisionWithPowers(player, player.getMovementHandler()));
    }

    @Test
    void testCollisionEnemies() {
        final PowerUpController pCon = new PowerUpController(glc, mapHandler1, mapHandler1.getMap());
        final Character player = new NakedWarrior(glc, cmd, mapHandler1, pCon);
        final List<EnemyImpl> enemies = new ArrayList<>();
        enemies.add(new EnemyImpl(1836, 576, 36, 36, true, glc.getEnemyHandler(), glc, 0));
        enemies.add(new EnemyImpl(2664, 576, 36, 36, true, glc.getEnemyHandler(), glc, 0));
        glc.getEnemyHandler().getEnemies().addAll(enemies);
        final KillDetectionImpl collisionEnemies = new KillDetectionImpl(glc, mapHandler1);

        assertTrue(isBehindTile(3564, 585, mapHandler1));
        assertFalse(isBehindTile(9360, 585, mapHandler1));
        assertEquals(2, glc.getEnemyHandler().getEnemies().size());
        player.getArea().setLocation(1838, 520);
        collisionEnemies.checkCollisionWithEnemeies(player);
        assertTrue(isTouchingUp(player.getArea(), enemies.getFirst().getBounds()));
        assertEquals(1, glc.getEnemyHandler().getEnemies().size());

        player.getArea().setLocation(2650, 560);
        assertTrue(player.getArea().intersects(glc.getEnemyHandler().getEnemies().getFirst().getBounds()));
        final int i = glc.getPowersHandler().getPowers();
        collisionEnemies.checkCollisionWithEnemeies(player);
        assertEquals(i - 1, glc.getPowersHandler().getPowers());
    }

    @Test
    void testCoinCollision(){
        CoinController coinController = new CoinController();
        Score score = new Score(GameSaveManager.getInstance());
        ScoreController scoreController = new ScoreController(score);
        final Character player = new NakedWarrior(glc, cmd, mapHandler1, null);
        final CoinDetectionImpl collisionCoins = new CoinDetectionImpl(tileSize, coinController, scoreController);

        coinController.addCoins(16, 74);
        //coinController.initCoinsFromFile("/Coins/CoinCoordinates_map2.txt");
        assertTrue(coinController.getCoinsCollected() == 0);
        player.getArea().setLocation(2633, 560);
        //player.getArea().setLocation(1661, 560);
        collisionCoins.controlCoinCollision(player);
        assertTrue(coinController.getCoinsCollected() == 1);
    }
}
