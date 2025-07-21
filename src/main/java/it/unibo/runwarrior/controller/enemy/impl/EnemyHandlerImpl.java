package it.unibo.runwarrior.controller.enemy.impl;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.*;

import it.unibo.runwarrior.controller.enemy.api.EnemyHandler;
import it.unibo.runwarrior.model.enemy.impl.EnemyImpl;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.enemy.impl.EnemyViewFactoryImpl;
/**
 * Implementes EnemyHandler interfaces to manage properly the enemies
 */
public class EnemyHandlerImpl implements EnemyHandler {
    private LinkedList<EnemyImpl> enemies;
    private EnemyViewFactoryImpl viewFactory;
    private final GameLoopPanel glp;
    /**
     * @param glp is the panel in which the enemy will be rendered
     * @param viewFactory contains the map<type,EnemyView>
     * Constructor of the class EnemyHandlerImpl
     */
    public EnemyHandlerImpl (final GameLoopPanel glp, final EnemyViewFactoryImpl viewFactory) {
        this.viewFactory = viewFactory;
        this.glp = glp;
        this.enemies = new LinkedList<>();
    }
    /**
     * {@inheritDoc}
     */
    public void render(final Graphics g) {
        for (EnemyImpl enemy : enemies) {
            viewFactory.get(enemy.getType()).render(g, enemy);
        }
    }
    /**
     * {@inheritDoc}
     */
    public void update() {
        Iterator<EnemyImpl> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            EnemyImpl enemy = iterator.next();
            enemy.update();
        }
    }
    /**
     * @param en the enemies to be added
     * Adds the enemy to the list
     */
    public void addEnemy(EnemyImpl en) {
        enemies.add(en);
    }
    /**
     * @param en the enemy to be removed
     * Removed the enemy from the list
     */
    public void removeEnemy(EnemyImpl en) {
        enemies.remove(en);
    }
    /**
     * {@inheritDoc}
     */
    public void updateWithMap(final List<Rectangle> mapObstacles) {
        Iterator<EnemyImpl> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            EnemyImpl enemy = iterator.next();
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
     * @param enemy is the enemy that is being checked
     * @return whether the enemy is visible on the screen or too far on the left
     */
    private boolean isEnemyInView(final EnemyImpl enemy) {
        int cameraX = glp.getPlayer().getArea().x;
        int enemyX = enemy.getX();
        int enemyWidth = enemy.getWidth();
        return enemyX + enemyWidth >= cameraX  && enemyX <= cameraX + GameLoopPanel.WIDTH;
    }
    /**
     * @return the list of the enemies
     */
    public LinkedList<EnemyImpl> getEnemies() {
        return enemies;
    }
}
