package it.unibo.runwarrior.model.enemy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;

import it.unibo.runwarrior.controller.EnemyHandler;
import it.unibo.runwarrior.model.enemy.impl.EnemyImpl;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.enemy.EnemyViewFactory;

public class EnemyImplTest {
    private EnemyImpl enemy;
    private GameLoopPanel glp;
    private EnemyHandler enemyHandler;
    private EnemyViewFactory enemyViewFactory;
    
    /**
     * Sets up a new EnemyImpl instance.
     */
    @BeforeEach
    public void setUp() {
        glp = new GameLoopPanel();
        enemyHandler = new EnemyHandler(glp, enemyViewFactory);
        enemy = new EnemyImpl(50, 100, 64, 64, true, enemyHandler, glp, 1); 
    }

    /**
     * Verifies that getBounds() returns the correct rectangle based on position and size.
     */
    @Test
    public void testGetBounds() {
        Rectangle bounds = enemy.getBounds();
        assertEquals(50, bounds.x);
        assertEquals(100, bounds.y);
        assertEquals(64, bounds.width);
        assertEquals(64, bounds.height);
    }

    /**
     * Ensures that checkMapCollision inverts velocit
     */
    @Test
    public void testCheckMapCollisionBlocksMovement() {
        Rectangle obstacle = new Rectangle(60, 100, 64, 64);
        enemy.setVelocityX(2);
        //enemy.setVelocityY(0); mi serve quando implemento la caduta dei nemici 
        enemy.checkMapCollision(List.of(obstacle));
        assertEquals(-2, enemy.getVelocityX());
        /*
         * sto cercando di fare un assert per controllare che non si infili nel blocco dal lato sinistro del blooc
         * questo non è definitivo per ora
         * assertTrue(enemy.getX() + enemy.getWidth() <= block.x);
         */
        
    }

    /**
     * questo metodo devo ancora capire se è corretto averlo qui o dentro enemyhandlertest (del quale manca ancora implementazione)
     */
    @Test 
    public void testDie(){
        enemyHandler.addEnemy(enemy);
        assertEquals(1, enemyHandler.getEnemies().size());
        enemy.die();
        assertEquals(0, enemyHandler.getEnemies().size());
    }
}

