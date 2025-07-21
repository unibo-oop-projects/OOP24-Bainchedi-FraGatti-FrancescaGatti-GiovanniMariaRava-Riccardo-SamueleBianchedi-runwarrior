package it.unibo.runwarrior.view.enemy;

import java.io.IOException;
import java.awt.Graphics;
import it.unibo.runwarrior.model.enemy.EnemyImpl;

public interface EnemyView {

    /**
     * @throws IOException
     * Load all the images of the enemy, throws IOException when the image is not found
     */
    public void loadResources() throws IOException;
    /**
     * @param g
     * @param enemy
     * Render the image of the enemy
     */
    public void render(Graphics g, EnemyImpl enemy);
} 
