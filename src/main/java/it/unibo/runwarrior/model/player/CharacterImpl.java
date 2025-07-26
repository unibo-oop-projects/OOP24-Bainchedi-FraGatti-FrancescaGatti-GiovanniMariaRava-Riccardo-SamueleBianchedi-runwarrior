package it.unibo.runwarrior.model.player;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import it.unibo.runwarrior.controller.CharacterAnimationHandler;
import it.unibo.runwarrior.controller.CharacterAnimationHandlerImpl;
import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.CharacterMovementHandler;
import it.unibo.runwarrior.controller.CharacterMovementHandlerImpl;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.PowerUpManager;

/**
 * Class that creates the player.
 */
public abstract class CharacterImpl implements Character {
    public static final int TO_TOUCH_FLOOR = 2;
    public static final int SPEED = 5;
    protected int sizeCharacter;
    protected Rectangle collisionArea;
    protected Rectangle swordArea;
    protected boolean rightDirection;
    protected BufferedImage right0;
    protected BufferedImage right1;
    protected BufferedImage right2;
    protected BufferedImage left0;
    protected BufferedImage left1;
    protected BufferedImage left2;
    protected BufferedImage jumpR;
    protected BufferedImage jumpL;
    protected BufferedImage attackR;
    protected BufferedImage attackL;
    protected BufferedImage tipR;
    protected BufferedImage tipL;

    protected CharacterComand cmd;
    protected CharacterAnimationHandler animation;
    protected CharacterMovementHandler movement;

    /**
     * Constructor of the player; set player images, first position, 
     * movement and animation handler and his area.
     *
     * @param panel game-loop panel
     * @param commands object that handles the movement with the keyboard
     * @param collision object that handles map tiles collision
     * @param mapHandler object that prints tiles
     * @param pMan object that prints powerups
     */
    public CharacterImpl(final GameLoopPanel panel, final CharacterComand commands, 
    final HandlerMapElement mapHandler, final PowerUpManager pMan) {
        this.cmd = commands;
        sizeCharacter = mapHandler.getTileSize() * 2;
        this.movement = new CharacterMovementHandlerImpl(panel, this, commands, mapHandler, pMan);
        this.animation = new CharacterAnimationHandlerImpl(commands, movement, right0, right1, right2, 
        left0, left1, left2, jumpR, jumpL, attackR, attackL, tipR, tipL);
        collisionArea = new Rectangle(movement.getPlX() + (sizeCharacter / 4), movement.getPlY() + (sizeCharacter / 4),
        sizeCharacter / 2, sizeCharacter - (sizeCharacter / 4) - TO_TOUCH_FLOOR);
        swordArea = new Rectangle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        this.rightDirection = movement.getRightDirection();
        movement.movePlayer();
        animation.frameChanger();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePlayerPosition() {
        collisionArea.setLocation(movement.getPlX() + (sizeCharacter / 4), movement.getPlY() + (sizeCharacter / 4));
        updateAttackCollision();
    }

    /**
     * Used by SwordWarrior and StickWizard to update swordArea when attacking.
     */
    public void updateAttackCollision(){
        //default void for skin without a weapon
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawPlayer(Graphics2D gr2) {
        BufferedImage im = null;
        BufferedImage tip = null;
        im = animation.imagePlayer(rightDirection);
        gr2.drawImage(im, movement.getScX(), movement.getPlY(), sizeCharacter, sizeCharacter, null);
        if (animation.isAttacking()) {
            tip = animation.getTip(rightDirection);
            int tipPos = rightDirection ? 1 : (-1);
            gr2.drawImage(tip, movement.getScX() + (tipPos * sizeCharacter), movement.getPlY(), sizeCharacter, sizeCharacter, null);
            //gr2.drawRect(movement.getScX() + (tipPos * sizeCharacter), swordArea.y, swordArea.width, swordArea.height);
        }
    }

    @Override
    public void drawRectangle(Graphics2D gr) {
        gr.drawRect(movement.getScX() + (sizeCharacter / 4), collisionArea.y, collisionArea.width, collisionArea.height);
        //si sposta in avanti perchè segue playerX non screenX
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
