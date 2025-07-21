package it.unibo.runwarrior.model;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import it.unibo.runwarrior.view.GameLoopPanel;

/**
 * Implementation of the powerup object.
 */
public class PowerUpImpl implements PowerUp {
    private BufferedImage image;
    private BufferedImage egg;
    private Rectangle touchArea;
    private GameLoopPanel glp;
    private boolean powerTaken;
    private boolean eggOpen;

    /**
     * Constructor that creates powerup area and set the egg image.
     *
     * @param glp game-loop panel
     */
    public PowerUpImpl(final GameLoopPanel glp) {
        this.glp = glp;
        touchArea = new Rectangle();
        try {
            egg = ImageIO.read(getClass().getResourceAsStream("/PowerUps/egg.png"));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void powerUpImage() {
        try {
            if (glp.getPowersHandler().getPowers() == 0) {
                image = ImageIO.read(getClass().getResourceAsStream("/PowerUps/armour.png"));
            }
            if (glp.getPowersHandler().getPowers() == 1) {
                image = ImageIO.read(getClass().getResourceAsStream("/PowerUps/sword.png"));
            }
            if (glp.getPowersHandler().getPowers() == 3) {
                image = ImageIO.read(getClass().getResourceAsStream("/PowerUps/cape.png"));
            }
            if (glp.getPowersHandler().getPowers() == 4) {
                image = ImageIO.read(getClass().getResourceAsStream("/PowerUps/stick.png"));
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedImage getImage() {
        return this.image;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedImage getEgg() {
        return this.egg;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTouchArea(final Rectangle deathPosition) {
        this.touchArea = deathPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
