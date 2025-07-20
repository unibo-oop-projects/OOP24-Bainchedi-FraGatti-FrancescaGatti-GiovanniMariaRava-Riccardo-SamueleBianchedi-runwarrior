package it.unibo.runwarrior.controller;

import java.awt.image.BufferedImage;

import it.unibo.runwarrior.model.player.PlayerFrame;

public class CharacterAnimationHandlerImpl implements CharacterAnimationHandler {

    private CharacterComand cmd;
    private CharacterMovementHandler movement;

    private final int TIME_TO_CHANGE = 8;
    private final int LIMIT_ATTACK = 60;
    private int changeFrame = 0;
    private boolean crossWalk = false;
    private int useAttackMoving = 0;
    private PlayerFrame playerFrame = PlayerFrame.STOP_FRAME;
    private BufferedImage right0, right1, right2, left0, left1, left2, attackR, attackL, tipR, tipL;

    public CharacterAnimationHandlerImpl(CharacterComand cmd, CharacterMovementHandler move, BufferedImage... im) {
        this.cmd = cmd;
        this.movement = move;
        right0 = im[0];
        right1 = im[1];
        right2 = im[2];
        left0 = im[3];
        left1 = im[4];
        left2 = im[5];
        attackR = im[6];
        attackL = im[7];
        tipR = im[8];
        tipL = im[9];
    }

    public void frameChanger() {
        if (!cmd.getStop() && !cmd.isJumping()) {
            changeFrame++;
            if (changeFrame > TIME_TO_CHANGE) {
                switch (playerFrame) {
                    case STOP_FRAME, ATTACK_FRAME -> {
                        playerFrame = crossWalk ? PlayerFrame.GO_FRAME1 : PlayerFrame.GO_FRAME2;
                        crossWalk = !crossWalk;
                    }
                    case GO_FRAME1, GO_FRAME2 -> playerFrame = PlayerFrame.STOP_FRAME;
                }
                changeFrame = 0;
            }
            useAttackMoving++;
            if (cmd.getAttack() && movement.canAttack() && useAttackMoving > LIMIT_ATTACK) { //numero di attacchi limitato se in movimento
                playerFrame = PlayerFrame.ATTACK_FRAME;
                useAttackMoving = 0;
            }
        }
        else if (cmd.isJumping()) {
            playerFrame = PlayerFrame.GO_FRAME2;
            if (cmd.getAttack() && movement.canAttack()) {
                playerFrame = PlayerFrame.ATTACK_FRAME;
            }
        }
        else if (cmd.getAttack() && movement.canAttack()) {
            playerFrame = PlayerFrame.ATTACK_FRAME;
        }
        else {
            playerFrame = PlayerFrame.STOP_FRAME;
        }
    }

    public BufferedImage imagePlayer(boolean rightDirection) {
        BufferedImage im = null;
        if (rightDirection) {
            switch (playerFrame) {
                case STOP_FRAME -> im = right0;
                case GO_FRAME1 -> im = right1;
                case GO_FRAME2 -> im = right2;
                case ATTACK_FRAME -> {
                    im = attackR;
                }
            }
        } else {
            switch (playerFrame) {
                case STOP_FRAME -> im = left0;
                case GO_FRAME1 -> im = left1;
                case GO_FRAME2 -> im = left2;
                case ATTACK_FRAME -> {
                    im = attackL;
                }
            }
        }
        return im;
    }

    public PlayerFrame getFrame(){
        return this.playerFrame;
    }

    public boolean isAttacking() {
        return playerFrame == PlayerFrame.ATTACK_FRAME ? true : false;
    }

    public BufferedImage getTip(boolean rightDirection) {
        if (playerFrame == PlayerFrame.ATTACK_FRAME && rightDirection) {
            return tipR;
        } else {
            return tipL;
        }
    }
}