package it.unibo.runwarrior.controller.enemy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import it.unibo.runwarrior.controller.GameLoopController;
import it.unibo.runwarrior.controller.enemy.impl.EnemyHandlerImpl;
import it.unibo.runwarrior.model.enemy.api.EnemySpawnPoints;
import it.unibo.runwarrior.model.enemy.impl.EnemyImpl;
import it.unibo.runwarrior.view.GameLoopPanel;
/**
 * EnemySpawner is the class that reads the file with enemies positions and fill the handler.enemies list
 */
public class EnemySpawner {
    private final static int TO_TOUCH_FLOOR = 8;
    private EnemyHandlerImpl handler;
    private final GameLoopController glp;
    private final List<EnemySpawnPoints> spawnPoints;
    private final Set<EnemySpawnPoints> spawnedEnemies;

    /**
     * Constructor of the class EnemySpawner
     * @param handler where the enemies list is stored
     * @param glp the panel in which enemies are shown
     */
    public EnemySpawner(final EnemyHandlerImpl handler, final GameLoopController glp) {
        this.handler = handler;
        this.glp = glp;
        this.spawnPoints = new ArrayList<>();
        this.spawnedEnemies = new HashSet();
    }
    
    /**
     * @param is
     * This method read the file enemies*.txt in order to fill the List enemies so EnemyHandler can render them
     */
    public void loadEnemiesFromStream(final InputStream is) {
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
                spawnPoints.add(new EnemySpawnPoints(type,tilex,tiley)); 
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
                EnemyImpl enemy = new EnemyImpl(enemyX, (spawnPoint.y()*tileSize) + TO_TOUCH_FLOOR, 64,64, true, handler, glp, spawnPoint.type());
                if (enemy != null) {
                    handler.addEnemy(enemy);
                    spawnedEnemies.add(spawnPoint);
                }
                iterator.remove();
            }
        }
    }
}
