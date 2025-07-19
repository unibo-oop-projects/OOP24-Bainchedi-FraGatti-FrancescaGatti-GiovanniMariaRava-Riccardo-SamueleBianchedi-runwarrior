package it.unibo.runwarrior.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GameSaveManager {
    private static final String SAVE_FILE = "game_save.txt";

    private int levelsCompleted;
    private int coinCollected;
    private boolean premiumSkinUnlocked;

    private static GameSaveManager instance;

    private GameSaveManager() {
        levelsCompleted = 0;
        coinCollected = 0;
        premiumSkinUnlocked = false;
    }

    private void saveGame() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))) {
            writer.write(Integer.toString(levelsCompleted));
            writer.newLine();
            writer.write(Integer.toString(coinCollected));
            writer.newLine();
            writer.write(Boolean.toString(premiumSkinUnlocked));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static GameSaveManager loadFromFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        GameSaveManager gsm = new GameSaveManager();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            
            if ((line = reader.readLine()) != null) {
                gsm.levelsCompleted = Integer.parseInt(line.trim());
            }
            if ((line = reader.readLine()) != null){
                gsm.coinCollected = Integer.parseInt(line.trim());
            }
            if ((line = reader.readLine()) != null){
                gsm.premiumSkinUnlocked = Boolean.parseBoolean(line.trim());
            }

            return gsm;
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static GameSaveManager getInstance() {
        if (instance == null) {
            instance = loadFromFile(SAVE_FILE);
            if (instance == null) {
                instance = new GameSaveManager();
                instance.saveGame();
            }
        }
        return instance;
    }
    
    public int getLevelsCompleted() {
        return levelsCompleted;
    }

    public void setLevelsCompleted(int levelsCompleted) {
        this.levelsCompleted = levelsCompleted;
        saveGame();
    }

    public int getCoinCollected() {
        return coinCollected;
    }

    public void setCoinCollected(int coinCollected) {
        this.coinCollected = coinCollected;
        saveGame();
    }

    public boolean isSkinPremiumSbloccata() {
        return premiumSkinUnlocked;
    }

    public void setSkinPremiumSbloccata(boolean sbloccata) {
        this.premiumSkinUnlocked = sbloccata;
        saveGame();
    }

    public void addCoin(int quantity) {
        this.coinCollected += quantity;
        saveGame();
    }

    public void livelloCompletato(int level) {
        if (level > levelsCompleted) {
            this.levelsCompleted = level;
            saveGame();
        }
    }

    public void onGameExit() {
        saveGame();
    }

}
