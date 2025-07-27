package it.unibo.runwarrior.view.enemy.impl;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import it.unibo.runwarrior.controller.GameLoopController;
import it.unibo.runwarrior.model.enemy.impl.EnemyImpl;
import it.unibo.runwarrior.view.enemy.api.EnemyView;

/**
 * Implementation of the enemy view with Guard enemy.
 */
public class GuardView implements EnemyView {
    private BufferedImage rightGuard;
    private BufferedImage leftGuard;
    private BufferedImage rightGuardMoving;
    private BufferedImage leftGuardMoving;
    private BufferedImage rightGuardRunning;
    private BufferedImage leftGuardRunning;
    private BufferedImage image;
    private final GameLoopController glp;
    private static final Logger LOGGER = Logger.getLogger(GuardView.class.getName());
    
    /**
     * Constructor of the GuardView class.
     * 
     * @param glp is the panel in which the guard need to be renderd.
     */
    public GuardView(final GameLoopController glp) {
        this.glp = glp;
        try {
            loadResources();
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il caricamento delle immagini di Guard");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadResources() throws IOException {
        rightGuard = ImageIO.read(GuardView.class.getResourceAsStream("/Guardia/rightGuard.png"));
        leftGuard = ImageIO.read(GuardView.class.getResourceAsStream("/Guardia/leftGuard.png"));
        rightGuardMoving = ImageIO.read(GuardView.class.getResourceAsStream("/Guardia/rightGuardMoving.png"));
        leftGuardMoving = ImageIO.read(GuardView.class.getResourceAsStream("/Guardia/leftGuardMoving.png"));
        rightGuardRunning = ImageIO.read(GuardView.class.getResourceAsStream("/Guardia/rightRunningGuard.png"));
        leftGuardRunning = ImageIO.read(GuardView.class.getResourceAsStream("/Guardia/leftRunningGuard.png"));
        image = rightGuard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void render(final Graphics g, final EnemyImpl enemy) {
        final BufferedImage currentImage;
        if (enemy.getVelocityX()== 0) {
            currentImage = image;
        } else if (enemy.getVelocityX() > 0) {
            currentImage = enemy.isStep() ? rightGuardMoving : rightGuardRunning;
            image = rightGuard;
        } else {
            currentImage = enemy.isStep() ? leftGuardMoving : leftGuardRunning;
            image = leftGuard;
        }
        final int shift = glp.getMapHandler().getShift();
        g.drawImage(currentImage, enemy.getX() + shift, enemy.getY(), enemy.getWidth(), enemy.getHeight(), null);
    }
}
