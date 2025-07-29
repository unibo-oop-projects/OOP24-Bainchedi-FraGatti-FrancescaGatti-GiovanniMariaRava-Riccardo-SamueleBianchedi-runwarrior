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
 * Implementation of the enemy view with Monkey enemy.
 */

public final class MonkeyView implements EnemyView {
    private static final Logger LOGGER = Logger.getLogger(MonkeyView.class.getName());
    private BufferedImage rightMonkey;
    private BufferedImage leftMonkey;
    private BufferedImage rightMonkeyMoving;
    private BufferedImage leftMonkeyMoving;
    private BufferedImage rightMonkeyRunning;
    private BufferedImage leftMonkeyRunning;
    private BufferedImage image;
    /*
    * SuppressFBWarnings: EI_EXPOSE_REP2
    *
    * Il warning EI_EXPOSE_REP2 segnala che un oggetto mutabile fornito come parametro
    * al costruttore viene salvato direttamente in un campo interno senza difensive copy.
    * In questo caso, la scelta è intenzionale: MonkewyView ha bisogno di mantenere un 
    * riferimento diretto al controller per invocare azioni durante il rendering, 
    * come previsto dall'architettura MVC. Non ha senso creare una copia, perché il controller 
    * rappresenta un punto di coordinamento globale e condiviso.
    */
    @SuppressFBWarnings(
    value = "EI_EXPOSE_REP2",
    justification = "MonkeyView needs to invoke controller actions during rendering")
    private final GameLoopController glc;

    /**
     * Constructor of the class MonkeyView.
     *
     * @param glc is the panel in which the monkey need to be renderd
     */
    public MonkeyView(final GameLoopController glc) {
        this.glc = glc;
        try {
            loadResources();
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il caricamento delle immagini di Monkey");
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
        image = rightMonkey;
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
            currentImage = enemy.isStep() ? rightMonkeyMoving : rightMonkeyRunning;
            image = rightMonkey;
        } else {
            currentImage = enemy.isStep() ? leftMonkeyMoving : leftMonkeyRunning;
            image = leftMonkey;
        }
        final int shift = glc.getMapHandler().getShift();
        g.drawImage(currentImage, enemy.getX() + shift, enemy.getY(), enemy.getWidth(), enemy.getHeight(), null);
    }
}
