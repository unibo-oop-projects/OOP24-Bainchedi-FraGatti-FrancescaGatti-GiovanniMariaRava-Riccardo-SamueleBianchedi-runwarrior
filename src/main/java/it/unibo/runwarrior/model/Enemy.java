package it.unibo.runwarrior.model;

import java.awt.Graphics;

public interface Enemy {
    public void render(Graphics g);

    public void die();
    
    public void update();

}
