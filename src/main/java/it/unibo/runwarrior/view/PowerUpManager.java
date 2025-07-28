package it.unibo.runwarrior.view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.model.PowerUp;

/**
 * Class that creates powerup objects and print them on the panel.
 */
public class PowerUpManager {
    public static final int END_OF_POWERUP = 222;
    public static final int NUM_POWERUP = 6;
    public static final int FIRST_DISTANCE_POWERUP = 55;
    public static final int OBSTACLE = 5;

    private final List<PowerUp> powerUps;
    private final HandlerMapElement mapHandler;
    private final int tileSize;

    /**
     * Constructor that takes powerup list and mapHandler.
     *
     * @param powerUps list of powerup
     * @param hM map handler
     */
    @SuppressWarnings("EI_EXPOSE_REP2")
    public PowerUpManager(final List<PowerUp> powerUps, final HandlerMapElement hM) {
        this.mapHandler = hM;
        this.tileSize = hM.getTileSize();
        this.powerUps = new ArrayList<>(powerUps);
    }

    /**
     * Prints the egg if the powerup isn't taken or the powerup if the egg has been opened.
     * If the powerup is taken, set his area at 0.
     *
     * @param gr2 Graphics object used to print
     */
    public void printPowerUp(final Graphics2D gr2) {
        BufferedImage im;
        for (final PowerUp p : powerUps) {
            p.powerUpImage();
            if (!p.isEggOpen()) {
                im = p.getEgg();
            } else {
                im = p.getImage();
            }
            if (!p.isPowerTaken()) {
                gr2.drawImage(im, p.getTouchArea().x + mapHandler.getShift(), 
                p.getTouchArea().y - (tileSize / 4), tileSize, tileSize, null);
            } else {
                p.getTouchArea().setSize(0, 0);
            }
        }
    }
}
