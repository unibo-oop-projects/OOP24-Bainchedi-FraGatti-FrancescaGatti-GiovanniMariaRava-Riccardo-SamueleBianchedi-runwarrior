package it.unibo.runwarrior.model;

import java.awt.image.BufferedImage;

public class MapElement {
    
    private BufferedImage image;
    private boolean collision;
    private boolean harmless;

    public void setImage(BufferedImage im){
        this.image = im;
    }

    public void setCollision(boolean col){
        this.collision = col;
    }

    public void setHarmless(boolean harm){
        this.harmless = harm;
    }

    public BufferedImage getImage(){
        return image;
    }

    public boolean getCollision(){
        return collision;
    }

    public boolean getHarmless(){
        return harmless;
    }
}

