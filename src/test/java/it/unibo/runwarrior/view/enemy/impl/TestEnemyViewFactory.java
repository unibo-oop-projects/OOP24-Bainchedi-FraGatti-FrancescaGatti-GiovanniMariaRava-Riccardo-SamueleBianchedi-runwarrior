package it.unibo.runwarrior.view.enemy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.runwarrior.controller.GameLoopController;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.enemy.api.EnemyView;

public class TestEnemyViewFactory {
    private EnemyViewFactoryImpl enemyViewFactoryImpl;
    private GameLoopController glc;
    private EnemyView guardView;
    private EnemyView snakeView;
    private EnemyView wizardView;
    @BeforeEach
    private void setUp() {
        enemyViewFactoryImpl = new EnemyViewFactoryImpl();
        guardView = new GuardView(glc);
        snakeView = new SnakeView(glc);
        wizardView = new WizardView(glc);
    }

    @Test
    public void testRegisterAndGet() {
        enemyViewFactoryImpl.register(1, guardView);
        assertEquals(guardView, enemyViewFactoryImpl.get(1));
        assertNull(enemyViewFactoryImpl.get(99));
    }

    @Test
    public void ModifiedRegistration () {
        enemyViewFactoryImpl.register(2, guardView);
        assertEquals(guardView, enemyViewFactoryImpl.get(2));
        
        enemyViewFactoryImpl.register(2, snakeView);
        assertEquals(snakeView, enemyViewFactoryImpl.get(2));
    }

    @Test
    public void MoreRegistration() {
        enemyViewFactoryImpl.register(1, guardView);
        enemyViewFactoryImpl.register(2, snakeView);
        enemyViewFactoryImpl.register(3, wizardView);
        
        assertEquals(guardView, enemyViewFactoryImpl.get(1));
        assertEquals(snakeView, enemyViewFactoryImpl.get(2));
        assertEquals(wizardView, enemyViewFactoryImpl.get(3));
        assertNull(enemyViewFactoryImpl.get(4));
    }
    


}