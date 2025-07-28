package it.unibo.runwarrior.controller.enemy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.runwarrior.controller.GameLoopController;
import it.unibo.runwarrior.controller.enemy.impl.EnemyHandlerImpl;
import it.unibo.runwarrior.model.enemy.api.EnemySpawnPoints;
import it.unibo.runwarrior.model.enemy.impl.EnemyImpl;
import it.unibo.runwarrior.view.GameLoopPanel;

/**
 * EnemySpawner reads the file with enemies positions and fill the handler.enemies list.
 */
public class EnemySpawner {
    private static final int TO_TOUCH_FLOOR = 8;
    private static final Logger LOGGER = Logger.getLogger(EnemySpawner.class.getName());
    private final EnemyHandlerImpl handler;
    private final GameLoopController glp;
    private final List<EnemySpawnPoints> spawnPoints;
    private final Set<EnemySpawnPoints> spawnedEnemies;

    /**
     * Constructor of the class EnemySpawner.
     *
     * @param handler where the enemies list is stored
     * @param glp the panel in which enemies are shown
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public EnemySpawner(final EnemyHandlerImpl handler, final GameLoopController glp) {
        this.handler = handler;
        this.glp = glp;
        this.spawnPoints = new ArrayList<>();
        this.spawnedEnemies = new HashSet<>();
    }

    /**
     * This method read the file enemies*.txt in order to fill the List enemies so EnemyHandler can render them.
     *
     * @param is lo stream di input da cui leggere le righe nel formato type , tileX, tileY.
     */
    public void loadEnemiesFromStream(final InputStream is) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            br.lines().map(String :: trim)
                .filter(s -> !s.isEmpty())
                .map(s -> s.split(",", -1))
                .filter(part -> part.length == 3)
                .map(part -> new EnemySpawnPoints(
                                                    Integer.parseInt(part[0]), 
                                                    Integer.parseInt(part[1]), 
                                                    Integer.parseInt(part[2])))
                .forEach(spawnPoints :: add);
        } catch (IOException | NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il caricamento dei nemici: " + e.getMessage());
        }
    }

    /**
     * This methods updates the enemy spawner by checking which enemies should be spawned based on current camera position.
     */
    public void update() {
        final int cameraX = glp.getPlayer().getArea().x;
        final int screenLeft = cameraX;
        final int screenRight = cameraX + GameLoopPanel.WIDTH;
        final int tileSize = glp.getMapHandler().getTileSize();
        final Iterator<EnemySpawnPoints> iterator = spawnPoints.iterator();
        while (iterator.hasNext()) {
            final EnemySpawnPoints spawnPoint = iterator.next();
            final int enemyX = spawnPoint.x() * tileSize;
            if (enemyX >= screenLeft && enemyX <= screenRight && !spawnedEnemies.contains(spawnPoint)) {
                final EnemyImpl enemy = new EnemyImpl(enemyX, (spawnPoint.y() * tileSize) + TO_TOUCH_FLOOR, 64, 64, 
                                            true, handler, glp, spawnPoint.type());
                handler.addEnemy(enemy);
                spawnedEnemies.add(spawnPoint);
                iterator.remove();
            }
        }
    }
}
