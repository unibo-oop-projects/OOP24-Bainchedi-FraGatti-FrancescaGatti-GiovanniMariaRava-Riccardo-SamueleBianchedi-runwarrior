package it.unibo.runwarrior.model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public interface PowerUp {

    public void powerUpImage();
    public BufferedImage getImage();
    public BufferedImage getEgg();
    public void setTouchArea(Rectangle deathPosition);
    public Rectangle getTouchArea();
    public boolean isPowerTaken();
    public boolean isEggOpen();
    public void takePower();
    public void openTheEgg();
}
