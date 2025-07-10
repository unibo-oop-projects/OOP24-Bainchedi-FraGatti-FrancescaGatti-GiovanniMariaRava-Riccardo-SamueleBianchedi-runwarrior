package it.unibo.runwarrior.model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.controller.EnemyHandler;


public abstract class EnemyImpl implements Enemy{
    

    public int x, y, width, height;

    public boolean solid;

    public int velocityX;

    public EnemyHandler enemyHandler;

    public BufferedImage image;

    public GameLoopPanel glp;
    private PowerUpImpl powerUp;

    
    public EnemyImpl(int x, int y, int width, int height, boolean solid, EnemyHandler handler, GameLoopPanel glp) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.solid = solid;
        this.enemyHandler = handler;
        
        this.glp = glp;
    }

    @Override
    public void die() {
        powerUp.setTouchArea(new Rectangle(x, y, width, height));
        glp.getPowersFactory().PowerUpAppearance(powerUp);
        enemyHandler.removeEnemy(this);
    }
    
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }


    public void CheckMapCollision(List<Rectangle> obastacles){
        Rectangle enemyRectangle = getBounds();

        for (Rectangle rectangle : obastacles) {
            if(enemyRectangle.intersects(rectangle)){
                velocityX = -velocityX;
                return;
            }
        }
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public boolean isSolid() {
        return solid;
    }
    public void setSolid(boolean solid) {
        this.solid = solid;
    }
    public int getVelocityX() {
        return velocityX;
    }
    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }
    public BufferedImage getImage() {
        return image;
    }
    public void setImage(BufferedImage image) {
        this.image = image;
    }


}
