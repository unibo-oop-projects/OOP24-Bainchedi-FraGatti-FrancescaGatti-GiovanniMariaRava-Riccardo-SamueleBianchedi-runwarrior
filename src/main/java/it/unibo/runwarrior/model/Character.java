package it.unibo.runwarrior.model;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import it.unibo.runwarrior.controller.CharacterMovementHandler;

public interface Character {

    public void update();
    public void drawPlayer(Graphics2D gr2);
    public void drawRectangle(Graphics2D gr2);
    public void playerImage();
    public CharacterMovementHandler getMovementHandler();
    public void updatePlayerPosition();
    public Rectangle getArea();
    public int getSpeed();
}