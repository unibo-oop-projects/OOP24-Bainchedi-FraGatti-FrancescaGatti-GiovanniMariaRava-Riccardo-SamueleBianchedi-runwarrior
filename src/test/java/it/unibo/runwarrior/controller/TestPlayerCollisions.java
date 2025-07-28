package it.unibo.runwarrior.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Rectangle;

import javax.swing.JFrame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.runwarrior.controller.collisions.CoinDetectionImpl;
import it.unibo.runwarrior.controller.collisions.CollisionDetectionImpl;
import it.unibo.runwarrior.controller.collisions.KillDetectionImpl;
import it.unibo.runwarrior.controller.collisions.PowerUpDetectionImpl;
import it.unibo.runwarrior.model.GameMap;
import it.unibo.runwarrior.model.Score;
import it.unibo.runwarrior.model.enemy.impl.EnemyImpl;
import it.unibo.runwarrior.model.player.ArmourWarrior;
import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.player.AbstractCharacterImpl;
import it.unibo.runwarrior.model.player.NakedWarrior;

/**
 * Test collision between entities and player, and consequences.
 */
class TestPlayerCollisions {
    private static final int FIFTY = 50;
    private static final int POSITION1 = 9914;
    private static final int FIFTEEN = 15;
    private static final int FIVE_SIXTY = 560;
    private static final int TWELVE = 12;
    // private static final int
    // private static final int
    // private static final int
    // private static final int
    // private static final int
    // private static final int
    private static final int TRY_TYLE = 36;
    private static final int TOLL = 5;
    private static final String FIRST_STRING = "tryMap.txt";
    private static final String SECOND_STRING = "Map2/forest_theme.txt";
    private final JFrame testFrame = new JFrame();
    private GameLoopController glc;
    private CharacterComand cmd;
    private GameMap gameMap1;
    private HandlerMapElement mapHandler1;
    private int tileSize;

    @BeforeEach
    void initCollisions() {
        gameMap1 = GameMap.load(FIRST_STRING, SECOND_STRING);
        mapHandler1 = new HandlerMapElement(gameMap1);
        cmd = new CharacterComand();
        glc = new GameLoopController(testFrame, FIRST_STRING, SECOND_STRING, 
        "/Map2/enemiesMap2.txt", "/Coins/CoinCoordinates_map2.txt");
        tileSize = TRY_TYLE;
    }

    private boolean isTouchingUp(final Rectangle playerArea, final Rectangle enemyArea) {
        return playerArea.y + playerArea.height <= enemyArea.y 
        && (playerArea.x + TOLL >= enemyArea.x && playerArea.x + TOLL <= enemyArea.x + enemyArea.width 
        || playerArea.x + playerArea.width - TOLL >= enemyArea.x 
        && playerArea.x + playerArea.width - TOLL <= enemyArea.x + enemyArea.width);
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
        final CollisionDetectionImpl collisionTiles = new CollisionDetectionImpl(gameMap1.getMapData(),
        mapHandler1.getBlocks(), tileSize, glc);

        collisionTiles.checkCollision(player);
        assertTrue(collisionTiles.touchSolid(12 * tileSize, FIFTEEN * tileSize, false));
        assertFalse(collisionTiles.touchSolid(FIFTY * tileSize, FIFTEEN * tileSize, false));

        assertEquals("right", collisionTiles.checkCollisionDirection(TWELVE * tileSize, FIVE_SIXTY, 
        TWELVE, FIFTEEN));
        assertEquals("up", collisionTiles.checkCollisionDirection(16 * tileSize, TWELVE * tileSize, 
        16, TWELVE));
        player.getArea().setLocation(POSITION1, FIFTY * 10);
        assertEquals("down", collisionTiles.checkCollisionDirection(POSITION1, FIFTY * 10, 
        275, 13));

        player.getArea().setLocation(56 * tileSize, 14 * tileSize);
        assertTrue(collisionTiles.isInAir(player));
        player.getArea().setLocation(75 * tileSize, (FIFTEEN * tileSize) + (tileSize / 2)  
        + AbstractCharacterImpl.TO_TOUCH_FLOOR);
        assertFalse(collisionTiles.isInAir(player));
        player.getArea().setLocation(1222, (FIFTEEN * tileSize) + (tileSize / 2)  
        + AbstractCharacterImpl.TO_TOUCH_FLOOR);
        assertEquals("left", collisionTiles.checkCollision(player));
    }

