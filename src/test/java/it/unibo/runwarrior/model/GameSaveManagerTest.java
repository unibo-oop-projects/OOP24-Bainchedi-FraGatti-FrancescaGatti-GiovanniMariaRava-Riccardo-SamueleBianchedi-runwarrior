package it.unibo.runwarrior.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameSaveManagerTest {
    private GameSaveManager gsm;

    /**
     * Create a test instance of the game with random variables
     */
    @BeforeEach
    public void resetSingleton() {
        gsm = GameSaveManager.getInstance();
        gsm.setLevelsCompleted(2);
        gsm.setCoinCollected(100);
        gsm.setSkinPremiumSbloccata(false);
    }

    /**
     * Check if the pattern singleton is effective and there is only one saving file.
     */
    @Test
    public void testSingletonInstance() {
        GameSaveManager anotherInstance = GameSaveManager.getInstance();
        assertSame(gsm, anotherInstance, "Le istanze devono essere le stesse (singleton)");
    }

    /**
     * Test both the saving in the file and the loading. Every time a setter method is called
     * is called also saveGame().
     */
    @Test
    public void testSaveAndLoadFromFile(){
        assertEquals(2, GameSaveManager.getInstance().getLevelsCompleted());
        gsm.setLevelsCompleted(3);
        assertEquals(3, GameSaveManager.getInstance().getLevelsCompleted());
        assertEquals(100, GameSaveManager.getInstance().getCoinCollected());
        gsm.setCoinCollected(115);
        assertEquals(115, GameSaveManager.getInstance().getCoinCollected());
        gsm.setSkinPremiumSbloccata(true);
        assertEquals(true, GameSaveManager.getInstance().isSkinPremiumSbloccata());
    }
    
    /**
     * Checks that the right exception is thrown
     */
    @Test
    public void testWrongLevelsSaving(){
        assertThrows(IllegalArgumentException.class, () -> gsm.setLevelsCompleted(5));
    }
}
