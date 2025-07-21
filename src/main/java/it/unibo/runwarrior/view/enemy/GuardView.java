package it.unibo.runwarrior.view.enemy;

import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import it.unibo.runwarrior.model.enemy.EnemyImpl;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.enemy.api.EnemyView;

/**
 * Implementation of the enemy view with Guard enemy
 */
public class GuardView implements EnemyView {
    private BufferedImage rightGuard;
    private BufferedImage leftGuard;
    private BufferedImage rightGuardMoving;
    private BufferedImage leftGuardMoving;
    private BufferedImage rightGuardRunning;
    private BufferedImage leftGuardRunning;
    private BufferedImage image;
    private final GameLoopPanel glp;
    /**
     * @param glp is the panel in which the guard need to be renderd
     * Constructor of the GuardView class
     */
    public GuardView(final GameLoopPanel glp) {
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
        rightGuard = ImageIO.read(getClass().getResourceAsStream("/Guardia/rightGuard.png"));
        leftGuard = ImageIO.read(getClass().getResourceAsStream("/Guardia/leftGuard.png"));
        rightGuardMoving = ImageIO.read(getClass().getResourceAsStream("/Guardia/rightGuardMoving.png"));
        leftGuardMoving = ImageIO.read(getClass().getResourceAsStream("/Guardia/leftGuardMoving.png"));
        rightGuardRunning = ImageIO.read(getClass().getResourceAsStream("/Guardia/rightRunningGuard.png"));
        leftGuardRunning = ImageIO.read(getClass().getResourceAsStream("/Guardia/leftRunningGuard.png"));
        image = rightGuard;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void render(final Graphics g, final EnemyImpl enemy) {
        BufferedImage currentImage;
        if (enemy.velocityX == 0) {
            currentImage = image;
        } else if (enemy.velocityX > 0) {
            currentImage = enemy.step ? rightGuardMoving : rightGuardRunning;
            image = rightGuard;
        } else {
            currentImage = enemy.step ? leftGuardMoving : leftGuardRunning;
            image = leftGuard;
        }
        int shift = glp.getMapHandler().getShift();
        //System.out.println("SNAKE XS: " + (x+shift)+ "X:"+ x);
        g.drawImage(currentImage, enemy.x + shift, enemy.y, enemy.width, enemy.height, null);
    }
}
