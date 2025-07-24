package it.unibo.runwarrior.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GameSaveManagerTest {
    private GameSaveManager gsm;

    @BeforeEach
    public void resetSingleton() {
        gsm = GameSaveManager.getInstance();
        gsm.setLevelsCompleted(2);
        gsm.setCoinCollected(100);
        gsm.setSkinPremiumSbloccata(false);
    }

    /**
     * Check if the pattern singleton is effective and there is only one saving file
     */
    @Test
    public void testSingletonInstance() {
        GameSaveManager anotherInstance = GameSaveManager.getInstance();
        assertSame(gsm, anotherInstance, "Le istanze devono essere le stesse (singleton)");
    }

    /**
     * Test the correct loading of the variables from the file
     */
    @Test
    public void testLoadFroamFile(){
        assertEquals(2, GameSaveManager.getInstance().getLevelsCompleted());
        gsm.setLevelsCompleted(3);
        assertEquals(3, GameSaveManager.getInstance().getLevelsCompleted());
        assertEquals(100, GameSaveManager.getInstance().getCoinCollected());
        gsm.setCoinCollected(115);
        assertEquals(115, GameSaveManager.getInstance().getCoinCollected());
    }

    /**
     * Test if the game is saved in the right way
     */
    @Test 
    public void testSaveGameOnExit(){
    
    }
    @Test
    public void testLoadFromInexistentFile(){
       //assertNull();
    }
}
