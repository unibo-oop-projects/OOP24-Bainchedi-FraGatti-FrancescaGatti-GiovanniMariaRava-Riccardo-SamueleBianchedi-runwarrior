package it.unibo.runwarrior.model;

import java.awt.Graphics2D;

public interface Character {

    public void update();
    public void drawPlayer(Graphics2D gr2);
    public void drawRectangle(Graphics2D gr2);
    public void frameChanger();
    public void playerImage();
    public void setLocationAfterPowerup(int x, int y, int realx);

    public int getGroundX();
    public int getPlX();
    public int getPlY();
    public int getScX();
    public int getScY();
}