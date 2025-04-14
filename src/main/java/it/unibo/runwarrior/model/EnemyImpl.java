package it.unibo.runwarrior.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class EnemyImpl implements Enemy{
    

    public int x, y, width, height;

    public boolean solid;

    public int velocityX;
    public int velocityY;

    public BufferedImage image; 

    
    public EnemyImpl(int x, int y, int width, int height, boolean solid) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.solid = solid;
    }
    @Override
    public void die() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void render(Graphics g) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        
    }


}
