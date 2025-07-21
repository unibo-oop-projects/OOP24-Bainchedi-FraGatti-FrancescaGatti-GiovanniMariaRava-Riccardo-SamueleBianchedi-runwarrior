package it.unibo.runwarrior.controller.enemy.api;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
/**
 * Interface EnemyHandler defines method to manage the enemies
 */
public interface EnemyHandler {
    /**
     * Update the enemies frame
     */
    public void update();
    /**
     * Call the view part to render the enemies
     * @param g is graphics used in the rendering part
     */
    public void render(Graphics g);
    /**
     * Updates the state of all enemies and handles their collision with the map.
     * Removes enemies that are not on the screen 
     * @param mapObstacles a list of rectangular objects representing obstacles on the map
     */
    public void updateWithMap(List<Rectangle> mapObstacles);
}
