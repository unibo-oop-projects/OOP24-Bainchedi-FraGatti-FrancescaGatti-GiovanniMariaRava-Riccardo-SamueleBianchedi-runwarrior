package it.unibo.runwarrior.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        gsm.setLevelsCompleted(0);
        gsm.setCoinCollected(0);
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

    }

    /**
     * Test if the game is saved in the right way
     */
    @Test 
    public void testSaveGame(){
        
    }
}
