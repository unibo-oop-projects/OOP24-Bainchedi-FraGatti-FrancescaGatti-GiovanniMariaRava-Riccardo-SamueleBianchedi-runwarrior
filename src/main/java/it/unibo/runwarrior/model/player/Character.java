package it.unibo.runwarrior.model.player;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import it.unibo.runwarrior.controller.CharacterAnimationHandlerImpl;
import it.unibo.runwarrior.controller.CharacterMovementHandlerImpl;

public interface Character {

    /**
     * Updates the state of the player.
     * Makes the player move and change frame while moving.
     */
    public void update();

    /**
     * @param gr2
     * Print the player images on the screen,
     * including the tip/stick if the player is at his secondo powerup
     */
    public void drawPlayer(Graphics2D gr2);

    /**
     * @param gr2
     * da cancellare
     */
    public void drawRectangle(Graphics2D gr2);

    /**
     * Loads the images for every skin
     */
    public void playerImage();

    /**
     * @return the object that handles the player movement,
     * including the collisions with tiles, powerups and enemies
     */
    public CharacterMovementHandlerImpl getMovementHandler();

    /**
     * @return the object that handles the player animation,
     * so how the player frames change
     */
    public CharacterAnimationHandlerImpl getAnimationHandler();

    /**
     * Updates the collision area of the player,
     * including the tip/stick
     */
    public void updatePlayerPosition();

    /**
     * @return the collision area of the weapon
     */
    public Rectangle getSwordArea();

    /**
     * @return the collision are of the player
     */
    public Rectangle getArea();
}