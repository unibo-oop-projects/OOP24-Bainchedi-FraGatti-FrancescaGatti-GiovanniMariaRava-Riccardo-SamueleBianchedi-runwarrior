package it.unibo.runwarrior.model.enemy.impl;

import java.awt.Rectangle;
import java.util.List;

import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.controller.enemy.impl.EnemyHandlerImpl;
import it.unibo.runwarrior.model.PowerUpImpl;
import it.unibo.runwarrior.model.enemy.api.Enemy;

/**
 * Implementation of Enemy interfaces.
 */
public class EnemyImpl implements Enemy {
    private static final int NUM_UPDATE_FRAME = 20;
    private static final int COLLISION_HEIGHT_WIDTH = 48;
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
     * Constructor of the class.
     * 
     * @param x starting x
     * @param y starting y
     * @param width 
     * @param height
     * @param solid setting for collision
     * @param handler used to render the enemy
     * @param glp panel in which enemies are shown
     * @param type of the enemy (Goblin, Guard, Snake, Monkey, Wizard)
     */
    public EnemyImpl(int x, final int y, final int width, final int height, final boolean solid, 
                    final EnemyHandlerImpl handler, final GameLoopPanel glp, final int type) {
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
            step = !step;
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
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, COLLISION_HEIGHT_WIDTH, COLLISION_HEIGHT_WIDTH);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getX() {
        return x;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setX(final int x) {
        this.x = x;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getY(){
        return y;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return width;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return height;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSolid() {
        return solid;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setSolid(final boolean solid) {
        this.solid = solid;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getVelocityX() {
        return velocityX;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setVelocityX(final int velocityX) {
        this.velocityX = velocityX;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getType() {
        return type;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStep() {
        return step;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setStep(final boolean step) {
        this.step = step;
    }
}
