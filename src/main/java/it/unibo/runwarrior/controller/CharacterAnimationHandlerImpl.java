package it.unibo.runwarrior.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import it.unibo.runwarrior.model.player.PlayerFrame;

/**
 * Class that handle player frames changing.
 */
public class CharacterAnimationHandlerImpl implements CharacterAnimationHandler {

    private final CharacterComand cmd;
    private final CharacterMovementHandler movement;

    private static final int TIME_TO_CHANGE = 8;
    private static final int LIMIT_ATTACK = 60;
    private int changeFrame;
    private boolean crossWalk;
    private int useAttackMoving;
    private PlayerFrame playerFrame = PlayerFrame.STOP_FRAME;
    private BufferedImage right0, right1, right2, left0, left1, left2, jumpR, jumpL, attackR, attackL, tipR, tipL;

    /**
     * Constructor of the player animation handler that sets current player images.
     *
     * @param cmd keyboard handler
     * @param move player movement handler
     * @param im current player images
     */
    public CharacterAnimationHandlerImpl(final CharacterComand cmd, final CharacterMovementHandler move) {
        this.cmd = cmd;
        this.movement = move;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setImages(final BufferedImage... im){
        right0 = im[0];
        right1 = im[1];
        right2 = im[2];
        left0 = im[3];
        left1 = im[4];
        left2 = im[5];
        jumpR = im[6];
        jumpL = im[7];
        attackR = im[8];
        attackL = im[9];
        tipR = im[10];
        tipL = im[11];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void frameChanger() {
        if (!cmd.isStop() && !cmd.isJumping()) {
            changeFrame++;
            if (changeFrame > TIME_TO_CHANGE) {
                switch (playerFrame) {
                    case STOP_FRAME, ATTACK_FRAME, JUMP_FRAME -> {
                        playerFrame = crossWalk ? PlayerFrame.GO_FRAME1 : PlayerFrame.GO_FRAME2;
                        crossWalk = !crossWalk;
                    }
                    case GO_FRAME1, GO_FRAME2 -> playerFrame = PlayerFrame.STOP_FRAME;
                }
                changeFrame = 0;
            }
            useAttackMoving++;
            if (cmd.isAttacking() && movement.canAttack() && useAttackMoving > LIMIT_ATTACK) {
                playerFrame = PlayerFrame.ATTACK_FRAME;
                useAttackMoving = 0;
            }
        }
        else if (cmd.isJumping()) {
            playerFrame = PlayerFrame.JUMP_FRAME;
            if (cmd.isAttacking() && movement.canAttack()) {
                playerFrame = PlayerFrame.ATTACK_FRAME;
            }
        }
        else if (cmd.isAttacking() && movement.canAttack()) {
            playerFrame = PlayerFrame.ATTACK_FRAME;
        }
        else {
            playerFrame = PlayerFrame.STOP_FRAME;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedImage imagePlayer(final boolean rightDirection) {
        BufferedImage im = null;
        if (rightDirection) {
            switch (playerFrame) {
                case STOP_FRAME -> im = right0;
                case GO_FRAME1 -> im = right1;
                case GO_FRAME2 -> im = right2;
                case JUMP_FRAME -> im = jumpR;
                case ATTACK_FRAME -> {
                    im = attackR;
                }
            }
        } else {
            switch (playerFrame) {
                case STOP_FRAME -> im = left0;
                case GO_FRAME1 -> im = left1;
                case GO_FRAME2 -> im = left2;
                case JUMP_FRAME -> im = jumpL;
                case ATTACK_FRAME -> {
                    im = attackL;
                }
            }
        }
        return im;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerFrame getFrame(){
        return this.playerFrame;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAttacking() {
        return playerFrame == PlayerFrame.ATTACK_FRAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BufferedImage getTip(final boolean rightDirection) {
        if (playerFrame == PlayerFrame.ATTACK_FRAME && rightDirection) {
            return copy(tipR);
        } else {
            return copy(tipL);
        }
    }

    /**
     * Create a copy of the image in order to not modify it.
     *
     * @param im original image
     * @return copy of the original
     */
    private BufferedImage copy(final BufferedImage im) {
        if (im != null){
            BufferedImage copy = new BufferedImage(im.getWidth(), im.getHeight(), im.getType());
            Graphics2D g = copy.createGraphics();
            g.drawImage(im, 0, 0, null);
            return copy;
        }
        return null;
    }
}