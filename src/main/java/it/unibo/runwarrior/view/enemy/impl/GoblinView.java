package it.unibo.runwarrior.view.enemy.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.runwarrior.controller.GameLoopController;
import it.unibo.runwarrior.controller.enemy.EnemySpawner;
import it.unibo.runwarrior.model.enemy.impl.EnemyImpl;
import it.unibo.runwarrior.view.enemy.api.EnemyView;
/**
 * Implementation of the enemy view with Goblin enemy.
 */

public class GoblinView implements EnemyView {
    private BufferedImage rightGoblin;
    private BufferedImage leftGoblin;
    private BufferedImage rightGoblinMoving;
    private BufferedImage leftGoblinMoving;
    private BufferedImage image;
    @SuppressFBWarnings(
    value = "EI_EXPOSE_REP2",
    justification = "GoblinView needs to invoke controller actions during rendering")
    private final GameLoopController glc;
    private static final Logger LOGGER = Logger.getLogger(GoblinView.class.getName());

    /**
     * Constructor of the class.
     * 
     * @param glc is the panel in which the goblin need to be rendered.
     */
    public GoblinView(final GameLoopController glc) {
        this.glc = glc;
        try {
            loadResources();
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il caricamento delle immagini Goblin");
        } 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void loadResources() throws IOException {
        rightGoblin = ImageIO.read(GoblinView.class.getResourceAsStream("/Goblin/rightGoblin.png"));
        leftGoblin = ImageIO.read(GoblinView.class.getResourceAsStream("/Goblin/leftGoblin.png"));
        rightGoblinMoving = ImageIO.read(GoblinView.class.getResourceAsStream("/Goblin/rightGoblinMoving.png"));
        leftGoblinMoving = ImageIO.read(GoblinView.class.getResourceAsStream("/Goblin/leftGoblinMoving.png"));
        image = rightGoblin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final Graphics g, final EnemyImpl enemy) {
        final BufferedImage currentImage;
        if (enemy.getVelocityX() == 0) {
            currentImage = image;
        } else if (enemy.getVelocityX() > 0) {
            currentImage = enemy.isStep() ? rightGoblinMoving : rightGoblin;
        } else {
            currentImage = enemy.isStep() ? leftGoblinMoving : leftGoblin;
        }
        final int shift = glc.getMapHandler().getShift();
        g.drawImage(currentImage, enemy.getX() + shift, enemy.getY(), enemy.getWidth(), enemy.getHeight(), null);
    }
}
