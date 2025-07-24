package it.unibo.runwarrior.model.enemy.impl;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.controller.enemy.impl.EnemyHandlerImpl;
import it.unibo.runwarrior.model.PowerUpImpl;
import it.unibo.runwarrior.model.enemy.api.Enemy;

/**
 * Implementation of Enemy interfaces
 */
public class EnemyImpl implements Enemy {
    private final static int NUM_UPDATE_FRAME = 20;
    private final static int COLLISION_HEIGHT_WIDTH = 48;
    private int x;
    private final int y;
    private final int width;
    private final int height;
    private final int type;
    private int frameCounter;
    private boolean step;

    private boolean solid;

    private int velocityX;

    private EnemyHandlerImpl enemyHandler;

    private final GameLoopPanel glp;
    private PowerUpImpl powerUp;
    
    /**
     * Constructor of the class
     * @param x starting x
     * @param y starting y
     * @param width 
     * @param height
     * @param solid setting for collision
     * @param handler used to render the enemy
     * @param glp panel in which enemies are shown
     * @param type of the enemy (Goblin, Guard, Snake, Monkey, Wizard)
     */
    public EnemyImpl(int x, final int y, final int width, final int height, final boolean solid, final EnemyHandlerImpl handler, final GameLoopPanel glp, final int type) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        x += velocityX;
        checkMapCollision(glp.getMapHandler().getCollisionRectangles());
        frameCounter++;
        if (frameCounter >= NUM_UPDATE_FRAME) {
            step =! step;
            frameCounter = 0;
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void die() {
        // powerUp.setTouchArea(new Rectangle(x, y, width, height));
        // glp.getPowersFactory().PowerUpAppearance(powerUp);
        enemyHandler.removeEnemy(this);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void checkMapCollision(final List<Rectangle> obstacles) {
        int newX = x + velocityX;
        Rectangle futureBounds = new Rectangle(newX, y, width, height);
        for (Rectangle rectangle : obstacles) {
            if (futureBounds.intersects(rectangle)) {
                if (velocityX > 0) {
                    x = rectangle.x - width;
                } else if (velocityX < 0) {
                    x = rectangle.x + rectangle.width;
                }
                velocityX = -velocityX;
                return;
            }
        }
        x = newX;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, COLLISION_HEIGHT_WIDTH, COLLISION_HEIGHT_WIDTH);
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

    public int getHeight() {
        return height;
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

    public int getType() {
        return type;
    }

    public boolean isStep() {
        return step;
    }

    public void setStep(boolean step) {
        this.step = step;
    }
}
