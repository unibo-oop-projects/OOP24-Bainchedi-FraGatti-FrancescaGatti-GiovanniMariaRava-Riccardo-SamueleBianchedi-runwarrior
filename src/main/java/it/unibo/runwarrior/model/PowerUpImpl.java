package it.unibo.runwarrior.model;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.view.GameLoopPanel;
/**
 * Implementation of the powerup object
 */
public class PowerUpImpl implements PowerUp {
    
    private BufferedImage image;
    private BufferedImage egg;
    private Rectangle touchArea;
    private GameLoopPanel glp;
    private boolean powerTaken;
    private boolean eggOpen;

    /**
     * Constructor that creates powerup area and set the egg image
     *
     * @param glp game-loop panel
     */
    public PowerUpImpl(GameLoopPanel glp) {
        this.glp = glp;
        touchArea = new Rectangle();
        try {
            egg = ImageIO.read(getClass().getResourceAsStream("/PowerUps/egg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void powerUpImage() {
        try{
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BufferedImage getImage() {
        return this.image;
    }

    @Override
    public BufferedImage getEgg() {
        return this.egg;
    }

    @Override
    public void setTouchArea(final Rectangle deathPosition) {
        this.touchArea = deathPosition;
    }

    @Override
    public Rectangle getTouchArea() {
        return touchArea;
    }

    @Override
    public boolean isPowerTaken() {
        return powerTaken;
    }

    @Override
    public boolean isEggOpen() {
        return eggOpen;
    }

    @Override
    public void takePower() {
        powerTaken = true;
    }

    @Override
    public void openTheEgg() {
        eggOpen = true;
    }
}
