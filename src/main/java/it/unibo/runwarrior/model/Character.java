package it.unibo.runwarrior.model;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public interface Character {

    public void update();
    public void drawPlayer(Graphics2D gr2);
    public void drawRectangle(Graphics2D gr2);
    public void playerImage();
    public void updatePlayerPosition();
    public Rectangle getArea();
    public int getSpeed();
}