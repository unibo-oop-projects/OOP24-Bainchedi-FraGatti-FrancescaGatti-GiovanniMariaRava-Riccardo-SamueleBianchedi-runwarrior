package it.unibo.runwarrior.controller.enemy.api;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
/**
 * Interface EnemyHandler defines method to manage the enemies
 */
public interface EnemyHandler {
    /**
     * update the enemies frame
     */
    public void update();
    /**
     * @param g is graphics used in the rendering part
     * call the view part to render the enemies
     */
    public void render(Graphics g);
    /**
     * @param mapObstacles give the position of the obstacles
     * update the enemies checking if the enemy is in the view and if it is not colliding
     */
    public void updateWithMap(List<Rectangle> mapObstacles);
}