    @Test
    void testCollisionPowerup() {
        final PowerUpController pCon = new PowerUpController(glc, mapHandler1, mapHandler1.getMap());
        final Character player = new NakedWarrior(glc, cmd, mapHandler1, pCon);
        final PowerUpDetectionImpl collisionPowerups = new PowerUpDetectionImpl(glc, pCon);

        assertTrue(isTouchingUp(new Rectangle(FIFTY, FIFTY, FIFTY, FIFTY), 
        new Rectangle(70, 100, FIFTY, FIFTY)));
        assertFalse(isTouchingUp(new Rectangle(FIFTY, FIFTY, FIFTY, FIFTY), 
        new Rectangle(70, 80, FIFTY, FIFTY)));

        player.getArea().setLocation(1984, 533);
        assertEquals("up", collisionPowerups.checkCollisionWithPowers(player, player.getMovementHandler()));
        player.getArea().setLocation(3290, FIVE_SIXTY);
        assertEquals("right", collisionPowerups.checkCollisionWithPowers(player, player.getMovementHandler()));
        player.getArea().setLocation(7343, FIVE_SIXTY);
        assertEquals("left", collisionPowerups.checkCollisionWithPowers(player, player.getMovementHandler()));

        player.getArea().setLocation(1949, FIVE_SIXTY);
        final int i = glc.getPowersHandler().getPowers();
        collisionPowerups.checkCollisionWithPowers(player, player.getMovementHandler());
        assertEquals(i + 1, glc.getPowersHandler().getPowers());
        assertEquals(ArmourWarrior.class, glc.getPlayer().getClass());
    }

    @Test
    void testCollisionEnemies() {
        final PowerUpController pCon = new PowerUpController(glc, mapHandler1, mapHandler1.getMap());
        final Character player = new NakedWarrior(glc, cmd, mapHandler1, pCon);
        final EnemyImpl first = new EnemyImpl(1836, 576, TRY_TYLE, TRY_TYLE, true, glc.getEnemyHandler(), glc, 0);
        final EnemyImpl second = new EnemyImpl(2664, 576, TRY_TYLE, TRY_TYLE, true, glc.getEnemyHandler(), glc, 0);
        glc.getEnemyHandler().addEnemy(first);
        glc.getEnemyHandler().addEnemy(second);
        final KillDetectionImpl collisionEnemies = new KillDetectionImpl(glc, mapHandler1);

        assertTrue(isBehindTile(3564, 585, mapHandler1));
        assertFalse(isBehindTile(9360, 585, mapHandler1));
        assertEquals(2, glc.getEnemyHandler().getEnemies().size());
        player.getArea().setLocation(1838, 520);
        collisionEnemies.checkCollisionWithEnemeies(player);
        assertTrue(isTouchingUp(player.getArea(), first.getBounds()));
        assertEquals(1, glc.getEnemyHandler().getEnemies().size());

        player.getArea().setLocation(2650, FIVE_SIXTY);
        assertTrue(player.getArea().intersects(glc.getEnemyHandler().getEnemies().getFirst().getBounds()));
        final int i = glc.getPowersHandler().getPowers();
        collisionEnemies.checkCollisionWithEnemeies(player);
        assertEquals(i - 1, glc.getPowersHandler().getPowers());
    }

    @Test
    void testCoinCollision() {
        final CoinController coinController = new CoinControllerImpl();
        final Score score = new Score();
        final ScoreController scoreController = new ScoreControllerImpl(score);
        final Character player = new NakedWarrior(glc, cmd, mapHandler1, null);
        final CoinDetectionImpl collisionCoins = new CoinDetectionImpl(tileSize, coinController, scoreController);

        coinController.addCoins(16, 74);
        assertEquals(coinController.getCoinsCollected(), 0);
        player.getArea().setLocation(2633, FIVE_SIXTY);
        collisionCoins.controlCoinCollision(player);
        assertEquals(coinController.getCoinsCollected(), 1);
    }
}
