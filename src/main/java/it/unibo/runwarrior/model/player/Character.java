package it.unibo.runwarrior.model.player;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import it.unibo.runwarrior.controller.CharacterAnimationHandler;
import it.unibo.runwarrior.controller.CharacterMovementHandler;

/**
 * Character entity.
 */
public interface Character {

    /**
     * Updates the state of the player.
     * Makes the player move and change frame while moving.
     */
    void update();

    /**
     * Print the player images on the screen,
     * including the tip/stick if the player is at his secondo powerup.
     *
     * @param gr2 object of Graphics2D class to print image
     */
    void drawPlayer(Graphics2D gr2);

    /**
     * Loads the images for every skin.
     */
    void playerImage();

    /**
     * @return the object that handles the player movement, including the collisions with tiles, powerups and enemies.
     */
    CharacterMovementHandler getMovementHandler();

    /**
     * @return the object that handles the player animation, so how the player frames change.
     */
    CharacterAnimationHandler getAnimationHandler();

    /**
     * Set sword area.
     *
     * @param swordArea Rectangle of the sword
     */
    void setSwordArea(Rectangle swordArea);

    /**
     * @return the collision area of the weapon
     */
    Rectangle getSwordArea();

    /**
     * @return the collision are of the player
     */
    Rectangle getArea();
}
