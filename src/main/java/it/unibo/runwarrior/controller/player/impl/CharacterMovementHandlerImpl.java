package it.unibo.runwarrior.controller.player.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.runwarrior.controller.collisions.api.CollisionDetection;
import it.unibo.runwarrior.controller.collisions.api.PowerUpDetection;
import it.unibo.runwarrior.controller.collisions.api.KillDetection;
import it.unibo.runwarrior.controller.player.CharacterComand;
import it.unibo.runwarrior.controller.GameLoopController;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.controller.PowerUpController;
import it.unibo.runwarrior.controller.collisions.impl.CoinDetectionImpl;
import it.unibo.runwarrior.controller.collisions.impl.CollisionDetectionImpl;
import it.unibo.runwarrior.controller.collisions.impl.KillDetectionImpl;
import it.unibo.runwarrior.controller.collisions.impl.PowerUpDetectionImpl;
import it.unibo.runwarrior.controller.collisions.api.CoinDetection;
import it.unibo.runwarrior.controller.player.api.CharacterMovementHandler;
import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.player.AbstractCharacterImpl;

/**
 * Class that handles player movement and his collisions.
 */
public class CharacterMovementHandlerImpl implements CharacterMovementHandler {
    public static final int SPEED_JUMP_UP = 12; 
    public static final int SPEED_JUMP_DOWN = 6;
    private static final int START_X = 96;
    private static final int JUMP_MAX = 5;
    private static final int JUMP_MID = 3;
    private static final int MIN_SCREEN_X = 0; //y IN CUI SI FERMA IL PLAYER NELLO SCHERMO
    private final GameLoopController glc;
    private final CharacterComand cmd;
    private final Character player;
    private final CollisionDetection collisionDetection;
    private final PowerUpDetection pUpDetection;
    private final KillDetection killDetection;
    private final CoinDetection coinDetection;
    private final int startY;
    private final int endOfMap;
    private final int sizeCharacter;
    private int maxJump;
    private int midJump;
    private int playerX; //POSIZIONE ORIZZONTALE DEL PLAYER NELLA MAPPA
    private int playerY; // * VERTICALE
    private int screenX; //POSIZIONE ORIZZONTALE DEL PLAYER NELLO SCHERMO
    private boolean hitHead;
    private boolean jumpKill;
    private boolean descend;
    private boolean canAttack;
    private boolean rightDirection = true;
    private int groundX; //variabile che permette lo scorrimento della mappa

