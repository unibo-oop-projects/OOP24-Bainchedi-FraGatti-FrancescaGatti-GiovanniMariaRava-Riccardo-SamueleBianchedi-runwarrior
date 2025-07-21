package it.unibo.runwarrior.view.enemy.api;

import java.io.IOException;
import java.awt.Graphics;
import it.unibo.runwarrior.model.enemy.EnemyImpl;

/**
 * The EnemyView interface defines the method that are used to visualize the enemy in the map
 */
public interface EnemyView {
    /**
     * @throws IOException when the image is not found
     * Load all the images of the enemy
     */
    
    public void loadResources() throws IOException;
    /**
     * @param g is the graphics that method use to render
     * @param enemy is the one rendered
     * Render the image of the enemy
     */

    public void render(Graphics g, EnemyImpl enemy);
} 
