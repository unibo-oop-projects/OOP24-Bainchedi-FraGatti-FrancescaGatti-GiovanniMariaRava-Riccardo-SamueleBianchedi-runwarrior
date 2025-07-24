package it.unibo.runwarrior.view.enemy.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.model.enemy.impl.EnemyImpl;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.enemy.api.EnemyView;
/**
 * Implementation of the enemy view with Monkey enemy.
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
     * Constructor of the class MonkeyView.
     * @param glp is the panel in which the monkey need to be renderd
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
        rightMonkey = ImageIO.read(MonkeyView.class.getResourceAsStream("/Monkey/rightMonkey.png"));
        leftMonkey = ImageIO.read(MonkeyView.class.getResourceAsStream("/Monkey/leftMonkey.png"));
        rightMonkeyMoving = ImageIO.read(MonkeyView.class.getResourceAsStream("/Monkey/rightMonkeyMoving.png"));
        leftMonkeyMoving = ImageIO.read(MonkeyView.class.getResourceAsStream("/Monkey/leftMonkeyMoving.png"));
        rightMonkeyRunning = ImageIO.read(MonkeyView.class.getResourceAsStream("/Monkey/rightMonkeyRunning.png"));
        leftMonkeyRunning = ImageIO.read(MonkeyView.class.getResourceAsStream("/Monkey/leftMonkeyRunning.png"));
        banana = ImageIO.read(MonkeyView.class.getResourceAsStream("/Monkey/banana.png"));
        image = rightMonkey;
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public void render(final Graphics g, final EnemyImpl enemy) {
        BufferedImage currentImage;
        if (enemy.getVelocityX() == 0) {
            currentImage = image;
        } else if (enemy.getVelocityX() > 0) {
            currentImage = enemy.isStep() ? rightMonkeyMoving : rightMonkeyRunning;
            image = rightMonkey;
        } else {
            currentImage = enemy.isStep() ? leftMonkeyMoving : leftMonkeyRunning;
            image = leftMonkey;
        }
        final int shift = glp.getMapHandler().getShift();
        g.drawImage(currentImage, enemy.getX() + shift, enemy.getY(), enemy.getWidth(), enemy.getHeight(), null);
    }
}