    /**
     * Constructor of player movemnt that sets the following parametres, the collision with tiles, powerup and enemies
     * and the starting position.
     *
     * @param glc game-loop controller
     * @param player current player
     * @param cmd keyboard handler
     * @param hM object that prints tiles
     * @param pCon object that creates powerup list
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public CharacterMovementHandlerImpl(final GameLoopController glc, final Character player, final CharacterComand cmd,
    final HandlerMapElement hM, final PowerUpController pCon) {
        this.glc = glc;
        this.cmd = cmd;
        this.player = player;
        this.collisionDetection = new CollisionDetectionImpl(hM.getMap(), hM.getBlocks(), hM.getTileSize(), glc);
        this.killDetection = new KillDetectionImpl(glc, hM);
        this.pUpDetection = new PowerUpDetectionImpl(glc, pCon);
        this.coinDetection = new CoinDetectionImpl(hM.getTileSize(), glc.getCoinController(), glc.getScoreController());
        playerX = START_X;
        screenX = START_X;
        groundX = 0;
        sizeCharacter = hM.getTileSize() * 2;
        this.startY = hM.getFirstY() + AbstractCharacterImpl.TO_TOUCH_FLOOR;
        endOfMap = ((hM.getMap()[0].length - 1) * hM.getTileSize()) - hM.getTileSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStartY(final int y) {
        playerY = y + AbstractCharacterImpl.TO_TOUCH_FLOOR;
        maxJump = playerY - (sizeCharacter * JUMP_MAX / 2);
        midJump = playerY - (sizeCharacter * JUMP_MID / 2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLocationAfterPowerup(final int x, final int y, final int realx, final int slide, final long lastHit) {
        this.screenX = x;
        this.playerY = y;
        this.playerX = realx;
        maxJump = playerY - (sizeCharacter * JUMP_MAX);
        midJump = playerY - (sizeCharacter * JUMP_MID);
        this.groundX = slide;
        this.killDetection.setHitWaitTime(lastHit);
        this.collisionDetection.setHitWaitTime(lastHit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void movePlayer() {
        final int maxScreenX = glc.getGlp().getWidth() / 2;
        String collisionDir = collisionDetection.checkCollision(player);
        final String tempDir = pUpDetection.checkCollisionWithPowers(player, this);
        if (!tempDir.isEmpty()) {
            collisionDir = tempDir;
        }
        killDetection.checkCollisionWithEnemeies(player);
        coinDetection.controlCoinCollision(player);

        hitHead = "down".equals(collisionDir);
        if (hitHead) {
            cmd.setJump(false);
        }
        jump(cmd.isJumping(), maxJump);
        final boolean handleDoubleCollision = ("up".equals(collisionDir) || "down".equals(collisionDir)) && descend;
        if (jumpKill) {
            jumpAfterKill();
        }

        if (cmd.isRight() && !cmd.isLeft()) {
            rightDirection = true;
            if (!"right".equals(collisionDir) && !handleDoubleCollision && playerX < endOfMap) {
                playerX += AbstractCharacterImpl.SPEED;
                if (screenX < maxScreenX) {
                    screenX += AbstractCharacterImpl.SPEED;
                } else {
                    groundX -= AbstractCharacterImpl.SPEED;
                }
            }
        }
        if (cmd.isLeft() && !cmd.isRight()) {
            rightDirection = false;
            if (!"left".equals(collisionDir) && !handleDoubleCollision) {
                if (screenX > 0) {
                    playerX -= AbstractCharacterImpl.SPEED;
                }
                if (screenX > MIN_SCREEN_X) {
                    screenX -= AbstractCharacterImpl.SPEED;
                }
            }
        }
        canAttack = !"right".equals(collisionDir) || !"left".equals(collisionDir);
    }

    /**
     * When it's called make the player jump or descend if the jump input from keyboard has ended.
     *
     * @param isJump true if the player is jumping
     * @param jumpHeight max height of the jump
     */
    private void jump(final boolean isJump, final int jumpHeight) {
        if (isJump && !descend) {
            if (playerY > jumpHeight) {
                playerY -= SPEED_JUMP_UP;
            } else {
                playerY = jumpHeight;
                cmd.setJump(false);
                updateJumpVariable();
            }
        } else {
            if (collisionDetection.isInAir(player) && !jumpKill) {
                descend = true;
                playerY += SPEED_JUMP_DOWN;
                updateJumpVariable();
            } else if (!jumpKill) {
                descend = false;
                cmd.setDoubleJump(false);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setJumpKill() {
        this.jumpKill = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void jumpAfterKill() {
        if (playerY >= midJump && !hitHead) {
            cmd.setDoubleJump(true);
            playerY -= SPEED_JUMP_UP;
        } else {
            if (!hitHead) {
                playerY = midJump;
            }
            jumpKill = false;
            cmd.setJump(false);
        }
    }

    /**
     * Updates the values of max and mid jump based on where the player is.
     */
    private void updateJumpVariable() {
        maxJump = startY - (sizeCharacter * JUMP_MAX / 2) + playerY - startY;
        midJump = startY - (sizeCharacter * JUMP_MID / 2) + playerY - startY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canAttack() {
        return this.canAttack;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings("EI_EXPOSE_REP")
    @Override
    public CollisionDetection getCollisionDetection() {
        return this.collisionDetection;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings("EI_EXPOSE_REP")
    @Override
    public KillDetection getKillDetection() {
        return this.killDetection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRightDirection() {
        return this.rightDirection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getGroundX() {
        return this.groundX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlX() {
        return this.playerX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlY() {
        return this.playerY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScX() {
        return this.screenX;
    }
}
