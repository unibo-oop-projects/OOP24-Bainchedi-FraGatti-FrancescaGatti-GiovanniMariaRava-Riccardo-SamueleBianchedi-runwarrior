package it.unibo.runwarrior.view.enemy.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.runwarrior.controller.GameLoopController;
import it.unibo.runwarrior.model.enemy.impl.EnemyImpl;
import it.unibo.runwarrior.view.enemy.api.EnemyView;

/**
 * Implementation of the enemy view with Snake enemy.
 */

public final class SnakeView implements EnemyView {
    private static final Logger LOGGER = Logger.getLogger(SnakeView.class.getName());
    private BufferedImage rightSnake;
    private BufferedImage rightSnakeMoving;
    private BufferedImage leftSnake;
    private BufferedImage leftSnakeMoving;
    @SuppressFBWarnings(
    value = "EI_EXPOSE_REP2",
    justification = "SnakeView needs to invoke controller actions during rendering")
    private final GameLoopController glc;

    /**
     * Constructor of the SnakeView class.
     *
     * @param glc is the panel in which the guard need to be rendered
     */
    public SnakeView(final GameLoopController glc) {
        this.glc = glc;
        try {
            loadResources();
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il caricamento delle immagini di Guard");
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("checkstyle:RedundantModifier")
    @Override
    public final void loadResources() throws IOException {
        rightSnake = ImageIO.read(SnakeView.class.getResourceAsStream("/Snake/rightSnake.png"));
        rightSnakeMoving = ImageIO.read(SnakeView.class.getResourceAsStream("/Snake/rightSnakeMoving.png"));
        leftSnake = ImageIO.read(SnakeView.class.getResourceAsStream("/Snake/leftSnake.png"));
        leftSnakeMoving = ImageIO.read(SnakeView.class.getResourceAsStream("/Snake/leftSnakeMoving.png"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final Graphics g, final EnemyImpl enemy) {
        final BufferedImage currentImage;
        if (enemy.getVelocityX() > 0) {
            currentImage = enemy.isStep() ? rightSnakeMoving : rightSnake;
        } else {
            currentImage = enemy.isStep() ? leftSnakeMoving : leftSnake;
        }
        final int shift = glc.getMapHandler().getShift();
        g.drawImage(currentImage, enemy.getX() + shift, enemy.getY(), enemy.getWidth(), enemy.getHeight(), null);
    }
}
