package it.unibo.runwarrior.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

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
