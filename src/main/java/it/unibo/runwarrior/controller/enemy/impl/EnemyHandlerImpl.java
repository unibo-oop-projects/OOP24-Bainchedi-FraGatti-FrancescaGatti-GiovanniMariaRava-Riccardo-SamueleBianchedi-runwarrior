package it.unibo.runwarrior.controller.enemy.impl;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import it.unibo.runwarrior.controller.GameLoopController;
import it.unibo.runwarrior.controller.enemy.api.EnemyHandler;
import it.unibo.runwarrior.model.enemy.impl.EnemyImpl;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.enemy.impl.EnemyViewFactoryImpl;

/**
 * Implementes EnemyHandler interfaces to manage properly the enemies.
 */
public class EnemyHandlerImpl implements EnemyHandler {
    private final List<EnemyImpl> enemies;
    private final EnemyViewFactoryImpl viewFactory;
    private final GameLoopController glp;

    /**
     * Constructor of the class EnemyHandlerImpl.
     * 
     * @param glp is the panel in which the enemy will be rendered
     * @param viewFactory contains the map with enemy type and 
     */
    public EnemyHandlerImpl(final GameLoopController glp, final EnemyViewFactoryImpl viewFactory) {
        this.viewFactory = viewFactory;
        this.glp = glp;
        this.enemies = new LinkedList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final Graphics g) {
        for (final EnemyImpl enemy : enemies) {
            viewFactory.get(enemy.getType()).render(g, enemy);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        final Iterator<EnemyImpl> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            final EnemyImpl enemy = iterator.next();
            enemy.update();
        }
    }

    /**
     * Adds the enemy to the list.
     * 
     * @param en the enemies to be added
     */
    public void addEnemy(final EnemyImpl en) {
        enemies.add(en);
    }

    /**
     * Removed the enemy from the list.
     * 
     * @param en the enemy to be removed
     */
    public void removeEnemy(final EnemyImpl en) {
        enemies.remove(en);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateWithMap(final List<Rectangle> mapObstacles) {
        final Iterator<EnemyImpl> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            final EnemyImpl enemy = iterator.next();
            if (enemy.getX() + enemy.getWidth() < glp.getPlayer().getArea().x - GameLoopPanel.WIDTH) {
                iterator.remove();
                continue;
            }
            enemy.update();
            if (isEnemyInView(enemy)) {
                enemy.checkMapCollision(mapObstacles);
            }
        }
    }
    
    /**
     * Checks if the enemyes in the visible screen part.
     * 
     * @param enemy is the enemy that is being checked
     * @return whether the enemy is visible on the screen or too far on the left
     */
    private boolean isEnemyInView(final EnemyImpl enemy) {
        final int cameraX = glp.getPlayer().getArea().x;
        final int enemyX = enemy.getX();
        final int enemyWidth = enemy.getWidth();
        return enemyX + enemyWidth >= cameraX && enemyX <= cameraX + GameLoopPanel.WIDTH;
    }

    /**
     * @return the list of the enemies
     */
    public List<EnemyImpl> getEnemies() {
        return enemies;
    }
}
