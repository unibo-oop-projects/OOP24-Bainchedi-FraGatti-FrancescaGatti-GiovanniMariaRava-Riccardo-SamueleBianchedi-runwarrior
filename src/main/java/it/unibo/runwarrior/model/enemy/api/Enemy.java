package it.unibo.runwarrior.model.enemy.api;

import java.awt.Rectangle;
import java.util.List;

/**
 * Interfaces that manage the enemy logic part.
 */
public interface Enemy {
    /**
     * Handles the logic that should be executed when the entity dies.
     * This include playing an animation or removing the entity from the game. 
     */
    void die();
        
    /**
     * Updates the state of the entity.
     * This include movement, animation and collision.
     */
    void update();

    /**
     * Check whether the current enemy collides with any of the obstacles in the map.
     * 
     * @param obstacles is the list of obstacle's position
     */
    void checkMapCollision(List<Rectangle> obstacles);

    /**
     * Return the bounds of the enemy's rectangle.
     * 
     * @return the rectangle of the enemy
     */
    Rectangle getBounds();

    /**
     * Returns the X-coordinate of the entity.
     * 
     * @return the current x position
     */
    int getX();

    /**
     * Sets the X-coordinate of the entity.
     * 
     * @param x the new x position
     */
    void setX(int x);

    /**
     * Returns the Y-coordinate of the entity.
     * 
     * @return the current y position
     */
    int getY();

    /**
     * Returns the width of the entity.
     * 
     * @return the width 
     */
    int getWidth();

    /**
     * Returns the height of the entity.
     * 
     * @return the height 
     */
    int getHeight();

    /**
     * Returns whether the entity is solid.
     * 
     * @return {@code true} if the entity is solid, {@code false} otherwise
     */
    boolean isSolid();

    /**
     * Sets the solidity of the entity.
     * 
     * @param solid {@code true} to make the entity solid, {@code false} to make it passable
     */
    void setSolid(boolean solid);

    /**
     * Returns the current horizontal velocity of the entity.
     * 
     * @return the velocity along the X axis
     */
    int getVelocityX();

    /**
     * Sets the horizontal velocity of the entity.
     * 
     * @param velocityX the new velocity along the X axis
     */
    void setVelocityX(int velocityX);

    /**
     * Returns the type identifier of the entity.
     * 
     * @return an integer representing the entity type
     */
    int getType();

    /**
     * Returns whether the entity is currently in a "step" state.
     * 
     * @return {@code true} if in step state, {@code false} otherwise
     */
    boolean isStep();

    /**
     * Sets the "step" state of the entity.
     * 
     * @param step {@code true} to enable step state, {@code false} to disable
     */
    void setStep(boolean step);
}
