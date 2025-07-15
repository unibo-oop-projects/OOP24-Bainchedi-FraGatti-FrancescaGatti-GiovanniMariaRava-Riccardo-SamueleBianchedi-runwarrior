package it.unibo.runwarrior.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GameSaveManager {
    private static final String SAVE_FILE = "game_save.txt";

    private int livelliCompletati;
    private int moneteRaccolte;
    private boolean skinPremiumSbloccata;

    private static GameSaveManager instance;

    private GameSaveManager() {
        livelliCompletati = 0;
        moneteRaccolte = 0;
        skinPremiumSbloccata = false;
    }

    private void saveGame() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))) {
            writer.write(Integer.toString(livelliCompletati));
            writer.newLine();
            writer.write(Integer.toString(moneteRaccolte));
            writer.newLine();
            writer.write(Boolean.toString(skinPremiumSbloccata));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
