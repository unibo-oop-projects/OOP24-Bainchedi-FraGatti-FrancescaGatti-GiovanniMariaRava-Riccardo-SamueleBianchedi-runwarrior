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
public abstract class AbstractCharacterImpl implements Character {
    public static final int TO_TOUCH_FLOOR = 2;
    public static final int SPEED = 5;
    /**
     * Logger used for exceptions and error messages.
     */
    public static final Logger LOGGER = Logger.getLogger(AbstractCharacterImpl.class.getName());
    private final Rectangle collisionArea;
    private Rectangle swordArea;
    private boolean rightDirection;
    private BufferedImage right0;
    private BufferedImage right1;
    private BufferedImage right2;
    private BufferedImage left0;
    private BufferedImage left1;
    private BufferedImage left2;
    private BufferedImage jumpR;
    private BufferedImage jumpL;
    private BufferedImage attackR;
    private BufferedImage attackL;
    private BufferedImage tipR;
    private BufferedImage tipL;
    private CharacterAnimationHandler animation;
    private CharacterMovementHandler movement;
    private int sizeCharacter;

    /**
     * Constructor of the player; set player images, first position, 
     * movement and animation handler and his area.
     *
     * @param glc game-loop controller
     * @param commands object that handles the movement with the keyboard
     * @param mapHandler object that prints tiles
     * @param pCon object that creates powerup list
     */
    @SuppressWarnings("EI_EXPOSE_REP2")
    public AbstractCharacterImpl(final GameLoopController glc, final CharacterComand commands, 
    final HandlerMapElement mapHandler, final PowerUpController pCon) {
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
        this.rightDirection = movement.isRightDirection();
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
    @SuppressWarnings("EI_EXPOSE_REP")
    public CharacterMovementHandler getMovementHandler() {
        return this.movement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("EI_EXPOSE_REP")
    public CharacterAnimationHandler getAnimationHandler() {
        return this.animation;
    }

    /**
     * @return true if the player has been going to the right
     */
    protected boolean isRightDirection() {
        return rightDirection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("EI_EXPOSE_REP")
    public Rectangle getSwordArea() {
        return this.swordArea;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("EI_EXPOSE_REP")
    public Rectangle getArea() {
        return this.collisionArea;
    }

    /**
     * @return size of the plyer
     */
    protected int getSizeCharacter() {
        return sizeCharacter;
    }

    /**
     * @param i image to set
     */
    protected void setRight0(final BufferedImage i) {
        this.right0 = i;
    }

    /**
     * @param i image to set
     */
    protected void setRight1(final BufferedImage i) {
        this.right1 = i;
    }

    /**
     * @param i image to set
     */
    protected void setRight2(final BufferedImage i) {
        this.right2 = i;
    }

    /**
     * @param i image to set
     */
    protected void setLeft0(final BufferedImage i) {
        this.left0 = i;
    }

    /**
     * @param i image to set
     */
    protected void setLeft1(final BufferedImage i) {
        this.left1 = i;
    }

    /**
     * @param i image to set
     */
    protected void setLeft2(final BufferedImage i) {
        this.left2 = i;
    }

    /**
     * @param i image to set
     */
    protected void setJumpR(final BufferedImage i) {
        this.jumpR = i;
    }

    /**
     * @param i image to set
     */
    protected void setJumpL(final BufferedImage i) {
        this.jumpL = i;
    }

    /**
     * @param i image to set
     */
    protected void setAttackR(final BufferedImage i) {
        this.attackR = i;
    }

    /**
     * @param i image to set
     */
    protected void setAttackL(final BufferedImage i) {
        this.attackL = i;
    }

    /**
     * @param i image to set
     */
    protected void setTipR(final BufferedImage i) {
        this.tipR = i;
    }
    
    /**
     * @param i image to set
     */
    protected void setTipL(final BufferedImage i) {
        this.tipL = i;
    }
}
