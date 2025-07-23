package it.unibo.runwarrior.controller;

import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.PowerUpFactoryImpl;

import it.unibo.runwarrior.controller.collisions.CollisionDetectionImpl;
import it.unibo.runwarrior.controller.collisions.KillDetectionImpl;
import it.unibo.runwarrior.controller.collisions.PowerUpDetectionImpl;
import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.player.CharacterImpl;

/**
 * Class that handles player movement and his collisions
 */
public class CharacterMovementHandlerImpl implements CharacterMovementHandler {
    private GameLoopPanel glp;
    private CharacterComand cmd;
    private Character player;
    private CollisionDetectionImpl collisionDetection;
    private PowerUpDetectionImpl pUpDetection;
    private KillDetectionImpl killDetection;

    public static final int START_X = 96;
    private int startY;
    private int maxJump;
    private int midJump;
    protected int sizeCharacter;
    
    private static final int MIN_SCREEN_X = 0;//y IN CUI SI FERMA IL PLAYER NELLO SCHERMO
    private int maxScreenX;//x IN CUI SI FERMA IL PLAYER NELLO SCHERMO
    protected int playerX;//POSIZIONE ORIZZONTALE DEL PLAYER NELLA MAPPA
    protected int playerY;// * VERTICALE
    private int screenX;//POSIZIONE ORIZZONTALE DEL PLAYER NELLO SCHERMO
    private int endOfMap;
    private String collisionDir;
    private String tempDir;
    private boolean hitHead;
    private boolean jumpKill;
    private boolean descend;
    private boolean handleDoubleCollision;
    private boolean canAttack;

    public static final int SPEED_JUMP_UP = 12; 
    public static final int SPEED_JUMP_DOWN = 6;
    private boolean rightDirection = true;
    private int groundX;//variabile che permette lo scorrimento della mappa

    /**
     * Constructor of player movemnt that sets the following parametres, the powerup and kill detection and the starting position
     *
     * @param panel game-loop panel
     * @param player current player
     * @param cmd keyboard handler
     * @param collDet collision with map tiles
     * @param hM object that prints tiles
     * @param pFact object that prints powerups
     */
    public CharacterMovementHandlerImpl(final GameLoopPanel panel, final Character player, final CharacterComand cmd,
    final HandlerMapElement hM, final PowerUpFactoryImpl pFact) {
        this.glp = panel;
        this.cmd = cmd;
        this.player = player;
        this.collisionDetection = new CollisionDetectionImpl(hM.getMap(), hM.getBlocks(), hM.getTileSize(), panel);
        this.pUpDetection = new PowerUpDetectionImpl(panel, pFact);
        this.killDetection = new KillDetectionImpl(panel, hM);
        playerX = START_X;
        screenX = START_X;
        groundX = 0;
        endOfMap = ((hM.getMap()[0].length - 1) * hM.getTileSize()) - hM.getTileSize();
        setStartY(hM.getFirstY(), hM.getTileSize());
    }

    /**
     * Set the initial position of the player.
     *
     * @param y y coordinate
     * @param tileSize tile dimension
     */
    private void setStartY(int y, int tileSize){
        startY = y + CharacterImpl.TO_TOUCH_FLOOR; //542
        playerY = startY;
        sizeCharacter = tileSize*2;
        maxJump = startY - (sizeCharacter*5/2); //362
        midJump = startY - (sizeCharacter*3/2);
    }

    public void setLocationAfterPowerup(int x, int y, int realx, int groundX, long lastHit) {
        this.screenX = x;
        this.playerY = y;
        this.playerX = realx;
        this.groundX = groundX;
        this.killDetection.setHitWaitTime(lastHit);
        this.collisionDetection.setHitWaitTime(lastHit);
    }

    public void movePlayer() {
        maxScreenX = glp.getWidth() / 2;
        collisionDir = collisionDetection.checkCollision(player);
        tempDir = pUpDetection.checkCollisionWithPowers(player, this);
        if (!tempDir.isEmpty()) {
            collisionDir = tempDir;
        }
        killDetection.checkCollisionWithEnemeies(player);

        hitHead = collisionDir.equals("down") ? true : false;
        if (hitHead) {
            cmd.setJump(false);
        }
        jump(cmd.isJumping(), maxJump);
        handleDoubleCollision = (collisionDir.equals("up") || collisionDir.equals("down")) && descend ? true : false;
        if (jumpKill) {
            jumpAfterKill();
        }
        if (cmd.getRight() && !cmd.getLeft()) {
            rightDirection = true;
            if (!collisionDir.equals("right") && !handleDoubleCollision && playerX < endOfMap) {
                playerX += CharacterImpl.SPEED;
                if (screenX < maxScreenX) {
                    screenX += CharacterImpl.SPEED;
                } else {
                    groundX -= CharacterImpl.SPEED;
                }
            }
        }
        if (cmd.getLeft() && !cmd.getRight()) {
            rightDirection = false;
            if (!collisionDir.equals("left") && !handleDoubleCollision) {
                if (screenX > 0) {
                    playerX -= CharacterImpl.SPEED;
                }
                if (screenX > MIN_SCREEN_X) {
                    screenX -= CharacterImpl.SPEED;
                }
            }
        }
        System.out.println("-- " + playerX);
        player.updatePlayerPosition();
        canAttack = (collisionDir.equals("right") || collisionDir.equals("left")) ? false : true;
    }

    public void jump (boolean isJump, int jumpHeight) {
        if (isJump && !descend) {
            if(playerY > jumpHeight){
                playerY -= SPEED_JUMP_UP;
            } else {
                playerY = jumpHeight;
                cmd.setJump(false);
            }
        } else {
            if (collisionDetection.isInAir(player) && !jumpKill) {
                descend = true;
                playerY += SPEED_JUMP_DOWN;
                updateJumpVariable();
            }
            else if (!jumpKill) {
                descend = false;
                cmd.setDoubleJump(false);
            }
        }
    }

    public void setJumpKill() {
        this.jumpKill = true;
        cmd.setJump(true);
    }

    public void jumpAfterKill(){
        if (playerY >= midJump && !hitHead) {
            cmd.setDoubleJump(true);
            playerY -= SPEED_JUMP_UP;
        } else {
            if(!hitHead){
                playerY = midJump;
            }
            jumpKill = false;
            cmd.setJump(false);
        }
    }

    public void updateJumpVariable() {
        maxJump = (startY - (sizeCharacter*5/2)) + (playerY - startY);
        midJump = (startY - (sizeCharacter*3/2)) + (playerY - startY);
    }

    public boolean canAttack() {
        return this.canAttack;
    }

    public CollisionDetectionImpl getCollisionDetection() {
        return this.collisionDetection;
    }

    public KillDetectionImpl getKillDetection() {
        return this.killDetection;
    }

    public boolean getRightDirection() {
        return this.rightDirection;
    }

    public int getGroundX() {
        return this.groundX;
    }

    public int getPlX() {
        return this.playerX;
    }

    public int getPlY() {
        return this.playerY;
    }

    public int getScX() {
        return this.screenX;
    }
}
