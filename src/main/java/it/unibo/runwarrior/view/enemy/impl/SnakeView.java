package it.unibo.runwarrior.view.enemy.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.model.enemy.impl.EnemyImpl;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.enemy.api.EnemyView;

/**
 * Implementation of the enemy view with Snake enemy.
 */

public class SnakeView implements EnemyView {
    private BufferedImage rightSnake;
    private BufferedImage rightSnakeMoving;
    private BufferedImage leftSnake;
    private BufferedImage leftSnakeMoving;
    private BufferedImage poisonImage;
    private final GameLoopPanel glp;
    /**
     * Constructor of the SnakeView class.
     * @param glp is the panel in which the guard need to be rendered
     */
    public SnakeView(final GameLoopPanel glp) {
        this.glp = glp;
        try {
            loadResources();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public void loadResources() throws IOException {
        rightSnake = ImageIO.read(getClass().getResourceAsStream("/Snake/rightSnake.png"));
        rightSnakeMoving = ImageIO.read(getClass().getResourceAsStream("/Snake/rightSnakeMoving.png"));
        leftSnake = ImageIO.read(getClass().getResourceAsStream("/Snake/leftSnake.png"));
        leftSnakeMoving = ImageIO.read(getClass().getResourceAsStream("/Snake/leftSnakeMoving.png"));
        poisonImage = ImageIO.read(getClass().getResourceAsStream("/Snake/poison.png"));
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public void render(final Graphics g, final EnemyImpl enemy) {
        BufferedImage currentImage;
        if (enemy.velocityX > 0) {
            currentImage = enemy.step ? rightSnakeMoving : rightSnake;
        } else {
            currentImage = enemy.step ? leftSnakeMoving : leftSnake;
        }
        final int shift = glp.getMapHandler().getShift();
        g.drawImage(currentImage, enemy.x + shift, enemy.y, enemy.width, enemy.height, null);
    }
}
