package it.unibo.runwarrior.model.enemy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.controller.EnemyHandler;
import it.unibo.runwarrior.model.PowerUpImpl;


public abstract class EnemyImpl implements Enemy{
    private final static int NUM_UPDATE_FRAME = 20;
    public int x;
    public int y;
    public int width; 
    public int height;
    public int type;
    public int frameCounter = 0;
    public boolean step = false;

    public boolean solid;

    public int velocityX;

    public EnemyHandler enemyHandler;

    public BufferedImage image;

    public GameLoopPanel glp;
    private PowerUpImpl powerUp;

    
    public EnemyImpl(int x, int y, int width, int height, boolean solid, EnemyHandler handler, GameLoopPanel glp, int type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.solid = solid;
        this.enemyHandler = handler;
        this.type = type;
        this.glp = glp;
        setVelocityX(1);
    }

    @Override
    public void update() {
        x += velocityX;
        checkMapCollision(glp.getMapHandler().getCollisionRectangles());
        frameCounter++;
        if (frameCounter >= NUM_UPDATE_FRAME) { 
            step = !step;
            frameCounter = 0;
        }
        
    }

    @Override
    public void die() {
        // powerUp.setTouchArea(new Rectangle(x, y, width, height));
        // glp.getPowersFactory().PowerUpAppearance(powerUp);
        enemyHandler.removeEnemy(this);
    }
    
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }

    @Override
    public void checkMapCollision(List<Rectangle> obstacles){
        Rectangle enemyRectangle = getBounds();
        int newX = x + velocityX;
        Rectangle futureBounds = new Rectangle(newX, y, width, height);
        for (Rectangle rectangle : obstacles) {
            if(futureBounds.intersects(rectangle)){
                if(velocityX>0){
                    x = rectangle.x - width;
                }else if (velocityX < 0) { 
                    x = rectangle.x + rectangle.width;
                }
                velocityX = -velocityX;
                return;
            }
        }
        x = newX;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY(){
        return y;
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
    public int getType(){
        return type;
    }


}
