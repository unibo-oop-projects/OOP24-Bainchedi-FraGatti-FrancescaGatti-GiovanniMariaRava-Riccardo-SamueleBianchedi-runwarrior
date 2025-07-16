package it.unibo.runwarrior.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import it.unibo.runwarrior.model.EnemyImpl;
import it.unibo.runwarrior.model.Goblin;
import it.unibo.runwarrior.model.Guard;
import it.unibo.runwarrior.model.Monkey;
import it.unibo.runwarrior.model.Snake;
import it.unibo.runwarrior.model.Wizard;
import it.unibo.runwarrior.model.EnemySpawnPoints;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.controller.EnemyHandler;

public class EnemySpawner {
    private EnemyHandler handler;
    private GameLoopPanel glp;
    private List<EnemyImpl> enemies;
    private final List<EnemySpawnPoints> spawnPoints;
    private final Set<EnemySpawnPoints> spawnedEnemies;

    public EnemySpawner(EnemyHandler handler, GameLoopPanel glp) {
        this.handler = handler;
        this.glp = glp;
        this.spawnPoints = new ArrayList<>();
        this.spawnedEnemies = new HashSet();
    }
    
    /**
     * @param is
     * This method read the file enemies*.txt in order to fill the List enemies so EnemyHandler can render them
     */
    public void loadEnemiesFromStream(InputStream is) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split(",");
                if (parts.length != 3) {
                    continue;
                }
                int type = Integer.parseInt(parts[0]);
                int tilex = Integer.parseInt(parts[1]);
                int tiley = Integer.parseInt(parts[2]);
                int x=tilex*glp.getMapHandler().getTileSize();
                int y = tiley *glp.getMapHandler().getTileSize();
                spawnPoints.add(new EnemySpawnPoints(type,tilex,tiley));
                /*
                 * Questa parte andrebbe tolta ma devo capire con riky come gestire i nemici quindi rimane qua per ora
                 * EnemyImpl enemy = createEnemyByType(type, x, y);
                 * if (enemy != null) {
                 *     handler.addEnemy(enemy);
                    System.out.println("Caricato nemico: "+i);
                    i++;
                }
                 */ 
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Errore durante il caricamento dei nemici: " + e.getMessage());
        }
    }
    
    /**
     * This methods updates the enemy spawner by checking which enemies should be spawned based on current camera position.
     */
    public void update() {
        int cameraX = glp.getPlayer().getArea().x;
        int screenLeft = cameraX;
        int screenRight = cameraX + GameLoopPanel.WIDTH;
        int tileSize = glp.getMapHandler().getTileSize();
        Iterator<EnemySpawnPoints> iterator = spawnPoints.iterator();
        while (iterator.hasNext()) {
            EnemySpawnPoints spawnPoint = iterator.next();
            int enemyX = spawnPoint.x()*tileSize;
            if (enemyX >= screenLeft && enemyX <= screenRight && !spawnedEnemies.contains(spawnPoint)) {
                System.out.println("Spawning enemy of type " + spawnPoint.type() + " at (" + enemyX + ", " + spawnPoint.y()*tileSize + ")");
                EnemyImpl enemy = createEnemyByType(spawnPoint.type(), enemyX, spawnPoint.y()*tileSize);
                System.out.println("Enemy created: " + enemy);
                if (enemy != null) {
                    handler.addEnemy(enemy);
                    spawnedEnemies.add(spawnPoint);
                }
                iterator.remove();
            }
        }
    }

    /**
     * @param type
     * @param x
     * @param y
     * @return a new Enemy Object depending on the number passed
     */
    private EnemyImpl createEnemyByType(int type, int x, int y) {
        switch (type) {
            case 1: return new Guard(x, y, 64, 64, true, handler, glp);
            case 2: return new Snake(x, y, 64, 64, true, handler, glp);
            case 3: return new Wizard(x, y, 64, 64, true, handler,glp);
            case 4: return new Goblin(x, y, 64, 64, true, handler,glp);
            case 5: return new Monkey(x, y, 64, 64, true, handler, glp);
            default: return null;
        }
    }

}
