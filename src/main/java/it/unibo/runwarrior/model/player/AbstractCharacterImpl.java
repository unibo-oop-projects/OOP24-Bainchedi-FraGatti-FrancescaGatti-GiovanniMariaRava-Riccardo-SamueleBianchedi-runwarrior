package it.unibo.runwarrior.model.player;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import it.unibo.runwarrior.controller.CharacterAnimationHandler;
import it.unibo.runwarrior.controller.CharacterAnimationHandlerImpl;
import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.CharacterMovementHandler;
import it.unibo.runwarrior.controller.CharacterMovementHandlerImpl;
import it.unibo.runwarrior.controller.GameLoopController;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.controller.PowerUpController;

/**
 * Class that creates the player.
 */
@SuppressWarnings("checkstyle:VisibilityModifier")
public abstract class AbstractCharacterImpl implements Character {
    public static final int TO_TOUCH_FLOOR = 2;
    public static final int SPEED = 5;
    protected static final Logger LOGGER = Logger.getLogger(AbstractCharacterImpl.class.getName());
    protected int sizeCharacter;
    private Rectangle collisionArea;
    protected Rectangle swordArea;
    protected boolean rightDirection;
    protected BufferedImage right0, right1, right2, left0, left1, left2, jumpR, jumpL, attackR, attackL, tipR, tipL;
    // protected BufferedImage right0;
    // protected BufferedImage right1;
    // protected BufferedImage right2;
    // protected BufferedImage left0;
    // protected BufferedImage left1;
    // protected BufferedImage left2;
    // protected BufferedImage jumpR;
    // protected BufferedImage jumpL;
    // protected BufferedImage attackR;
    // protected BufferedImage attackL;
    // protected BufferedImage tipR;
    // protected BufferedImage tipL;

    protected CharacterComand cmd;
    protected CharacterAnimationHandler animation;
    protected CharacterMovementHandler movement;

    /**
     * Constructor of the player; set player images, first position, 
     * movement and animation handler and his area.
     *
     * @param glc game-loop controller
     * @param commands object that handles the movement with the keyboard
     * @param mapHandler object that prints tiles
     * @param pCon object that creates powerup list
     */
    public AbstractCharacterImpl(final GameLoopController glc, final CharacterComand commands, 
    final HandlerMapElement mapHandler, final PowerUpController pCon) {
        this.cmd = commands;
        sizeCharacter = mapHandler.getTileSize() * 2;
        this.movement = new CharacterMovementHandlerImpl(glc, this, commands, mapHandler, pCon);
        this.animation = new CharacterAnimationHandlerImpl(commands, movement);
        collisionArea = new Rectangle(movement.getPlX() + (sizeCharacter / 4), movement.getPlY() + (sizeCharacter / 4),
        sizeCharacter / 2, sizeCharacter - (sizeCharacter / 4) - TO_TOUCH_FLOOR);
        swordArea = new Rectangle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void update() {
        playerImage();
        updatePlayerPosition();
        this.rightDirection = movement.getRightDirection();
        movement.movePlayer();
        animation.frameChanger();
    }

    /**
     * Updates the collision area of the player,
     * including the tip/stick.
     */
    private void updatePlayerPosition() {
        collisionArea.setLocation(movement.getPlX() + (sizeCharacter / 4), movement.getPlY() + (sizeCharacter / 4));
        updateAttackCollision();
    }

    /**
     * Used by SwordWarrior and StickWizard to update swordArea when attacking.
     */
    protected void updateAttackCollision() {
        //default void for skin without a weapon
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawPlayer(final Graphics2D gr2) {
        animation.setImages(right0, right1, right2, left0, left1, left2, jumpR, jumpL, attackR, attackL, tipR, tipL);
        final BufferedImage im;
        final BufferedImage tip;
        im = animation.imagePlayer(rightDirection);
        gr2.drawImage(im, movement.getScX(), movement.getPlY(), sizeCharacter, sizeCharacter, null);
        if (animation.isAttacking()) {
            tip = animation.getTip(rightDirection);
            final int tipPos = rightDirection ? 1 : (-1);
            gr2.drawImage(tip, movement.getScX() + (tipPos * sizeCharacter), movement.getPlY(), 
            sizeCharacter, sizeCharacter, null);
            //gr2.drawRect(movement.getScX() + (tipPos * sizeCharacter), swordArea.y, swordArea.width, swordArea.height);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void playerImage();

    /**
     * {@inheritDoc}
     */
    @Override
    public CharacterMovementHandler getMovementHandler() {
        return this.movement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharacterAnimationHandler getAnimationHandler() {
        return this.animation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rectangle getSwordArea() {
        return this.swordArea;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rectangle getArea() {
        return this.collisionArea;
    }
}
