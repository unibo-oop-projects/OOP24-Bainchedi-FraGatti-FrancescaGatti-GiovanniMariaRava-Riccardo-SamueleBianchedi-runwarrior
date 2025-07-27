package it.unibo.runwarrior.view.enemy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.runwarrior.controller.GameLoopController;
import it.unibo.runwarrior.view.enemy.api.EnemyView;

/**
 * Test if the map created is correct.
 */
public class TestEnemyViewFactory {
    private static final int WRONG_TYPE = 99;
    private EnemyViewFactoryImpl enemyViewFactoryImpl;
    private GameLoopController glc;
    private EnemyView guardView;
    private EnemyView snakeView;
    private EnemyView wizardView;

    /**
     * Set up a new enemyViewFactoryImp and three views.
     */
    @BeforeEach
    private void setUp() {
        enemyViewFactoryImpl = new EnemyViewFactoryImpl();
        guardView = new GuardView(glc);
        snakeView = new SnakeView(glc);
        wizardView = new WizardView(glc);
    }

    /**
     * Checks if register return null if wrong number is passed.
     */
    @Test
    public void testRegisterAndGet() {
        enemyViewFactoryImpl.register(1, guardView);
        assertEquals(guardView, enemyViewFactoryImpl.get(1));
        assertNull(enemyViewFactoryImpl.get(WRONG_TYPE));
    }

    /**
     * Testing if the view change if registred in different way.
     */
    @Test
    public void modifiedRegistration() {
        enemyViewFactoryImpl.register(2, guardView);
        assertEquals(guardView, enemyViewFactoryImpl.get(2));

        enemyViewFactoryImpl.register(2, snakeView);
        assertEquals(snakeView, enemyViewFactoryImpl.get(2));
    }

    /**
     * Checks if mulitple registration are executed correctly.
     */
    @Test
    public void moreRegistration() {
        enemyViewFactoryImpl.register(1, guardView);
        enemyViewFactoryImpl.register(2, snakeView);
        enemyViewFactoryImpl.register(3, wizardView);
        
        assertEquals(guardView, enemyViewFactoryImpl.get(1));
        assertEquals(snakeView, enemyViewFactoryImpl.get(2));
        assertEquals(wizardView, enemyViewFactoryImpl.get(3));
        assertNull(enemyViewFactoryImpl.get(4));
    }
}
