package it.unibo.runwarrior.view.enemy.api;

import java.io.IOException;

import it.unibo.runwarrior.model.enemy.impl.EnemyImpl;

import java.awt.Graphics;

/**
 * The EnemyView interface defines the method that are used to visualize the enemy in the map.
 */

public interface EnemyView {
    /**
     * Load all the images of the enemy.
     * 
     * @throws IOException when the image is not found
     */   
    void loadResources() throws IOException;

    /**
     * Render the image of the enemy.
     * 
     * @param g is the graphics that method use to render
     * @param enemy is the one rendered
     */
    void render(Graphics g, EnemyImpl enemy);
} 
