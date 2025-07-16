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
        gsm.setLivelliCompletati(0);
        gsm.setMoneteRaccolte(0);
        gsm.setSkinPremiumSbloccata(false);
    }

    @Test
    public void testSingletonInstance() {
        GameSaveManager anotherInstance = GameSaveManager.getInstance();
        assertSame(gsm, anotherInstance, "Le istanze devono essere le stesse (singleton)");
    }


}
