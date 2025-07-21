package it.unibo.runwarrior.view.enemy.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.model.enemy.impl.EnemyImpl;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.enemy.api.EnemyView;
/**
 * Implementation of the enemy view with Monkey enemy
 */
public class MonkeyView implements EnemyView {
    private BufferedImage rightMonkey;
    private BufferedImage leftMonkey;
    private BufferedImage rightMonkeyMoving;
    private BufferedImage leftMonkeyMoving;
    private BufferedImage rightMonkeyRunning;
    private BufferedImage leftMonkeyRunning;
    private BufferedImage banana;
    private BufferedImage image;
    private final GameLoopPanel glp;
    /**
     * @param glp is the panel in which the monkey need to be renderd
     * Constructor of the class MonkeyView
     */
    public MonkeyView(final GameLoopPanel glp) {
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
        rightMonkey = ImageIO.read(getClass().getResourceAsStream("/Monkey/rightMonkey.png"));
        leftMonkey = ImageIO.read(getClass().getResourceAsStream("/Monkey/leftMonkey.png"));
        rightMonkeyMoving = ImageIO.read(getClass().getResourceAsStream("/Monkey/rightMonkeyMoving.png"));
        leftMonkeyMoving = ImageIO.read(getClass().getResourceAsStream("/Monkey/leftMonkeyMoving.png"));
        rightMonkeyRunning = ImageIO.read(getClass().getResourceAsStream("/Monkey/rightMonkeyRunning.png"));
        leftMonkeyRunning = ImageIO.read(getClass().getResourceAsStream("/Monkey/leftMonkeyRunning.png"));
        banana = ImageIO.read(getClass().getResourceAsStream("/Monkey/banana.png"));
        image = rightMonkey;
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
            currentImage = enemy.step ? rightMonkeyMoving : rightMonkeyRunning;
            image = rightMonkey;
        } else {
            currentImage = enemy.step ? leftMonkeyMoving : leftMonkeyRunning;
            image = leftMonkey;
        }
        final int shift = glp.getMapHandler().getShift();
        g.drawImage(currentImage, enemy.x+shift, enemy.y, enemy.width, enemy.height, null);
    }   
}
