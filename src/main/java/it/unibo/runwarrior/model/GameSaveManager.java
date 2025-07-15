package it.unibo.runwarrior.model;

public class GameSaveManager {
    private static final String SAVE_FILE = "game_save.txt";

    private int livelliCompletati;
    private int moneteRaccolte;
    private boolean skinPremiumSbloccata;

    private static GameSaveManager instance;
}
