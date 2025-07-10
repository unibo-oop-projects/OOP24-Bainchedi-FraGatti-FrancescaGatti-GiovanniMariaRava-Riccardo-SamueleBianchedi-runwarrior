package it.unibo.runwarrior.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import it.unibo.runwarrior.model.EnemyImpl;
import it.unibo.runwarrior.model.Goblin;
import it.unibo.runwarrior.model.Guard;
import it.unibo.runwarrior.model.Snake;
import it.unibo.runwarrior.model.Wizard;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.controller.EnemyHandler;

public class EnemySpawner {
    private EnemyHandler handler;
    private GameLoopPanel glp;
    private List<EnemyImpl> enemies;
    public EnemySpawner(EnemyHandler handler, GameLoopPanel glp) {
        this.handler = handler;
        this.glp = glp;
    }
      /*
     * sta classe dovrebbe leggere un file che ha le coordinate di tutti i nemici e che contiene tre numeri perriga:
     * tipo di nemico, coordinata x, coordinata y.
     */
    public void loadEnemiesFromStream(InputStream is) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split(",");
                if (parts.length != 3) continue;

                int type = Integer.parseInt(parts[0]);
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);

                EnemyImpl enemy = createEnemyByType(type, x, y);
                if (enemy != null) {
                    handler.addEnemy(enemy);
                    
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Errore durante il caricamento dei nemici: " + e.getMessage());
        }
    }

    private EnemyImpl createEnemyByType(int type, int x, int y) {
        switch (type) {
            case 1: return new Guard(x, y, 64, 64, true, handler, glp);
            //case 2: return new Snake(x, y, 64, 64, true, handler, glp);
            case 3: return new Wizard(x, y, 64, 64, true, handler, y, y, glp);
            case 4: return new Goblin(x, y, 64, 64, true, handler,y, y, glp);
            default: return null;
        }
    }

}
