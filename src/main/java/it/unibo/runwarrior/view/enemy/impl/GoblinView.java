package it.unibo.runwarrior.view.enemy.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.model.enemy.EnemyImpl;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.enemy.api.EnemyView;
/**
 * Implementation of the enemy view with Goblin enemy
 */
public class GoblinView implements EnemyView {
    private BufferedImage rightGoblin;
    private BufferedImage leftGoblin;
    private BufferedImage rightGoblinMoving;
    private BufferedImage leftGoblinMoving;
    private BufferedImage image;
    private final GameLoopPanel glp;
    /**
     * @param glp is the panel in which the goblin need to be renderd
     * Constructor of the class
     */
    public GoblinView(final GameLoopPanel glp) {
        this.glp = glp;
        try {
            loadResources();
        } catch (IOException e){
            e.printStackTrace();
        } 
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void loadResources() throws IOException {
        rightGoblin = ImageIO.read(getClass().getResourceAsStream("/Goblin/rightGoblin.png"));
        leftGoblin = ImageIO.read(getClass().getResourceAsStream("/Goblin/leftGoblin.png"));
        rightGoblinMoving = ImageIO.read(getClass().getResourceAsStream("/Goblin/rightGoblinMoving.png"));
        leftGoblinMoving = ImageIO.read(getClass().getResourceAsStream("/Goblin/leftGoblinMoving.png"));
        image = rightGoblin;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final Graphics g, final EnemyImpl enemy) {
        BufferedImage currentImage;
        if (enemy.velocityX == 0) {
            currentImage = image;
        } else if (enemy.velocityX > 0) {
            currentImage = enemy.step ? rightGoblinMoving : rightGoblin;
        } else {
            currentImage = enemy.step ? leftGoblinMoving : leftGoblin;
        }
        final int shift = glp.getMapHandler().getShift();  
        g.drawImage(currentImage, enemy.x + shift, enemy.y, enemy.width, enemy.height, null);
    }
}
