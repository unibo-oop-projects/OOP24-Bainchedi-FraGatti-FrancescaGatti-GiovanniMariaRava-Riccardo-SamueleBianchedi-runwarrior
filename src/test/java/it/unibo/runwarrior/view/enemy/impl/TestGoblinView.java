package it.unibo.runwarrior.view.enemy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.swing.JFrame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.runwarrior.controller.GameLoopController;

public class TestGoblinView {
    private GoblinView goblinView;
    private GameLoopController glc;
    private JFrame mainFrame;
    @BeforeEach
    public void setUp(){
        glc = new GameLoopController(mainFrame, "tryMap.txt", "Map2/forest_theme.txt", 
                            "/Map2/enemiesMap2.txt", "/Coins/CoinCoordinates_map2.txt");
        goblinView = new GoblinView(glc);
    }

    @Test
    public void testLoadResources(){
        assertNotNull(goblinView.getRightGoblin());
        assertNotNull(goblinView.getLeftGoblin());
        assertNotNull(goblinView.getRightGoblinMoving());
        assertNotNull(goblinView.getLeftGoblinMoving());
        assertEquals(36, goblinView.getRightGoblin().getWidth());;
    }
}
