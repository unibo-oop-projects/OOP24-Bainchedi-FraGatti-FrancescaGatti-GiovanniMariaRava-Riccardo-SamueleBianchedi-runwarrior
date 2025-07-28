package it.unibo.runwarrior.model;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.controller.GameLoopController;

/**
 * Implementation of the powerup object.
 */
public class PowerUpImpl implements PowerUp {
    private static final Logger LOGGER = Logger.getLogger(PowerUpImpl.class.getName());
    private BufferedImage image;
    private BufferedImage egg;
    private final Rectangle touchArea;
    private final GameLoopController glc;
    private boolean powerTaken;
    private boolean eggOpen;

    /**
     * Constructor that creates powerup area and set the egg image.
     *
     * @param glc game-loop controller
     */
    @SuppressWarnings("EI_EXPOSE_REP2")
    public PowerUpImpl(final GameLoopController glc) {
        this.glc = glc;
        touchArea = new Rectangle();
        try {
            egg = ImageIO.read(PowerUpImpl.class.getResourceAsStream("/PowerUps/egg.png"));
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, "Cannot load egg image");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void powerUpImage() {
        try {
            if (glc.getPowersHandler().getPowers() == 0) {
                image = ImageIO.read(getClass().getResourceAsStream("/PowerUps/armour.png"));
            }
            if (glc.getPowersHandler().getPowers() == 1) {
                image = ImageIO.read(getClass().getResourceAsStream("/PowerUps/sword.png"));
            }
            if (glc.getPowersHandler().getPowers() == 3) {
                image = ImageIO.read(getClass().getResourceAsStream("/PowerUps/cape.png"));
            }
            if (glc.getPowersHandler().getPowers() == 4) {
                image = ImageIO.read(getClass().getResourceAsStream("/PowerUps/stick.png"));
            }
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, "Cannot load powerup images");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedImage getImage() {
        return copy(image);
    }

    /**
     * Create a copy of the image in order to not modify it.
     *
     * @param im original image
     * @return copy of the original
     */
    private BufferedImage copy(final BufferedImage im) {
        BufferedImage copy = new BufferedImage(im.getWidth(), im.getHeight(), im.getType());
        Graphics2D g = copy.createGraphics();
        g.drawImage(im, 0, 0, null);
        return copy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedImage getEgg() {
        return copy(egg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("EI_EXPOSE_REP")
    public Rectangle getTouchArea() {
        return touchArea;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPowerTaken() {
        return powerTaken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEggOpen() {
        return eggOpen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void takePower() {
        powerTaken = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void openTheEgg() {
        eggOpen = true;
    }
}
